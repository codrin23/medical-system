package CCM.cchira.medical.system.dto.medicationPlanPacient;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class MedicationPlanViewDTO implements Serializable {
    private java.sql.Date start;
    private java.sql.Date end;
    private String doctorName;
    private List<DrugPlanViewIntervalDTO> drugPlanInterval;

    public MedicationPlanViewDTO() {
    }

    public MedicationPlanViewDTO(Date start, Date end, String doctorName, List<DrugPlanViewIntervalDTO> drugPlanInterval) {
        this.start = start;
        this.end = end;
        this.doctorName = doctorName;
        this.drugPlanInterval = drugPlanInterval;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public List<DrugPlanViewIntervalDTO> getDrugPlanInterval() {
        return drugPlanInterval;
    }

    public void setDrugPlanInterval(List<DrugPlanViewIntervalDTO> drugPlanInterval) {
        this.drugPlanInterval = drugPlanInterval;
    }

    @Override
    public String toString() {
        return "MedicationPlanViewDTO{" +
                "start=" + start +
                ", end=" + end +
                ", doctorName='" + doctorName + '\'' +
                ", drugPlanInterval=" + drugPlanInterval +
                '}';
    }
}
