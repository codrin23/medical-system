package CCM.cchira.pillcase.userinterface;

import CCM.cchira.grpc.IntakeInterval;
import CCM.cchira.pillcase.userinterface.table.MedicationTable;
import CCM.cchira.pillcase.timesimulator.ClockTicker;
import CCM.cchira.pillcase.userinterface.table.MyTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PillCaseUserInterface extends JFrame {

    private List<IntakeInterval> intakeIntervals;
    private ClockTicker clockTicker;
    private MedicationTable medicationTable;

    public PillCaseUserInterface(List<IntakeInterval> intakeIntervals, ClockTicker clockTicker) throws HeadlessException {
        this.setTitle("Pillcase");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.intakeIntervals = intakeIntervals;
        this.clockTicker = clockTicker;
        this.createGui();
    }

    private void createGui() {
        this.medicationTable = new MedicationTable(this.intakeIntervals, this.clockTicker);
        this.medicationTable.setOpaque(true);
        this.setContentPane(this.medicationTable);

        this.pack();
        this.setVisible(true);
    }

    public void repaintModel(List<IntakeInterval> intakeIntervals) {
        MyTableModel myTableModel = new MyTableModel(intakeIntervals, this.clockTicker);
        this.medicationTable.repaintModel(myTableModel);
    }
}
