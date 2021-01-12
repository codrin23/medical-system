import React from "react";
import validate from "../person-data/person/validators/person-validators";
import TextInput from "../person-data/person/fields/TextInput";
import "../person-data/person/fields/fields.css";
import Button from "react-bootstrap/Button";
import APIResponseErrorMessage from "../common/api-response-error-message";
import { notification } from "antd";

import { insertDrug, getDrug, updateDrug } from "../util/APIUtils";

class DrugForm extends React.Component {
  constructor(props) {
    super(props);
    this.toggleForm = this.toggleForm.bind(this);

    this.state = {
      errorStatus: 0,
      error: null,

      formIsValid: false,

      formControls: {
        name: {
          value: "",
          placeholder: "What is drug's name?...",
          valid: false,
          touched: false,
          validationRules: {
            minLength: 3,
            isRequired: true
          }
        },

        dosage: {
          value: "",
          placeholder: "What is drug's dosage?...",
          valid: false,
          touched: false,
          validationRules: {
            minLength: 3,
            isRequired: true
          }
        },

        sideEffects: {
          value: "",
          placeholder: "What is drug's side effects?...",
          valid: false,
          touched: false,
          validationRules: {
            minLength: 3,
            isRequired: true
          }
        }
      }
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  toggleForm() {
    this.setState({ collapseForm: !this.state.collapseForm });
  }

  componentDidMount() {
    if (this.props.match !== undefined) {
      const { drugId } = this.props.match.params;
      console.log("Am drugId:");
      console.log(drugId);

      getDrug(drugId)
        .then(response => {
          if (response === null) {
            console.log("Drug not found " + drugId);
          } else {
            console.log(response);

            const updatedControls = {
              ...this.state.formControls
            };

            let formIsValid = false;
            for (let updatedFormElementName in updatedControls) {
              console.log(updatedFormElementName);
              console.log(response[updatedFormElementName]);
              updatedControls[updatedFormElementName].valid = true;
              updatedControls[updatedFormElementName].touched = true;
              updatedControls[updatedFormElementName].value =
                response[updatedFormElementName];
            }

            console.log(updatedControls);

            this.setState({
              formControls: updatedControls,
              formIsValid: formIsValid
            });
          }
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
                error.message ||
                "Sorry! Something went wrong. Please try again!"
            });
          }
        });
    }
  }

  handleChange = event => {
    const name = event.target.name;
    const value = event.target.value;

    const updatedControls = {
      ...this.state.formControls
    };

    const updatedFormElement = {
      ...updatedControls[name]
    };

    updatedFormElement.value = value;
    updatedFormElement.touched = true;
    updatedFormElement.valid = validate(
      value,
      updatedFormElement.validationRules
    );

    console.log("Element: " + name + " validated: " + updatedFormElement.valid);

    updatedControls[name] = updatedFormElement;

    let formIsValid = true;
    for (let updatedFormElementName in updatedControls) {
      formIsValid =
        updatedControls[updatedFormElementName].valid && formIsValid;
    }

    this.setState({
      formControls: updatedControls,
      formIsValid: formIsValid
    });
  };

  handleSubmit(event) {
    event.preventDefault();

    let drug = {
      name: this.state.formControls.name.value,
      dosage: this.state.formControls.dosage.value,
      sideEffects: this.state.formControls.sideEffects.value
    };

    const updatedControls = {
      ...this.state.formControls
    };

    for (let updatedFormElementName in updatedControls) {
      updatedControls[updatedFormElementName].valid = false;
      updatedControls[updatedFormElementName].touched = false;
      updatedControls[updatedFormElementName].value = "";
    }

    console.log("New drug data:");
    console.log(drug);

    if (this.props.match === undefined) {
      //normal behaviour, i.e. insert
      insertDrug(drug)
        .then(response => {
          if (response == null) {
            notification.error({
              message: "Medical System",
              description: "Sorry! Drug could not be added. Please try again!"
            });
            return;
          }
          notification.success({
            message: "Medical System",
            description: "Drug inserted"
          });
        })
        .catch(error => {
          if (error.status === 401) {
            this.props.handleLogout(
              "/login",
              "error",
              "You have been logged out. Please login to create drug."
            );
          } else {
            notification.error({
              message: "Medical System",
              description:
                error.message ||
                "Sorry! Something went wrong. Please try again!"
            });
          }
        });
    } else {
      //update is required here
      const { drugId } = this.props.match.params;
      drug.id = drugId;
      updateDrug(drug)
        .then(response => {
          if (response == null) {
            notification.error({
              message: "Medical System",
              description: "Sorry! Drug could not be updated. Please try again!"
            });
            return;
          }
          notification.success({
            message: "Medical System",
            description: "Drug updated"
          });
        })
        .catch(error => {
          if (error.status === 401) {
            this.props.handleLogout(
              "/login",
              "error",
              "You have been logged out. Please login to create drug."
            );
          } else {
            notification.error({
              message: "Medical System",
              description:
                error.message ||
                "Sorry! Something went wrong. Please try again!"
            });
          }
        });
    }
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <h1>Insert new drug</h1>

        <p> Name: </p>
        <TextInput
          name="name"
          placeholder={this.state.formControls.name.placeholder}
          value={this.state.formControls.name.value}
          onChange={this.handleChange}
          touched={this.state.formControls.name.touched}
          valid={this.state.formControls.name.valid}
        />
        {this.state.formControls.name.touched &&
          !this.state.formControls.name.valid && (
            <div className={"error-message row"}>
              {" "}
              * Name must have at least 3 characters{" "}
            </div>
          )}

        <p> Dosage: </p>
        <TextInput
          name="dosage"
          placeholder={this.state.formControls.dosage.placeholder}
          value={this.state.formControls.dosage.value}
          onChange={this.handleChange}
          touched={this.state.formControls.dosage.touched}
          valid={this.state.formControls.dosage.valid}
        />

        <p> Side effects: </p>
        <TextInput
          name="sideEffects"
          placeholder={this.state.formControls.sideEffects.placeholder}
          value={this.state.formControls.sideEffects.value}
          onChange={this.handleChange}
          touched={this.state.formControls.sideEffects.touched}
          valid={this.state.formControls.sideEffects.valid}
        />

        <p></p>
        <Button
          variant="success"
          type={"submit"}
          disabled={!this.state.formIsValid}
        >
          Submit
        </Button>

        {this.state.errorStatus > 0 && (
          <APIResponseErrorMessage
            errorStatus={this.state.errorStatus}
            error={this.state.error}
          />
        )}
      </form>
    );
  }
}

export default DrugForm;
