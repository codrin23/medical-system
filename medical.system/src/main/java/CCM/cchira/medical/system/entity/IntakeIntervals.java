package CCM.cchira.medical.system.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="intake_intervals")
public class IntakeIntervals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Basic
    private java.sql.Time startHour;

    @Basic
    private java.sql.Time endHour;

    @ManyToOne(fetch = FetchType.LAZY)
    private Drug drug;

    @ManyToOne(fetch = FetchType.LAZY)
    private MedicationPlan medicationPlan;

    public IntakeIntervals() {
    }

    public IntakeIntervals(Time startHour, Time endHour, Drug drug, MedicationPlan medicationPlan) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.drug = drug;
        this.medicationPlan = medicationPlan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getStartHour() {
        return startHour;
    }

    public void setStartHour(Time startHour) {
        this.startHour = startHour;
    }

    public Time getEndHour() {
        return endHour;
    }

    public void setEndHour(Time endHour) {
        this.endHour = endHour;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public MedicationPlan getMedicationPlan() {
        return medicationPlan;
    }

    public void setMedicationPlan(MedicationPlan medicationPlan) {
        this.medicationPlan = medicationPlan;
    }

    @Override
    public String toString() {
        return "IntakeIntervals{" +
                "id=" + id +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", drug=" + drug +
                ", medicationPlan=" + medicationPlan +
                '}';
    }
}
