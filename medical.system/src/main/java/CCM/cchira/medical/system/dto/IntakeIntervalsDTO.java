package CCM.cchira.medical.system.dto;

public class IntakeIntervalsDTO {
    private String endHour;
    private String startHour;

    public IntakeIntervalsDTO(String endHour, String startHour) {
        this.endHour = endHour;
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    @Override
    public String toString() {
        return "IntakeIntervalsDTO{" +
                "endHour=" + endHour +
                ", startHour=" + startHour +
                '}';
    }
}
