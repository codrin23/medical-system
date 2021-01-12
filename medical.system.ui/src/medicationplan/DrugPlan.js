import React from "react";
import SelectInput from "../person-data/person/fields/SelectInput";
import IntakeInterval from "./IntakeInterval";

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

class DrugPlan extends React.Component {
  constructor(props) {
    super(props);

    this.appendChild = this.appendChild.bind(this);

    this.state = {
      children: []
    };
  }

  appendChild(event) {
    this.props.onChange(event);
    let children = [];
    if (this.state !== undefined) {
      let children = { ...children };
    }

    for (let i = 0; i < event.target.value; i++) {
      children.push(
        <IntakeInterval
          row={this.props.number}
          number={i}
          name={"intakeInterval " + this.props.number}
          onChange={this.props.onChange}
        />
      );
    }

    this.setState({
      children: children
    });
  }

  render() {
    console.log("props");
    console.log(this.props);
    return (
      <div>
        <p>Select pacient's drug:</p>
        <SelectInput
          name={"drug " + this.props.number}
          data={this.props.drugs}
          onChange={this.props.onChange}
        />
        <p>How many times a day:</p>
        <SelectInput
          name={"repetitions " + this.props.number}
          data={options}
          onChange={this.appendChild}
        />
        <div>{this.state.children.map(child => child)}</div>
      </div>
    );
  }
}

export default DrugPlan;
