import React from "react";
import SelectInput from "../person-data/person/fields/SelectInput";
import DrugPlan from "./DrugPlan";
import DateInterval from "./DateInterval";
import Button from "react-bootstrap/Button";

import { getDrugs, createMedicationPlan } from "../util/APIUtils";
import { notification } from "antd";

const options = [
  { value: 1, label: "1" },
  { value: 2, label: "2" },
  { value: 3, label: "3" },
  { value: 4, label: "4" },
  { value: 5, label: "5" },
  { value: 6, label: "6" },
  { value: 7, label: "7" },
  { value: 8, label: "8" },
  { value: 9, label: "9" },
  { value: 10, label: "10" }
];

class MedicationPlan extends React.Component {
  constructor() {
    super();

    this.appendChild = this.appendChild.bind(this);
    this.handleDateIntervalChange = this.handleDateIntervalChange.bind(this);
    this.handleDrugPlanChange = this.handleDrugPlanChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

    this.state = {
      children: [],

      errorStatus: 0,
      error: null,
      formIsValid: false,

      drugsNumber: 0,
      intervalControl: {
        start: null,
        end: null,
        valid: false,
        validationRules: {
          isRequired: true
        }
      },
      drugPlansControl: []
    };
  }

  componentDidMount() {
    this.fetchDrugs();
  }

