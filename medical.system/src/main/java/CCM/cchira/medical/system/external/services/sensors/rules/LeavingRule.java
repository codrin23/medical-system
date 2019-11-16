package CCM.cchira.medical.system.external.services.sensors.rules;

import CCM.cchira.medical.system.external.services.sensors.MonitoredData;

public class LeavingRule implements Rule {
    private static final int ALLOWED_LEAVING_HOURS = 12;

    @Override
    public boolean isRuleBroken(MonitoredData monitoredData) {

        return (monitoredData.getDuration() / MILLIS_TO_HOURS > ALLOWED_LEAVING_HOURS);
    }
}
