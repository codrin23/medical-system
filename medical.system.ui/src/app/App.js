import React, { Component } from "react";
import "./App.css";
import "./App.scss";
import { Route, withRouter, Switch } from "react-router-dom";

import { getCurrentUser } from "../util/APIUtils";
import { ACCESS_TOKEN } from "../constants";

import Login from "../user/login/Login";
import Profile from "../user/profile/Profile";
import AppHeader from "../common/AppHeader";
import NotFound from "../common/NotFound";
import UnauthorizedPage from "../common/UnauthorizedPage";
import LoadingIndicator from "../common/LoadingIndicator";
import Home from "../home/home";
import PrivateRoute from "../common/PrivateRoute";

import { Layout, notification } from "antd";
import Patients from "../pacients/Patients";
import PatientForm from "../pacients/PatientForm";
import Caregivers from "../caregivers/Caregivers";
import CaregiverForm from "../caregivers/CaregiverForm";
import Drugs from "../drugs/Drugs";
import DrugForm from "../drugs/DrugForm";
import MedicationPlan from "../medicationplan/MedicationPlan";
import MedicalPlanPatient from "../pacients/medicationPlan/MedicationPlan";
import Stomp from "stomp-websocket";
import SockJS from "sockjs-client";
const { Content } = Layout;

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false
    };
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);

    notification.config({
      placement: "topRight",
      top: 70,
      duration: 3
    });
  }

  registerWebSocketForCaregiver(currentUser) {
    if (currentUser.role === "ROLE_CAREGIVER") {
      var wsocket = new SockJS("http://localhost:8085/sensordata");
      var client = Stomp.over(wsocket);
      console.log("jwt client");
      console.log(localStorage.getItem(ACCESS_TOKEN));
      client.connect(
        { authorization: "Bearer " + localStorage.getItem(ACCESS_TOKEN) },
        function(frame) {
          client.subscribe(
            "/topic/caregiver/" + currentUser.username,
            function(message) {
              console.log(message.body);
              notification.success({
                message: "Medical System",
                description: message.body
              });
            },
            { authorization: "Bearer " + localStorage.getItem(ACCESS_TOKEN) }
          );
        }
      );
    }
  }

  loadCurrentUser() {
    this.setState({
      isLoading: true
    });
    getCurrentUser()
      .then(response => {
        console.log("Current user:");
        console.log(response);

        this.registerWebSocketForCaregiver(response);

        this.setState({
          currentUser: response,
          isAuthenticated: true,
          isLoading: false
        });
      })
      .catch(error => {
        this.setState({
          isLoading: false
        });
      });
  }

  componentDidMount() {
    this.loadCurrentUser();
  }

  handleLogout(
    redirectTo = "/",
    notificationType = "success",
    description = "You're successfully logged out."
  ) {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);

    notification[notificationType]({
      message: "Medical System",
      description: description
    });
  }

  handleLogin() {
    notification.success({
      message: "Medical System",
      description: "You're successfully logged in."
    });
    this.loadCurrentUser();
    this.props.history.push("/");
  }

  render() {
    if (this.state.isLoading) {
      return <LoadingIndicator />;
    }
    return (
      <Layout className="app-container">
        <AppHeader
          isAuthenticated={this.state.isAuthenticated}
          currentUser={this.state.currentUser}
          onLogout={this.handleLogout}
        />
        <Content className="app-content">
          <div className="container">
            <Switch>
              <Route exact path="/" render={() => <Home />} />
              <Route
                path="/login"
                render={props => (
                  <Login onLogin={this.handleLogin} {...props} />
                )}
              />
              <Route
                path="/users/:username"
                render={props => (
                  <Profile
                    isAuthenticated={this.state.isAuthenticated}
                    currentUser={this.state.currentUser}
                    {...props}
                  />
                )}
              />
              <Route
                path="/unauthorized"
                render={props => <UnauthorizedPage />}
              />
              <PrivateRoute
                authenticated={this.state.isAuthenticated}
                currentUser={this.state.currentUser}
                path="/doctor/patients"
                component={Patients}
                handleLogout={this.handleLogout}
              ></PrivateRoute>
              <PrivateRoute
                authenticated={this.state.isAuthenticated}
                currentUser={this.state.currentUser}
                path="/doctor/caregivers"
                component={Caregivers}
                handleLogout={this.handleLogout}
              ></PrivateRoute>
              <PrivateRoute
                authenticated={this.state.isAuthenticated}
                currentUser={this.state.currentUser}
                path="/doctor/drugs"
                component={Drugs}
                handleLogout={this.handleLogout}
              ></PrivateRoute>
              <PrivateRoute
                authenticated={this.state.isAuthenticated}
                currentUser={this.state.currentUser}
                path="/doctor/drug/:drugId"
                component={DrugForm}
                handleLogout={this.handleLogout}
              ></PrivateRoute>
              <PrivateRoute
                authenticated={this.state.isAuthenticated}
                currentUser={this.state.currentUser}
                path="/doctor/caregiver/:caregiverId"
                component={CaregiverForm}
                handleLogout={this.handleLogout}
              ></PrivateRoute>
              <PrivateRoute
                authenticated={this.state.isAuthenticated}
                currentUser={this.state.currentUser}
                path="/doctor/patient/:patientId"
                component={PatientForm}
                handleLogout={this.handleLogout}
              ></PrivateRoute>
              <PrivateRoute
                authenticated={this.state.isAuthenticated}
                currentUser={this.state.currentUser}
                path="/doctor/medicationPlan/:patientId"
                component={MedicationPlan}
                handleLogout={this.handleLogout}
              ></PrivateRoute>

              <PrivateRoute
                authenticated={this.state.isAuthenticated}
                currentUser={this.state.currentUser}
                path="/caregiver/patients"
                component={Patients}
                handleLogout={this.handleLogout}
              ></PrivateRoute>
              <PrivateRoute
                authenticated={this.state.isAuthenticated}
                currentUser={this.state.currentUser}
                path="/patient/medicationPlans"
                component={MedicalPlanPatient}
                handleLogout={this.handleLogout}
              ></PrivateRoute>

              <Route component={NotFound} />
            </Switch>
          </div>
        </Content>
      </Layout>
    );
  }
}

export default withRouter(App);
