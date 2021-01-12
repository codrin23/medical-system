package CCM.cchira.medical.system.external.services.sensors.rules;

import CCM.cchira.medical.system.external.services.sensors.MonitoredData;

public interface Rule {
    public static final long MILLIS_TO_HOURS = 360000;

    public boolean isRuleBroken(MonitoredData monitoredData);
}
