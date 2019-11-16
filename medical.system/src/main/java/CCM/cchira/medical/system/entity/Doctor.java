package CCM.cchira.medical.system.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Doctor extends User {

    @OneToMany(cascade= CascadeType.ALL)
    @JoinTable(name="doctor_has_patient",joinColumns=@JoinColumn(name="doctor_id"),inverseJoinColumns=@JoinColumn(name="patient_id"))
    private List<Patient> patientList;

    public Doctor() {
    }

    public Doctor(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public Doctor(String userName, String password, String firstName, String lastName, Date birthDate, Gender gender, Address address, boolean isActive, String email, List<Patient> patientList) {
        super(userName, password, firstName, lastName, birthDate, gender, address, isActive, email);
        this.patientList = patientList;
    }



    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }



}
