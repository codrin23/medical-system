package CCM.cchira.medical.system.dto;

import java.sql.Date;
import java.util.List;

public class MedicationPlanDTO {

    private java.sql.Date start;
    private java.sql.Date end;
    private Integer patientId;
    private Integer doctorId;
    private List<DrugsPlanInterval> drugsPlanIntervals;

    public MedicationPlanDTO() {
    }

    public MedicationPlanDTO(Date start, Date end, Integer patientId, Integer doctorId, List<DrugsPlanInterval> drugsPlanIntervals) {
        this.start = start;
        this.end = end;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.drugsPlanIntervals = drugsPlanIntervals;
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public List<DrugsPlanInterval> getDrugsPlanIntervals() {
        return drugsPlanIntervals;
    }

    public void setDrugsPlanIntervals(List<DrugsPlanInterval> drugsPlanIntervals) {
        this.drugsPlanIntervals = drugsPlanIntervals;
    }

    @Override
    public String toString() {
        return "MedicationPlanDTO{" +
                "start=" + start +
                ", end=" + end +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", drugsPlanIntervals=" + drugsPlanIntervals +
                '}';
    }
}
