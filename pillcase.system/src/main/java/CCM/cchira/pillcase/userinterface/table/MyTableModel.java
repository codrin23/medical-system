package CCM.cchira.pillcase.userinterface.table;

import CCM.cchira.grpc.IntakeInterval;
import CCM.cchira.grpc.MedicationTakenRequest;
import CCM.cchira.grpc.MedicationTakenResponse;
import CCM.cchira.grpc.MedicationTakenServiceGrpc;
import CCM.cchira.pillcase.timesimulator.ClockTicker;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.swing.table.AbstractTableModel;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyTableModel extends AbstractTableModel {
    private boolean DEBUG = true;

    private String[] columnNames = {"Drug Name",
            "Dosage",
            "From",
            "To",
            "Taken"};
    private List<IntakeInterval> data;

    private ClockTicker clockTicker;


    public MyTableModel(List<IntakeInterval> data, ClockTicker clockTicker) {
        this.data = data;
        this.clockTicker = clockTicker;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        IntakeInterval intakeIntervalRow = data.get(row);
        switch (col) {
            case 0: return intakeIntervalRow.getDrugName();
            case 1: return intakeIntervalRow.getDosage();
            case 2: return intakeIntervalRow.getStartHour();
            case 3: return intakeIntervalRow.getEndHour();
            case 4: return false;
            default: return null;
        }
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        if (col < 4) {
            return false;
        }

        System.out.println(clockTicker.getCurrentInterval());

        // editable only if it is false and the hour is in the prescripted interval
        return col == 4 &&
                getValueAt(row, col).equals(Boolean.FALSE) &&
                clockTicker.getCurrentInterval().compareTo(getValueAt(row, 2).toString()) >= 0 &&
                clockTicker.getCurrentInterval().compareTo(getValueAt(row, 3).toString()) <= 0;
    }

    /**
     * this is called only for taken column
     */
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                    + " to " + value
                    + " (an instance of "
                    + value.getClass() + ")");
        }

        IntakeInterval intakeIntervalRow = data.get(row);
        this.medicationTakenAnnounced(intakeIntervalRow.getDrugName(), intakeIntervalRow.getStartHour(), intakeIntervalRow.getEndHour());

        this.data.remove(intakeIntervalRow);
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void medicationTakenAnnounced(String drugName, String startHour, String endHour) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();

        MedicationTakenServiceGrpc.MedicationTakenServiceBlockingStub stub
                = MedicationTakenServiceGrpc.newBlockingStub(channel);

        DateTimeFormatter FINAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());


        MedicationTakenResponse helloResponse = stub.medicationTaken(MedicationTakenRequest.newBuilder()
                .setPatientId(1)
                .setCurrentDate(FINAL_DATE_FORMATTER.format(this.clockTicker.getCurrentTime()))
                .setDrugName(drugName)
                .setStartHour(startHour)
                .setEndHour(endHour)
                .build());

        System.out.println("Response received from server:\n" + helloResponse);
    }

    private void printDebugData() {
        int numRows = getRowCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            System.out.println(data.get(i).toString());
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
