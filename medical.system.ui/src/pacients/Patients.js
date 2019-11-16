import React from "react";
import APIResponseErrorMessage from "../common/api-response-error-message";
import { Card, Col, Row } from "reactstrap";
import Table from "../tables/table";
import {
  getDoctorPatients,
  deletePatient,
  getCaregiverPatients
} from "../util/APIUtils";
import { notification } from "antd";
import { Link } from "react-router-dom";
import PatientForm from "./PatientForm";

let columns = [
  {
    Header: "Name",
    accessor: "name"
  },
  {
    Header: "Email",
    accessor: "email"
  },
  {
    Header: "Birth Date",
    accessor: "birthDate"
  },
  {
    Header: "Address",
    accessor: "address"
  }
];

const filters = [
  {
    accessor: "name"
  },
  {
    accessor: "email"
  }
];

class Pacients extends React.Component {
  constructor(props) {
    super(props);
    this.toggleForm = this.toggleForm.bind(this);
    this.refresh = this.refresh.bind(this);
    this.state = {
      collapseForm: true,
      loadPage: false,
      errorStatus: 0,
      error: null
    };

    this.tableData = [];
  }

  toggleForm() {
    this.setState({ collapseForm: !this.state.collapseForm });
  }

  componentDidMount() {
    this.fetchPacients();
    if (this.props.currentUser.role === "ROLE_DOCTOR" && columns.length === 4) {
      columns.push(
        {
          Header: "Delete",
          accessor: "delete"
        },
        {
          Header: "Edit",
          accessor: "edit"
        },
        {
          Header: "Medication Plan",
          accessor: "medicationPlan"
        }
      );
      columns[columns.length - 1].Cell = row => (
        <div>
          <Link
            component="button"
            variant="body2"
            to={"/doctor/medicationPlan/" + row.original.id}
          >
            Create
          </Link>
        </div>
      );
      columns[columns.length - 2].Cell = row => (
        <div>
          <Link
            component="button"
            variant="body2"
            to={"/doctor/patient/" + row.original.id}
          >
            Edit
          </Link>
        </div>
      );
      columns[columns.length - 3].Cell = row => (
        <div>
          <Link
            to="/doctor/patients"
            component="button"
            variant="body2"
            onClick={() => this.deletePacientByid(row)}
          >
            Delete
          </Link>
        </div>
      );
    }
  }

  fetchPacients() {
    console.log(this.props);
    switch (this.props.currentUser.role) {
      case "ROLE_DOCTOR": {
        getDoctorPatients(this.props.currentUser.id)
          .then(response =>
            response.forEach(patient => {
              this.tableData.push({
                id: patient.id,
                name: patient.name,
                email: patient.email,
                birthDate: patient.birthDate,
                address: patient.address
              });
              this.refresh();
            })
          )
          .catch(error => {
            if (error.status === 401) {
              notification.error({
                message: "Medical System",
                description:
                  "Your Username or Password is incorrect. Please try again!"
              });
            } else {
              notification.error({
                message: "Medical System",
                description:
                  error.message ||
                  "Sorry! Something went wrong. Please try again!"
              });
            }
          });
        break;
      }
      case "ROLE_CAREGIVER": {
        getCaregiverPatients(this.props.currentUser.id)
          .then(response =>
            response.forEach(patient => {
              this.tableData.push({
                id: patient.id,
                name: patient.name,
                email: patient.email,
                birthDate: patient.birthDate,
                address: patient.address
              });
            })
          )
          .catch(error => {
            if (error.status === 401) {
              notification.error({
                message: "Medical System",
                description:
                  "Your Username or Password is incorrect. Please try again!"
              });
            } else {
              notification.error({
                message: "Medical System",
                description:
                  error.message ||
                  "Sorry! Something went wrong. Please try again!"
              });
            }
          });
        break;
      }
      default:
        break;
    }
  }

  deletePacientByid(row) {
    console.log("Delete clicked");
    console.log(row.original.id);
    deletePatient(row.original.id)
      .then(response => {
        notification.success({
          message: "Medical System",
          description: response.status
        });
        this.refresh();
      })
      .catch(error => {
        if (error.status === 401) {
          notification.error({
            message: "Medical System",
            description:
              "Your Username or Password is incorrect. Please try again!"
          });
        } else {
          notification.error({
            message: "Medical System",
            description:
              error.message || "Sorry! Something went wrong. Please try again!"
          });
        }
      });
  }

  refresh() {
    this.forceUpdate();
  }

  render() {
    let pageSize = 5;
    return (
      <div>
        <Row>
          <Col>
            <Card body>
              <Table
                data={this.tableData}
                columns={columns}
                search={filters}
                pageSize={pageSize}
              />
            </Card>
          </Col>
        </Row>

        {this.props.currentUser.role === "ROLE_DOCTOR" && (
          <Row>
            <Col>
              <Card body>
                <div>
                  <PatientForm
                    handleSubmit={this.refresh}
                    doctorId={this.props.currentUser.id}
                  ></PatientForm>
                </div>
              </Card>
            </Col>
          </Row>
        )}

        {this.state.errorStatus > 0 && (
          <APIResponseErrorMessage
            errorStatus={this.state.errorStatus}
            error={this.state.error}
          />
        )}
      </div>
    );
  }
}

export default Pacients;
