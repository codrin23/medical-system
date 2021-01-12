package CCM.cchira.medical.system.external.services.sensors.rules;

import CCM.cchira.medical.system.external.services.sensors.MonitoredData;

public class Context {
    private Rule rule;

    public Context() {

    }

    public Context(Rule rule) {
        this.rule = rule;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public boolean isRuleBroken(MonitoredData monitoredData) {
        return rule.isRuleBroken(monitoredData);
    }
}
