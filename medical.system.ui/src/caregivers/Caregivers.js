import React from "react";
import APIResponseErrorMessage from "../common/api-response-error-message";
import { Card, Col, Row } from "reactstrap";
import Table from "../tables/table";
import { getCaregivers, deleteCaregiver } from "../util/APIUtils";
import { notification } from "antd";
import CaregiverForm from "./CaregiverForm";
import { Link } from "react-router-dom";

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
  },
  {
    Header: "Delete",
    accessor: "delete"
  },
  {
    Header: "Edit",
    accessor: "edit"
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

class Caregivers extends React.Component {
  constructor(props) {
    super(props);
    this.toggleForm = this.toggleForm.bind(this);
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
    this.fetchCaregivers();
  }

  deleteCaregiver(row) {
    console.log("Delete clicked");
    console.log(row.original.id);
    deleteCaregiver(row.original.id)
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

  fetchCaregivers() {
    console.log(this.props);
    switch (this.props.currentUser.role) {
      case "ROLE_DOCTOR": {
        getCaregivers()
          .then(response =>
            response.forEach(caregiver => {
              this.tableData.push({
                id: caregiver.id,
                name: caregiver.firstName + " " + caregiver.lastName,
                email: caregiver.email,
                birthDate: caregiver.birthDate,
                address:
                  caregiver.city +
                  " " +
                  caregiver.street +
                  " " +
                  caregiver.number
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
      default:
        break;
    }
  }

  refresh() {
    this.forceUpdate();
  }

  render() {
    let pageSize = 5;
    columns[columns.length - 1].Cell = row => (
      <div>
        <Link
          to={"/doctor/caregiver/" + row.original.id}
          component="button"
          variant="body2"
        >
          Edit
        </Link>
      </div>
    );
    columns[columns.length - 2].Cell = row => (
      <div>
        <Link
          to="/doctor/caregivers"
          component="button"
          variant="body2"
          onClick={() => this.deleteCaregiver(row)}
        >
          Delete
        </Link>
      </div>
    );
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

        <Row>
          <Col>
            <Card body>
              <div>
                <CaregiverForm handleSubmit={this.refresh}></CaregiverForm>
              </div>
            </Card>
          </Col>
        </Row>

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

export default Caregivers;
