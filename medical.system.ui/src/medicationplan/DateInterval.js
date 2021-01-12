import React from "react";
import "react-datepicker/dist/react-datepicker.css";
import { DateRangePicker } from "@progress/kendo-react-dateinputs";
import "@progress/kendo-theme-default/dist/all.css";

const checkInValidationMessage = "The interval must be selected";

class DateInterval extends React.Component {
  constructor(props) {
    super(props);

    this.setStartDate = this.setStartDate.bind(this);
    this.setEndDate = this.setEndDate.bind(this);

    this.state = {
      startDate: new Date(),
      endDate: new Date()
    };
  }

  setStartDate(date) {
    this.setState({
      startDate: date
    });
  }

  setEndDate(date) {
    this.setState({
      endDate: date
    });
  }

  render() {
    return (
      <div>
        <p>Medication Plan interval:</p>
        <DateRangePicker
          name="intervalControl"
          required={true}
          validationMessage={checkInValidationMessage}
          onChange={this.props.onChange}
        />
      </div>
    );
  }
}

export default DateInterval;
