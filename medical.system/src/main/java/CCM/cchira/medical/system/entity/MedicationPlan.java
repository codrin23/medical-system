package CCM.cchira.medical.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
public class MedicationPlan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Basic
    private java.sql.Date start;

    @Basic
    private java.sql.Date end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @OneToMany(
            mappedBy = "medicationPlan",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<IntakeIntervals> intakeIntervals;

    public MedicationPlan() {
    }

    public MedicationPlan(Date start, Date end, Patient patient, Doctor doctor) {
        this.start = start;
        this.end = end;
        this.patient = patient;
        this.doctor = doctor;
    }

    public MedicationPlan(Date start, Date end, Patient patient, Doctor doctor, List<IntakeIntervals> intakeIntervals) {
        this.start = start;
        this.end = end;
        this.patient = patient;
        this.doctor = doctor;
        this.intakeIntervals = intakeIntervals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<IntakeIntervals> getIntakeIntervals() {
        return intakeIntervals;
    }

    public void setIntakeIntervals(List<IntakeIntervals> intakeIntervals) {
        this.intakeIntervals = intakeIntervals;
    }

    @Override
    public String toString() {
        return "MedicationPlan{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", intakeIntervals=" + intakeIntervals +
                '}';
    }
}
