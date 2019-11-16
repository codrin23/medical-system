package CCM.cchira.medical.system.external.services.sensors;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.json.JSONException;
import org.json.JSONObject;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = MonitoredData.class)
public class MonitoredData {
    private long startTime;
    private long endTime;
    private String activityLabel;
    private long patientId;

    public MonitoredData() {
    }

    public MonitoredData(long date,
                         long date2,
                         String activityLabel,
                         long patientId) {
        this.startTime = date;
        this.endTime = date2;
        this.activityLabel = activityLabel;
        this.patientId = patientId;
    }

    public long getDuration() {
        return this.endTime - this.startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String toString(){
        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("startTime", this.startTime);
            jsonInfo.put("endTime", this.endTime);
            jsonInfo.put("activityLabel", this.activityLabel);
            jsonInfo.put("patientId", this.patientId);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonInfo.toString();
    }
}
