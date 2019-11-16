package CCM.cchira.pillcase.model;

import CCM.cchira.grpc.IntakeInterval;

public class IntakeIntervalComparable implements Comparable<IntakeIntervalComparable> {

    private IntakeInterval intakeInterval;

    public IntakeIntervalComparable(IntakeInterval intakeInterval) {
        this.intakeInterval = intakeInterval;
    }

    public IntakeInterval getIntakeInterval() {
        return intakeInterval;
    }

    public void setIntakeInterval(IntakeInterval intakeInterval) {
        this.intakeInterval = intakeInterval;
    }

    @Override
    public int compareTo(IntakeIntervalComparable o) {
        if (this.intakeInterval.getEndHour().compareTo(o.getIntakeInterval().getEndHour()) < 0) {

            return -1;
        } else if (this.intakeInterval.getEndHour().compareTo(o.getIntakeInterval().getEndHour()) > 0) {

            return 1;
        }
        return 0;
    }
}
