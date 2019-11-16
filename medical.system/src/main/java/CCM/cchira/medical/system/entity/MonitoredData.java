package CCM.cchira.medical.system.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class MonitoredData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Basic
    private java.sql.Timestamp start;

    @Basic
    private java.sql.Timestamp end;

    private String activityLabel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public MonitoredData(Timestamp start, Timestamp end, String activityLabel, Patient patient) {
        this.start = start;
        this.end = end;
        this.activityLabel = activityLabel;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
