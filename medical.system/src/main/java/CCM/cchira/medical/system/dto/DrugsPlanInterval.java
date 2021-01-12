package CCM.cchira.medical.system.dto;

import java.util.List;

public class DrugsPlanInterval {
    private Integer drugId;
    private List<IntakeIntervalsDTO> intakeIntervals;

    public DrugsPlanInterval(Integer drugId, List<IntakeIntervalsDTO> intakeIntervals) {
        this.drugId = drugId;
        this.intakeIntervals = intakeIntervals;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public List<IntakeIntervalsDTO> getIntakeIntervals() {
        return intakeIntervals;
    }

    public void setIntakeIntervals(List<IntakeIntervalsDTO> intakeIntervals) {
        this.intakeIntervals = intakeIntervals;
    }

    @Override
    public String toString() {
        return "DrugsPlanInterval{" +
                "drugId=" + drugId +
                ", intakeIntervals=" + intakeIntervals +
                '}';
    }
}
