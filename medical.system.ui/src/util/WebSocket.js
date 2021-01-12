import React, { Component } from "react";
import { notification } from "antd";
import { SockJsClient } from "react-stomp";

const WEB_SOCKET_ENDPOINT = "http://localhost:8085/stomp";

class WebSocket extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return <div></div>;
  }
}

export default WebSocket;
