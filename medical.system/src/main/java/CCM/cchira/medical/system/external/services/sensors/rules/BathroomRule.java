package CCM.cchira.medical.system.external.services.sensors.rules;

import CCM.cchira.medical.system.external.services.sensors.MonitoredData;

public class BathroomRule implements Rule{
    private static final int ALLOWED_BATHROOM_HOURS = 1;

    @Override
    public boolean isRuleBroken(MonitoredData monitoredData) {

        return (monitoredData.getDuration() / MILLIS_TO_HOURS > ALLOWED_BATHROOM_HOURS);
    }
}
