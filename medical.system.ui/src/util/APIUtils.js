import { API_BASE_URL, ACCESS_TOKEN } from "../constants";

const request = options => {
  console.log("jwt");
  console.log(localStorage.getItem(ACCESS_TOKEN));
  const headers = new Headers({
    "Content-Type": "application/json"
  });

  if (localStorage.getItem(ACCESS_TOKEN)) {
    headers.append(
      "Authorization",
      "Bearer " + localStorage.getItem(ACCESS_TOKEN)
    );
  }

  const defaults = { headers: headers };
  options = Object.assign({}, defaults, options);

  return fetch(options.url, options).then(response =>
    response.json().then(json => {
      if (!response.ok) {
        return Promise.reject(json);
      }
      return json;
    })
  );
};

export function login(loginRequest) {
  return request({
    url: API_BASE_URL + "/auth/signin",
    method: "POST",
    body: JSON.stringify(loginRequest)
  });
}

export function signup(signupRequest) {
  return request({
    url: API_BASE_URL + "/auth/signup",
    method: "POST",
    body: JSON.stringify(signupRequest)
  });
}

export function checkUsernameAvailability(username) {
  return request({
    url: API_BASE_URL + "/user/checkUsernameAvailability?username=" + username,
    method: "GET"
  });
}

export function checkEmailAvailability(email) {
  return request({
    url: API_BASE_URL + "/user/checkEmailAvailability?email=" + email,
    method: "GET"
  });
}

export function getCurrentUser() {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: API_BASE_URL + "/user/me",
    method: "GET"
  });
}

export function getUserProfile(username) {
  return request({
    url: API_BASE_URL + "/users/" + username,
    method: "GET"
  });
}

// /doctor/* requests

export function getDoctors() {
  return request({
    url: API_BASE_URL + "/doctor/doctors/",
    method: "GET"
  });
}

export function getDoctorPatients(doctorId) {
  return request({
    url: API_BASE_URL + "/doctor/patients/" + doctorId,
    method: "GET"
  });
}

export function getCaregivers() {
  return request({
    url: API_BASE_URL + "/doctor/caregivers",
    method: "GET"
  });
}

export function getCaregiver(caregiverId) {
  return request({
    url: API_BASE_URL + "/doctor/caregiver/" + caregiverId,
    method: "GET"
  });
}

export function insertCaregiver(caregiver) {
  return request({
    url: API_BASE_URL + "/doctor/caregiver",
    method: "POST",
    body: JSON.stringify(caregiver)
  });
}

export function updateCaregiver(caregiver) {
  return request({
    url: API_BASE_URL + "/doctor/caregiver",
    method: "PUT",
    body: JSON.stringify(caregiver)
  });
}

export function deleteCaregiver(caregiverId) {
  return request({
    url: API_BASE_URL + "/doctor/caregiver",
    method: "DELETE",
    body: JSON.stringify(caregiverId)
  });
}

export function insertDoctorPatient(doctorId, patient) {
  return request({
    url: API_BASE_URL + "/doctor/patient/" + doctorId,
    method: "POST",
    body: JSON.stringify(patient)
  });
}

export function getDoctorPatient(patientId) {
  return request({
    url: API_BASE_URL + "/doctor/patient/" + patientId,
    method: "GET"
  });
}

export function deletePatient(patientId) {
  return request({
    url: API_BASE_URL + "/doctor/patient/" + patientId,
    method: "DELETE"
  });
}

export function updatePatient(patient) {
  return request({
    url: API_BASE_URL + "/doctor/patient",
    method: "PUT",
    body: JSON.stringify(patient)
  });
}

export function getDrugs() {
  return request({
    url: API_BASE_URL + "/doctor/drugs",
    method: "GET"
  });
}

export function getDrug(drugId) {
  return request({
    url: API_BASE_URL + "/doctor/drug/" + drugId,
    method: "GET"
  });
}

export function insertDrug(drug) {
  return request({
    url: API_BASE_URL + "/doctor/drug",
    method: "POST",
    body: JSON.stringify(drug)
  });
}

export function updateDrug(drug) {
  return request({
    url: API_BASE_URL + "/doctor/drug",
    method: "PUT",
    body: JSON.stringify(drug)
  });
}

export function deleteDrugById(drugId) {
  return request({
    url: API_BASE_URL + "/doctor/drug/" + drugId,
    method: "DELETE"
  });
}

export function createMedicationPlan(medicationPlan) {
  return request({
    url: API_BASE_URL + "/doctor/medicationPlan",
    method: "POST",
    body: JSON.stringify(medicationPlan)
  });
}

export function getCaregiverPatients(caregiverId) {
  return request({
    url: API_BASE_URL + "/caregiver/patients/" + caregiverId,
    method: "GET"
  });
}

export function getMedicalPlans(patientId) {
  return request({
    url: API_BASE_URL + "/patient/medicationPlans/" + patientId,
    method: "GET"
  });
}
