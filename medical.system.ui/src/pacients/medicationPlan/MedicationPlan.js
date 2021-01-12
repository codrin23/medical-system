import React from "react";

import { getMedicalPlans } from "../../util/APIUtils";
import { notification } from "antd";
import MedTable from "./MedicationTable";

class MedicationPlan extends React.Component {
  constructor(props) {
    super(props);

    this.refresh = this.refresh.bind(this);

    // an array of medication plans to be displayed
    this.state = {
      medicationPlans: [],
      touched: false,
      display: true,
      options: []
    };
  }

  componentDidMount() {
    this.fetchMedicalPlans();
  }

  fetchMedicalPlans() {
    getMedicalPlans(this.props.currentUser.id)
      .then(response => {
        let medicationPlans = [];
        response.forEach(medicalPlan => {
          let medicationPlan = {
            start: medicalPlan.start,
            end: medicalPlan.end,
            doctorName: medicalPlan.doctorName,
            /* E.g.:
                    {
                        drugName: "Paracetamol",
                        start: "00:00",
                        end: "03:00"
                    }
                */
            drugPlanIntervals: []
          };

          medicalPlan.drugPlanInterval.forEach(drugPlan => {
            drugPlan.intakeIntervals.forEach(intakeInterval => {
              let drugPlanInterval = {
                drugName: drugPlan.drugName,
                endHour: intakeInterval.endHour,
                startHour: intakeInterval.startHour
              };
              medicationPlan.drugPlanIntervals.push(drugPlanInterval);
            });
          });
          medicationPlans.push(medicationPlan);
        });
        let options = [];
        for (let i = 0; i < medicationPlans.length; i++) {
          options.push({
            value: i,
            label: i + 1
          });
        }
        this.setState({
          medicationPlans: medicationPlans,
          touched: true,
          options: options
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

  refresh() {
    this.setState({
      display: false
    });
  }

  render() {
    return (
      <div>
        {this.state.display && (
          <h1>
            {" "}
            You have {this.state.medicationPlans.length} available medication
            plans!
          </h1>
        )}

        {this.state.touched && (
          <MedTable
            medicationPlans={this.state.medicationPlans}
            options={this.state.options}
            onRefresh={this.refresh}
          />
        )}
      </div>
    );
  }
}

export default MedicationPlan;
