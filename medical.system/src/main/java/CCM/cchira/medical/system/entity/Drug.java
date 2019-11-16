package CCM.cchira.medical.system.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    private String name;
    private String sideEffects;
    private String dosage;

    @OneToMany(
            mappedBy = "drug",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<IntakeIntervals> intakeIntervalsList;

    public Drug() {
    }

    public Drug(String name, String sideEffects) {
        this.name = name;
        this.sideEffects = sideEffects;
    }

    public Drug(String name, String sideEffects, String dosage) {
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
    }

    public Drug(String name, String sideEffects, String dosage, List<IntakeIntervals> intakeIntervalsList) {
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
        this.intakeIntervalsList = intakeIntervalsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public List<IntakeIntervals> getIntakeIntervalsList() {
        return intakeIntervalsList;
    }

    public void setIntakeIntervalsList(List<IntakeIntervals> intakeIntervalsList) {
        this.intakeIntervalsList = intakeIntervalsList;
    }
}
