import React from "react";
import "./fields.css";
import Select from "react-select";

class SelectInput extends React.Component {
  constructor(props) {
    super(props);

    this.onChangeFunction = this.onChangeFunction.bind(this);

    this.state = {
      name: this.props.name,
      placeholder: this.props.placeholder,
      value: this.props.value,
      onChange: this.props.handleChange,
      touched: this.props.touched,
      valid: this.props.valid
    };
  }

  onChangeFunction = event => {
    this.props.onChange(event);
  };

  render() {
    return (
      <div className="form-group">
        <Select
          name={this.props.name}
          isSearchable={true}
          onChange={val => {
            this.onChangeFunction({
              target: { name: this.props.name, value: val.value }
            });
          }}
          options={this.props.data}
        />
      </div>
    );
  }
}

export default SelectInput;