  fetchDrugs() {
    getDrugs()
      .then(response => {
        let foundDrugs = [];
        response.forEach(drug => {
          foundDrugs.push({
            value: drug.id,
            label: drug.name
          });
        });
        this.setState({
          drugs: foundDrugs
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

  /**
   * Format a date to sql string type
   * @param {Date} date
   */
  formatDate(date) {
    let month = date.getMonth(),
      day = date.getDate();

    if (month < 10) {
      month = "0" + month;
    }

    if (day < 10) {
      day = "0" + day;
    }

    return date.getFullYear() + "-" + month + "-" + day;
  }

  formatIntakeInterval(date) {
    let hour = date.getHours(),
      minute = date.getMinutes();

    if (hour < 10) {
      hour = "0" + hour;
    }
    if (minute < 10) {
      minute = "0" + minute;
    }

    return hour + ":" + minute;
  }

  /**
   * Handle the change in the interval phase
   * @param {*} event
   */
  handleDateIntervalChange(event) {
    let intervalControl = this.state.intervalControl;

    console.log("Final interval control:");
    console.log(intervalControl);
    console.log(event);

    this.setState({
      intervalControl: intervalControl
    });
  }

  handleDrugPlanChange(event) {
    let updatedDrugPlansControl = { ...this.state.drugPlansControl },
      updatedTimeControl = { ...this.state.intervalControl };

    let name = ["intervalControl"];
    if (event.target.name !== undefined) {
      name = event.target.name.split(" ");
    }

    // separam dupa nume, "drug", "repetitions", "from" si "to" pt fiecare componenta modificam altceva
    switch (name[0]) {
      case "drug": {
        const index = parseInt(name[1], 10);
        updatedDrugPlansControl[index].drugId = event.target.value;
        break;
      }
      case "repetitions": {
        const index = parseInt(name[1], 10);
        updatedDrugPlansControl[index].repetitions = event.target.value;
        // create empty timeIntervals array with length = repetitions
        updatedDrugPlansControl[index].timeIntervals = [];
        for (let i = 0; i < event.target.value; i++) {
          let timeInterval = {
            start: null,
            end: null,
            valid: false,
            touched: false,
            validationRules: {
              isRequired: true
            }
          };
          updatedDrugPlansControl[index].timeIntervals.push(timeInterval);
        }
        break;
      }
      case "from": {
        const row = parseInt(name[1], 10),
          column = parseInt(name[2], 10),
          start = event.target.value;
        updatedDrugPlansControl[row].timeIntervals[
          column
        ].start = this.formatIntakeInterval(start);

        // mark valid for this timeInterval after checking
        if (
          updatedDrugPlansControl[row].timeIntervals[column].end != null &&
          updatedDrugPlansControl[row].timeIntervals[column].start <
            updatedDrugPlansControl[row].timeIntervals[column].end
        ) {
          updatedDrugPlansControl[row].timeIntervals[column].valid = true;
        } else {
          updatedDrugPlansControl[row].timeIntervals[column].valid = false;
        }

        break;
      }
      case "to": {
        const row = parseInt(name[1], 10),
          column = parseInt(name[2], 10),
          end = event.target.value;
        updatedDrugPlansControl[row].timeIntervals[
          column
        ].end = this.formatIntakeInterval(end);

        // mark valid for this timeInterval after checking
        if (
          updatedDrugPlansControl[row].timeIntervals[column].start != null &&
          updatedDrugPlansControl[row].timeIntervals[column].start <
            updatedDrugPlansControl[row].timeIntervals[column].end
        ) {
          updatedDrugPlansControl[row].timeIntervals[column].valid = true;
        } else {
          updatedDrugPlansControl[row].timeIntervals[column].valid = false;
        }

        break;
      }
      case "intervalControl": {
        updatedTimeControl.start = event.target.value.start;
        updatedTimeControl.end = event.target.value.end;
        if (
          Date.parse(event.target.value.start) <
          Date.parse(event.target.value.end)
        ) {
          updatedTimeControl.valid = true;
        } else {
          updatedTimeControl.valid = false;
        }
        break;
      }
      default:
        break;
    }

    // vezi ce s-a modificat in functie de nume si modifica in updateddrugPlansControl

    // valideaza totul (inclusiv dateinterval) si seteaza drugPlanControl ca valid sau nu
    // (parcurge toate obj din updateddrugPlansControl si ai valoarea updateddrugPlansControl.valid)
    console.log("Update drugPlans:");
    console.log(updatedDrugPlansControl);

    // Daca toate timeIntervals sunt valide, atunci tot formul e valid
    let formIsValid = true,
      isTimeIntervalsValid = true;

    console.log(updatedTimeControl);

    for (let i = 0; i < this.state.drugsNumber; i++) {
      if (updatedDrugPlansControl[i].repetitions === 0) {
        isTimeIntervalsValid = false;
        continue;
      }
      updatedDrugPlansControl[i].timeIntervals.map(timeInterval => {
        if (timeInterval.valid === false) {
          isTimeIntervalsValid = false;
        }
      });
    }

    formIsValid = updatedTimeControl.valid && isTimeIntervalsValid;

    console.log("isTimeIntervalsValid");
    console.log(isTimeIntervalsValid);
    console.log("updatedTimeControl.valid");
    console.log(updatedTimeControl.valid);
    console.log(formIsValid);

    this.setState({
      drugPlansControl: updatedDrugPlansControl,
      intervalControl: updatedTimeControl,
      formIsValid: formIsValid
    });
  }

  appendChild(event) {
    let children = [],
      drugPlansControl = [],
      drugPlanControl = {};
    if (this.state !== undefined) {
      let children = { ...children };
    }

    // add start and end date for the plan
    children.push(
      <DateInterval
        name="intervalControl "
        onChange={this.handleDrugPlanChange}
      />
    );

    for (let i = 0; i < event.target.value; i++) {
      children.push(
        <DrugPlan
          number={i} // send the key of the index
          drugs={this.state.drugs}
          onChange={this.handleDrugPlanChange}
        />
      );

      // creem si controalele pt fiecae, i.e., array
      drugPlanControl = {
        valid: false,
        touched: false,
        validationRules: {
          isRequired: true
        },
        drugId: null,
        repetitions: 0,
        timeIntervals: []
      };
      drugPlansControl.push(drugPlanControl);
    }

    this.setState({
      drugsNumber: event.target.value,
      children: children,
      drugPlansControl: drugPlansControl
    });
  }

  handleSubmit(event) {
    event.preventDefault();

    const { patientId } = this.props.match.params;

    let medicationPlan = {
      start: this.state.intervalControl.start,
      end: this.state.intervalControl.end,
      doctorId: this.props.currentUser.id,
      patientId: patientId,
      drugsPlanIntervals: []
    };

    for (let i = 0; i < this.state.drugsNumber; i++) {
      let drugPlanInterval = {
        drugId: this.state.drugPlansControl[i].drugId,
        intakeIntervals: []
      };
      for (let j = 0; j < this.state.drugPlansControl[i].repetitions; j++) {
        let intakeInterval = {
          startHour:
            this.state.drugPlansControl[i].timeIntervals[j].start + ":00",
          endHour: this.state.drugPlansControl[i].timeIntervals[j].end + ":00"
        };
        drugPlanInterval.intakeIntervals.push(intakeInterval);
      }
      medicationPlan.drugsPlanIntervals.push(drugPlanInterval);
    }

    console.log("New Medication plan:");
    console.log(medicationPlan);

    createMedicationPlan(medicationPlan)
      .then(response => {
        if (response == null) {
          notification.error({
            message: "Medical System",
            description:
              "Sorry! Medical Plan could not be created. Please try again!"
          });
          return;
        }
        notification.success({
          message: "Medical System",
          description: "Medical Plan inserted"
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
              error.message || "Sorry! Something went wrong. Please try again!"
          });
        }
      });
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <p>Select number of drugs:</p>
        <SelectInput
          name="drugNumber"
          data={options}
          onChange={this.appendChild}
        />
        <div>{this.state.children.map(child => child)}</div>
        <Button
          variant="success"
          type={"submit"}
          disabled={!this.state.formIsValid}
        >
          Submit
        </Button>
      </form>
    );
  }
}

export default MedicationPlan;
