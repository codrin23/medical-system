package CCM.cchira.pillcase;

import CCM.cchira.pillcase.timesimulator.ClockTicker;
import CCM.cchira.pillcase.timesimulator.Simulator;

public class Application {

    public static void main(String[] args) {
        ClockTicker clockTicker = new ClockTicker();

        Simulator simulator = new Simulator(clockTicker);

        new Thread(simulator).start();
    }
}
