import React from "react";
import SelectInput from "../../person-data/person/fields/SelectInput";
import { Card, Col, Row } from "reactstrap";
import Table from "../../tables/table";

let columns = [
  {
    Header: "Drug Name",
    accessor: "drugName"
  },
  {
    Header: "From",
    accessor: "startHour"
  },
  {
    Header: "To",
    accessor: "endHour"
  }
];

let filters = [
  {
    accessor: "drugName"
  }
];

class MedTable extends React.Component {
  constructor(props) {
    super(props);

    this.displayTable = this.displayTable.bind(this);

    this.state = {
      loading: true,
      medicationPlan: {}
    };
  }

  displayTable(event) {
    let theIntervals = [];
    if (this.state.medicationPlan.drugPlanIntervals !== undefined) {
      theIntervals = this.state.medicationPlan.drugPlanIntervals.map(l =>
        Object.assign({}, l)
      );
    }

    this.props.medicationPlans[event.target.value].drugPlanIntervals.forEach(
      plan => theIntervals.push(plan)
    );

    const updatedMedicationPlan = {
      doctorName: this.props.medicationPlans[event.target.value].doctorName,
      start: this.props.medicationPlans[event.target.value].start,
      end: this.props.medicationPlans[event.target.value].end,
      drugPlanIntervals: theIntervals
    };

    this.setState({
      loading: false,
      medicationPlan: updatedMedicationPlan
    });

    this.props.onRefresh();
  }

  render() {
    const pStyle = {
      fontSize: "45px",
      textAlign: "center"
    };
    let pageSize = 5;
    return (
      <div>
        {!this.state.loading && (
          <div>
            <h2>
              Medication plan from Doctor {this.state.medicationPlan.doctorName}{" "}
              in the interval {this.state.medicationPlan.start} -{" "}
              {this.state.medicationPlan.end}
            </h2>
            <div>
              <Row>
                <Col>
                  <Card body>
                    <Table
                      data={this.state.medicationPlan.drugPlanIntervals}
                      columns={columns}
                      search={filters}
                      pageSize={pageSize}
                    />
                  </Card>
                </Col>
              </Row>
            </div>
          </div>
        )}
        {this.state.loading && (
          <div>
            <h2>Select medication plan to display:</h2>
            <SelectInput
              name="index"
              data={this.props.options}
              onChange={this.displayTable}
            />
          </div>
        )}
        {!this.state.loading && <h2 style={pStyle}>Take care!</h2>}
      </div>
    );
  }
}

export default MedTable;
