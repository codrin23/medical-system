package CCM.cchira.medical.system.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Patient extends User {

    @ManyToOne
    @JoinColumn(name="caregiver_id")
    private Caregiver caregiver;

    @ManyToOne
    @JoinColumn(name="doctor_id")
    private Doctor doctor;

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MedicationPlan> medicationPlanList;

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MonitoredData> monitoredDataList;

    public Patient() {
    }

    public Patient(Caregiver caregiver, Doctor doctor) {
        this.caregiver = caregiver;
        this.doctor = doctor;
    }

    public Patient(String userName, String password, String firstName, String lastName, Date sqlDate, Gender gender, Address address, boolean isActive, String email, Caregiver caregiver, Doctor doctor) {
        super(userName, password, firstName, lastName, sqlDate, gender, address, isActive, email);
        this.caregiver = caregiver;
        this.doctor = doctor;
    }

    public Patient(Caregiver caregiver, Doctor doctor, List<MedicationPlan> medicationPlanList) {
        this.caregiver = caregiver;
        this.doctor = doctor;
        this.medicationPlanList = medicationPlanList;
    }

    public Patient(String userName, String password, String firstName, String lastName, Date birthDate, Gender gender, Address address, boolean isActive, String email, Caregiver caregiver, Doctor doctor, List<MedicationPlan> medicationPlanList) {
        super(userName, password, firstName, lastName, birthDate, gender, address, isActive, email);
        this.caregiver = caregiver;
        this.doctor = doctor;
        this.medicationPlanList = medicationPlanList;
    }

    public Patient(Caregiver caregiver, Doctor doctor, List<MedicationPlan> medicationPlanList, List<MonitoredData> monitoredDataList) {
        this.caregiver = caregiver;
        this.doctor = doctor;
        this.medicationPlanList = medicationPlanList;
        this.monitoredDataList = monitoredDataList;
    }

    public Patient(String userName, String password, String firstName, String lastName, Date birthDate, Gender gender, Address address, boolean isActive, String email, Caregiver caregiver, Doctor doctor, List<MedicationPlan> medicationPlanList, List<MonitoredData> monitoredDataList) {
        super(userName, password, firstName, lastName, birthDate, gender, address, isActive, email);
        this.caregiver = caregiver;
        this.doctor = doctor;
        this.medicationPlanList = medicationPlanList;
        this.monitoredDataList = monitoredDataList;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<MedicationPlan> getMedicationPlanList() {
        return medicationPlanList;
    }

    public void setMedicationPlanList(List<MedicationPlan> medicationPlanList) {
        this.medicationPlanList = medicationPlanList;
    }

    public List<MonitoredData> getMonitoredDataList() {
        return monitoredDataList;
    }

    public void setMonitoredDataList(List<MonitoredData> monitoredDataList) {
        this.monitoredDataList = monitoredDataList;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "caregiver=" + caregiver +
                ", doctor=" + doctor +
                ", medicationPlanList=" + medicationPlanList +
                '}';
    }
}
