import React from "react";
import { TimePicker } from "@progress/kendo-react-dateinputs";
import "@progress/kendo-theme-default/dist/all.css";

const checkInValidationMessage = "The Start time is required.";
const checkOutValidationMessage = "The To time is required.";

class IntakeInterval extends React.Component {
  render() {
    return (
      <div>
        <fieldset>
          <legend>Please select the time interval:</legend>
          <label>
            <span>From</span>
            <TimePicker
              format="HH:mm"
              width="100%"
              name={"from " + this.props.row + " " + this.props.number}
              required={true}
              validationMessage={checkInValidationMessage}
              onChange={this.props.onChange}
            />
          </label>
          <label>
            <span>To</span>
            <TimePicker
              format="HH:mm"
              width="100%"
              name={"to " + this.props.row + " " + this.props.number}
              required={true}
              validationMessage={checkOutValidationMessage}
              onChange={this.props.onChange}
            />
          </label>
        </fieldset>
      </div>
    );
  }
}

export default IntakeInterval;
