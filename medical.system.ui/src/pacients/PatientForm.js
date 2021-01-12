import React from "react";
import validate from "../person-data/person/validators/person-validators";
import TextInput from "../person-data/person/fields/TextInput";
import "../person-data/person/fields/fields.css";
import Button from "react-bootstrap/Button";
import APIResponseErrorMessage from "../common/api-response-error-message";
import { notification } from "antd";
import SelectInput from "../person-data/person/fields/SelectInput";

import {
  insertDoctorPatient,
  getDoctorPatient,
  updatePatient,
  getDoctors,
  getCaregivers
} from "../util/APIUtils";

class PatientForm extends React.Component {
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
        },

        doctors: {
          data: [],
          value: "",
          valid: true,
          touched: true
        },

        caregivers: {
          data: [],
          value: "",
          valid: true,
          touched: true
        }
      },

      doctorId: null,

      caregiver: null
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  toggleForm() {
    this.setState({ collapseForm: !this.state.collapseForm });
  }

  componentDidMount() {
    if (this.props.match !== undefined) {
      const { patientId } = this.props.match.params;
      console.log("Am patientId:");
      console.log(patientId);

      getDoctorPatient(patientId)
        .then(response => {
          if (response === null) {
            console.log("Patient not found " + patientId);
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

      getDoctors()
        .then(response => {
          if (response === null) {
            console.log("Doctors not found ");
          } else {
            console.log(response);

            const updatedControls = {
              ...this.state.formControls
            };

            let doctorsNames = [];

            response.forEach(doctor => {
              let doctorName = {
                label: doctor.firstName + " " + doctor.lastName,
                value: doctor.id
              };
              doctorsNames.push(doctorName);
            });

            console.log(doctorsNames);

            let isFormValid = this.state.isFormValid;
            updatedControls["doctors"].data = doctorsNames;

            this.setState({
              formControls: updatedControls,
              isFormValid: isFormValid
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

      getCaregivers()
        .then(response => {
          if (response === null) {
            console.log("Caregivers not found ");
          } else {
            console.log(response);

            const updatedControls = {
              ...this.state.formControls
            };

            let caregiversNames = [];

            response.forEach(caregiver => {
              let caregiverName = {
                label: caregiver.firstName + " " + caregiver.lastName,
                value: caregiver.id
              };
              caregiversNames.push(caregiverName);
            });

            console.log(caregiversNames);

            let isFormValid = this.state.isFormValid;
            updatedControls["caregivers"].data = caregiversNames;

            this.setState({
              formControls: updatedControls,
              isFormValid: isFormValid
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
    console.log("Event");
    console.log(event.target.name);
    console.log(event.target.value);
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

    let doctorId = this.state.doctorId,
      caregiverId = this.state.caregiverId;

    if (name === "doctors") {
      console.log("Event de schimbat doctor");
      console.log(value);
      doctorId = value;
    }

    if (name === "caregivers") {
      console.log("Event de schimbat caregiver");
      console.log(value);
      caregiverId = value;
    }

    this.setState({
      formControls: updatedControls,
      formIsValid: formIsValid,
      doctorId: doctorId,
      caregiverId: caregiverId
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

    let patient = {
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
      email: this.state.formControls.email.value,
      doctorId: this.state.doctorId,
      caregiverId: this.state.caregiverId
    };

    const updatedControls = {
      ...this.state.formControls
    };

    for (let updatedFormElementName in updatedControls) {
      updatedControls[updatedFormElementName].valid = false;
      updatedControls[updatedFormElementName].touched = false;
      updatedControls[updatedFormElementName].value = "";
    }

    console.log("New patient data:");
    console.log(patient);

    if (this.props.match === undefined) {
      //normal behaviour, i.e. insert
      insertDoctorPatient(this.props.doctorId, patient)
        .then(response => {
          if (response == null) {
            notification.error({
              message: "Medical System",
              description:
                "Sorry! Patient could not be added. Please try again!"
            });
            return;
          }
          notification.success({
            message: "Medical System",
            description: "Patient inserted"
          });
          this.props.handleSubmit();
        })
        .catch(error => {
          if (error.status === 401) {
            this.props.handleLogout(
              "/login",
              "error",
              "You have been logged out. Please login to create patient."
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
      const { patientId: patientId } = this.props.match.params;
      patient.id = patientId;
      updatePatient(patient)
        .then(response => {
          if (response == null) {
            notification.error({
              message: "Medical System",
              description:
                "Sorry! Patient could not be updated. Please try again!"
            });
            return;
          }
          notification.success({
            message: "Medical System",
            description: "Patient updated"
          });
        })
        .catch(error => {
          if (error.status === 401) {
            this.props.handleLogout(
              "/login",
              "error",
              "You have been logged out. Please login to create patient."
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
        <h1>Insert new patient</h1>

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

        {this.props.match !== undefined && (
          <div>
            <p>Select pacient's doctor:</p>

            <SelectInput
              name="doctors"
              data={this.state.formControls.doctors.data}
              value={this.state.formControls.doctors.value}
              onChange={this.handleChange}
            />

            <p>Select pacient's caregiver:</p>

            <SelectInput
              name="caregivers"
              data={this.state.formControls.caregivers.data}
              value={this.state.formControls.caregivers.value}
              onChange={this.handleChange}
            />
          </div>
        )}

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

export default PatientForm;
