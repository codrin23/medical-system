package CCM.cchira.pillcase.userinterface.table;

import CCM.cchira.grpc.IntakeInterval;
import CCM.cchira.pillcase.timesimulator.ClockTicker;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MedicationTable extends JPanel {

    private MyTableModel myTableModel;
    private JTable table;

    public MedicationTable(List<IntakeInterval> intakeIntervals, ClockTicker clockTicker) {
        super(new GridLayout(1,0));

        this.myTableModel = new MyTableModel(intakeIntervals, clockTicker);
        this.table = new JTable(myTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
    }

    public void repaintModel(MyTableModel tableModel) {
        this.table.setModel(tableModel);
    }
}
