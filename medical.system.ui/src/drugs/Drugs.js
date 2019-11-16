import React from "react";
import APIResponseErrorMessage from "../common/api-response-error-message";
import { Card, Col, Row } from "reactstrap";
import Table from "../tables/table";
import { getDrugs, deleteDrugById } from "../util/APIUtils";
import { notification } from "antd";
import DrugForm from "./DrugForm";
import { Link } from "react-router-dom";

let columns = [
  {
    Header: "Name",
    accessor: "name"
  },
  {
    Header: "Dosage",
    accessor: "dosage"
  },
  {
    Header: "Side effects",
    accessor: "sideEffects"
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
  }
];

class Drugs extends React.Component {
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
    this.fetchDrugs();
  }

  deleteDrug(row) {
    console.log("Delete clicked");
    console.log(row.original.id);
    deleteDrugById(row.original.id)
      .then(response => {
        notification.success({
          message: "Medical System",
          description: response.status
        });
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

  fetchDrugs() {
    console.log(this.props);
    switch (this.props.currentUser.role) {
      case "ROLE_DOCTOR": {
        getDrugs()
          .then(response =>
            response.forEach(drug => {
              this.tableData.push({
                id: drug.id,
                name: drug.name,
                dosage: drug.dosage,
                sideEffects: drug.sideEffects
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
          to={"/doctor/drug/" + row.original.id}
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
          to="/doctor/drugs"
          component="button"
          variant="body2"
          onClick={() => this.deleteDrug(row)}
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
                <DrugForm handleSubmit={this.refresh}></DrugForm>
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

export default Drugs;
