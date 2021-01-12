import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import "./AppHeader.css";
import doctorIcon from "../images/doctor.svg";
import drugIcon from "../images/caregiver.svg";
import caregiverIcon from "../images/drug.svg";
import { Layout, Menu, Dropdown, Icon } from "antd";
const Header = Layout.Header;

class AppHeader extends Component {
  constructor(props) {
    super(props);
    this.state = {
      removeUsername: null
    };
    this.handleMenuClick = this.handleMenuClick.bind(this);
  }

  handleMenuClick({ key }) {
    if (key === "logout") {
      this.props.onLogout();
    }
    if (key === "getUsers") {
      this.props.onGetUsers();
    }
    if (key === "getPacients") {
      this.props.onGetTvseries();
    }
    if (key === "getCaregivers") {
      this.props.onGetCurrentUserTvseries();
    }
  }

  render() {
    let menuItems;
    if (this.props.currentUser) {
      switch (this.props.currentUser.role) {
        case "ROLE_DOCTOR": {
          console.log("Avem doctor aicea");

          menuItems = [
            <Menu.Item key="/">
              <Link to="/">
                <Icon type="home" className="nav-icon" />
              </Link>
            </Menu.Item>,
            <Menu.Item key="getPacients">
              <Link to="/doctor/patients">
                <img src={doctorIcon} alt="poll" className="poll-icon" />
              </Link>
            </Menu.Item>,
            <Menu.Item key="getCaregivers">
              <Link to="/doctor/caregivers">
                <img src={caregiverIcon} alt="poll" className="poll-icon" />
              </Link>
            </Menu.Item>,
            <Menu.Item key="getDrugs">
              <Link to="/doctor/drugs">
                <img src={drugIcon} alt="poll" className="poll-icon" />
              </Link>
            </Menu.Item>,
            <Menu.Item key="/profile" className="profile-menu">
              <ProfileDropdownMenu
                currentUser={this.props.currentUser}
                handleMenuClick={this.handleMenuClick}
              />
            </Menu.Item>
          ];

          break;
        }
        case "ROLE_CAREGIVER": {
          console.log("Avem caregiver aicea");
          menuItems = [
            <Menu.Item key="/">
              <Link to="/">
                <Icon type="home" className="nav-icon" />
              </Link>
            </Menu.Item>,
            <Menu.Item key="getPacients">
              <Link to="/caregiver/patients">
                <img src={doctorIcon} alt="poll" className="poll-icon" />
              </Link>
            </Menu.Item>,
            <Menu.Item key="/profile" className="profile-menu">
              <ProfileDropdownMenu
                currentUser={this.props.currentUser}
                handleMenuClick={this.handleMenuClick}
              />
            </Menu.Item>
          ];

          break;
        }
        case "ROLE_PATIENT": {
          menuItems = [
            <Menu.Item key="/">
              <Link to="/">
                <Icon type="home" className="nav-icon" />
              </Link>
            </Menu.Item>,
            <Menu.Item key="getDrugs">
              <Link to="/patient/medicationPlans">
                <img src={drugIcon} alt="poll" className="poll-icon" />
              </Link>
            </Menu.Item>,
            <Menu.Item key="/profile" className="profile-menu">
              <ProfileDropdownMenu
                currentUser={this.props.currentUser}
                handleMenuClick={this.handleMenuClick}
              />
            </Menu.Item>
          ];

          break;
        }
        default:
          break;
      }
    } else {
      menuItems = [
        <Menu.Item key="/login">
          <Link to="/login">Login</Link>
        </Menu.Item>
      ];
    }

    return (
      <Header className="app-header">
        <div className="container">
          <div className="app-title">
            <Link to="/">Medical System</Link>
          </div>
          <Menu
            className="app-menu"
            mode="horizontal"
            selectedKeys={[this.props.location.pathname]}
            style={{ lineHeight: "64px" }}
          >
            {menuItems}
          </Menu>
        </div>
      </Header>
    );
  }
}

function ProfileDropdownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Item key="user-info" className="dropdown-item" disabled>
        <div className="user-full-name-info">{props.currentUser.name}</div>
        <div className="username-info">@{props.currentUser.username}</div>
      </Menu.Item>
      <Menu.Divider />
      <Menu.Item key="profile" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Profile</Link>
      </Menu.Item>
      <Menu.Item key="logout" className="dropdown-item">
        Logout
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={() =>
        document.getElementsByClassName("profile-menu")[0]
      }
    >
      <a className="ant-dropdown-link">
        <Icon type="user" className="nav-icon" style={{ marginRight: 0 }} />{" "}
        <Icon type="down" />
      </a>
    </Dropdown>
  );
}

export default withRouter(AppHeader);
