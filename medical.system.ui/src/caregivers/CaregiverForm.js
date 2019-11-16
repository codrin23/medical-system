import React from "react";
import validate from "../person-data/person/validators/person-validators";
import TextInput from "../person-data/person/fields/TextInput";
import "../person-data/person/fields/fields.css";
import Button from "react-bootstrap/Button";
import APIResponseErrorMessage from "../common/api-response-error-message";
import { notification } from "antd";

import {
  insertCaregiver,
  getCaregiver,
  updateCaregiver
} from "../util/APIUtils";

class CaregiverForm extends React.Component {
  constructor(props) {
    super(props);
    this.toggleForm = this.toggleForm.bind(this);

    this.state = {
      errorStatus: 0,
      error: null,

      formIsValid: false,

      formControls: {
        userName: {
          value: "",
          placeholder: "What is your username?...",
          valid: false,
          touched: false,
          validationRules: {
            minLength: 3,
            isRequired: true
          }
        },

        password: {
          value: "",
          placeholder: "What is your password?...",
          valid: false,
          touched: false,
          validationRules: {
            minLength: 6,
            isRequired: true
          }
        },

        firstName: {
          value: "",
          placeholder: "What is your first name?...",
          valid: false,
          touched: false,
          validationRules: {
            minLength: 2,
            isRequired: true
          }
        },

        lastName: {
          value: "",
          placeholder: "What is your last name?...",
          valid: false,
          touched: false,
          validationRules: {
            minLength: 2,
            isRequired: true
          }
        },

        birthDate: {
          value: "",
          valid: true
        },

        gender: {
          value: "male",
          valid: true,
          touched: false
        },

        city: {
          value: "",
          placeholder: "What is your city?...",
          valid: false,
          touched: false,
          validationRules: {
            minLength: 2,
            isRequired: true
          }
        },

        street: {
          value: "",
          placeholder: "What is your street?...",
          valid: false,
          touched: false,
          validationRules: {
            minLength: 2,
            isRequired: true
          }
        },

        number: {
          value: "",
          placeholder: "What is your number?...",
          valid: false,
          touched: false,
          validationRules: {
            minLength: 1,
            isRequired: true
          }
        },

        email: {
          value: "",
          placeholder: "Email...",
          valid: false,
          touched: false,
          validationRules: {
            emailValidator: true
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
      const { caregiverId } = this.props.match.params;
      console.log("Am caregiverId:");
      console.log(caregiverId);

      getCaregiver(caregiverId)
        .then(response => {
          if (response === null) {
            console.log("Caregiver not found " + caregiverId);
          } else {
            console.log(response);

            const updatedControls = {
              ...this.state.formControls
            };

            let formIsValid = false;
            for (let updatedFormElementName in updatedControls) {
              if (updatedFormElementName === "password") continue;
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

    // check for garbage
    // TODO: add validator for it
    let gender = "male";

    if (gender === "female") {
      gender = "female";
    }

    let caregiver = {
      userName: this.state.formControls.userName.value,
      password: this.state.formControls.password.value,
      firstName: this.state.formControls.firstName.value,
      lastName: this.state.formControls.lastName.value,
      birthDate: this.state.formControls.birthDate.value,
      gender: gender,
      city: this.state.formControls.city.value,
      street: this.state.formControls.street.value,
      number: this.state.formControls.number.value,
      isActive: "true",
      email: this.state.formControls.email.value
    };

    const updatedControls = {
      ...this.state.formControls
    };

    for (let updatedFormElementName in updatedControls) {
      updatedControls[updatedFormElementName].valid = false;
      updatedControls[updatedFormElementName].touched = false;
      updatedControls[updatedFormElementName].value = "";
    }

    console.log("New caregiver data:");
    console.log(caregiver);

    if (this.props.match === undefined) {
      //normal behaviour, i.e. insert
      insertCaregiver(caregiver)
        .then(response => {
          if (response == null) {
            notification.error({
              message: "Medical System",
              description:
                "Sorry! Caregiver could not be added. Please try again!"
            });
            return;
          }
          notification.success({
            message: "Medical System",
            description: "Caregiver inserted"
          });
        })
        .catch(error => {
          if (error.status === 401) {
            this.props.handleLogout(
              "/login",
              "error",
              "You have been logged out. Please login to create caregiver."
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
      const { caregiverId } = this.props.match.params;
      caregiver.id = caregiverId;
      updateCaregiver(caregiver)
        .then(response => {
          if (response == null) {
            notification.error({
              message: "Medical System",
              description:
                "Sorry! Caregiver could not be updated. Please try again!"
            });
            return;
          }
          notification.success({
            message: "Medical System",
            description: "Caregiver updated"
          });
        })
        .catch(error => {
          if (error.status === 401) {
            this.props.handleLogout(
              "/login",
              "error",
              "You have been logged out. Please login to create caregiver."
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
        <h1>Insert new caregiver</h1>

        <p> Username: </p>
        <TextInput
          name="userName"
          placeholder={this.state.formControls.userName.placeholder}
          value={this.state.formControls.userName.value}
          onChange={this.handleChange}
          touched={this.state.formControls.userName.touched}
          valid={this.state.formControls.userName.valid}
        />
        {this.state.formControls.userName.touched &&
          !this.state.formControls.userName.valid && (
            <div className={"error-message row"}>
              {" "}
              * Name must have at least 3 characters{" "}
            </div>
          )}

        <p> Password: </p>
        <TextInput
          name="password"
          type="password"
          placeholder={this.state.formControls.password.placeholder}
          value={this.state.formControls.password.value}
          onChange={this.handleChange}
          touched={this.state.formControls.password.touched}
          valid={this.state.formControls.password.valid}
        />

        <p> First name: </p>
        <TextInput
          name="firstName"
          placeholder={this.state.formControls.firstName.placeholder}
          value={this.state.formControls.firstName.value}
          onChange={this.handleChange}
          touched={this.state.formControls.firstName.touched}
          valid={this.state.formControls.firstName.valid}
        />

        <p> Last name: </p>
        <TextInput
          name="lastName"
          placeholder={this.state.formControls.lastName.placeholder}
          value={this.state.formControls.lastName.value}
          onChange={this.handleChange}
          touched={this.state.formControls.lastName.touched}
          valid={this.state.formControls.lastName.valid}
        />

        <p> Email: </p>
        <TextInput
          name="email"
          placeholder={this.state.formControls.email.placeholder}
          value={this.state.formControls.email.value}
          onChange={this.handleChange}
          touched={this.state.formControls.email.touched}
          valid={this.state.formControls.email.valid}
        />
        {this.state.formControls.email.touched &&
          !this.state.formControls.email.valid && (
            <div className={"error-message"}>
              {" "}
              * Email must have a valid format
            </div>
          )}

        <p> Birth Date: </p>
        <TextInput
          name="birthDate"
          type="date"
          placeholder={this.state.formControls.birthDate.placeholder}
          value={this.state.formControls.birthDate.value}
          onChange={this.handleChange}
          touched={this.state.formControls.birthDate.touched}
          valid={this.state.formControls.birthDate.valid}
        />

        <p> Gender: </p>
        <TextInput
          name="gender"
          value="male"
          selectBoxOptions="male;female"
          value={this.state.formControls.gender.value}
          onChange={this.handleChange}
          touched={this.state.formControls.gender.touched}
          valid={this.state.formControls.gender.valid}
        />

        <p> City: </p>
        <TextInput
          name="city"
          placeholder={this.state.formControls.city.placeholder}
          value={this.state.formControls.city.value}
          onChange={this.handleChange}
          touched={this.state.formControls.city.touched}
          valid={this.state.formControls.city.valid}
        />

        <p> Street: </p>
        <TextInput
          name="street"
          placeholder={this.state.formControls.street.placeholder}
          value={this.state.formControls.street.value}
          onChange={this.handleChange}
          touched={this.state.formControls.street.touched}
          valid={this.state.formControls.street.valid}
        />

        <p> Number: </p>
        <TextInput
          name="number"
          placeholder={this.state.formControls.number.placeholder}
          value={this.state.formControls.number.value}
          onChange={this.handleChange}
          touched={this.state.formControls.number.touched}
          valid={this.state.formControls.number.valid}
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

export default CaregiverForm;
