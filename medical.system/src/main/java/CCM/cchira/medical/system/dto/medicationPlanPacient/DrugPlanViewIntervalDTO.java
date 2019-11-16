package CCM.cchira.medical.system.dto.medicationPlanPacient;

import CCM.cchira.medical.system.dto.IntakeIntervalsDTO;

import java.util.List;

public class DrugPlanViewIntervalDTO {
    private String drugName;
    private List<IntakeIntervalsDTO> intakeIntervals;

    public DrugPlanViewIntervalDTO() {
    }

    public DrugPlanViewIntervalDTO(String drugName, List<IntakeIntervalsDTO> intakeIntervals) {
        this.drugName = drugName;
        this.intakeIntervals = intakeIntervals;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public List<IntakeIntervalsDTO> getIntakeIntervals() {
        return intakeIntervals;
    }

    public void setIntakeIntervals(List<IntakeIntervalsDTO> intakeIntervals) {
        this.intakeIntervals = intakeIntervals;
    }

    @Override
    public String toString() {
        return "DrugPlanViewIntervalDTO{" +
                "drugName='" + drugName + '\'' +
                ", intakeIntervals=" + intakeIntervals +
                '}';
    }
}
