package CCM.cchira.pillcase.timesimulator;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class ClockTicker extends Clock {

    private final int MINUTES_SIMULATION = 10;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.systemDefault());
    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")
            .withZone(ZoneId.systemDefault());

    private static void log(Object msg){
        System.out.println(msg);
    }

    @Override public ZoneId getZone() {
        return DEFAULT_TZONE;
    }

    @Override public Clock withZone(ZoneId zone) {
        return Clock.fixed(WHEN_STARTED, zone);
    }

    @Override public Instant instant() {
        return nextInstant();
    }

    public Instant getCurrentTime() {
        return WHEN_STARTED.plusSeconds(count * 60 * MINUTES_SIMULATION);
    }

    public String getCurrentDate() {
        return DATE_FORMATTER.format(WHEN_STARTED.plusSeconds(count * 60 * MINUTES_SIMULATION));
    }

    public String getCurrentInterval() {
        return TIME_FORMATTER.format(WHEN_STARTED.plusSeconds(count * 60 * MINUTES_SIMULATION));
    }

    //PRIVATE
    private final Instant WHEN_STARTED = Instant.now();
    private final ZoneId DEFAULT_TZONE = ZoneId.systemDefault();
    private long count = 0;

    /**
     * 5 minutes passes for each second in real life
     * @return instant for current time of the simulator
     */
    private Instant nextInstant() {
        ++count;
        return WHEN_STARTED.plusSeconds(count * 60 * MINUTES_SIMULATION);
    }
}
