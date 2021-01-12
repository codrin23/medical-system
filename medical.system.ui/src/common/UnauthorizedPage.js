import React, { Component } from "react";
import "./NotFound.css";
import { Link } from "react-router-dom";
import { Button, notification } from "antd";

class UnauthorizedPage extends Component {
  render() {
    notification.error({
      message: "Medical System",
      description: "Not authorized!"
    });
    return (
      <div className="page-not-found">
        <h1 className="title">401</h1>
        <div className="desc">
          You do not have the rights to access this resource
        </div>
        <Link to="/">
          <Button className="go-back-btn" type="primary" size="large">
            Go Back
          </Button>
        </Link>
      </div>
    );
  }
}

export default UnauthorizedPage;
