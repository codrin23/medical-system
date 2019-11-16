package CCM.cchira.pillcase.timesimulator;

import CCM.cchira.grpc.*;
import CCM.cchira.pillcase.model.IntakeIntervalComparable;
import CCM.cchira.pillcase.userinterface.PillCaseUserInterface;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Simulator implements Runnable {
    private ClockTicker clockTicker;

    public Simulator(ClockTicker clockTicker) {
        this.clockTicker = clockTicker;
    }

    @Override
    public void run() {
        boolean firstRun = true;
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd")
                .withZone(ZoneId.systemDefault());

        DateTimeFormatter FINAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        DateTimeFormatter INTERVAL_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

        List<IntakeInterval> intakeIntervals = new ArrayList<>();
        PillCaseUserInterface pillCaseUserInterface = new PillCaseUserInterface(intakeIntervals, this.clockTicker);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();

        DownloadMedicationPlanServiceGrpc.DownloadMedicationPlanServiceBlockingStub downloadMedicationPlanServiceBlockingStub
                = DownloadMedicationPlanServiceGrpc.newBlockingStub(channel);

        MedicationNotTakenServiceGrpc.MedicationNotTakenServiceBlockingStub medicationNotTakenServiceBlockingStub
                = MedicationNotTakenServiceGrpc.newBlockingStub(channel);

        while (true) {
            Instant oldInstant = this.clockTicker.getCurrentTime();
            Instant newInstant = this.clockTicker.instant();
            System.out.println("Current time of the system is: " + newInstant.atZone(ZoneId.of("Europe/Bucharest")));

            String previousDay = DATE_FORMATTER.format(oldInstant),
                    currentDay = DATE_FORMATTER.format(newInstant);

            // in fiecare secunda verificam daca am trecut de vreun intakeInterval endHour, daca da inseamna ca
            // il putem marca ca untaken si anuntam serverul
            if (intakeIntervals.size() != 0) {
                List<IntakeInterval> intakeIntervalsToBeRemoved = new ArrayList<>();
                for (IntakeInterval intakeInterval : intakeIntervals) {
                    if (intakeInterval.getEndHour().compareTo(INTERVAL_FORMATTER.format(newInstant)) < 0) {

                        intakeIntervalsToBeRemoved.add(intakeInterval);
                        System.out.println("Medicament neluat " + intakeInterval.toString());
                        MedicationNotTakenResponse medicationNotTakenResponse = medicationNotTakenServiceBlockingStub.medicationNotTaken(
                                MedicationNotTakenRequest
                                        .newBuilder()
                                        .setPatientId(1)
                                        .setCurrentDate(FINAL_DATE_FORMATTER.format(newInstant))
                                        .setDrugName(intakeInterval.getDrugName())
                                        .setStartHour(intakeInterval.getStartHour())
                                        .setEndHour(intakeInterval.getEndHour())
                                        .build()
                                );
                        System.out.println("Status request medication not taken " + medicationNotTakenResponse.getSuccess());
                    }
                }
                intakeIntervals.removeAll(intakeIntervalsToBeRemoved);
                pillCaseUserInterface.repaintModel(intakeIntervals);
            }

            if (!previousDay.equals(currentDay) || firstRun) {
                firstRun = false;

                DownloadMedicationPlanResponse downloadMedicationPlanResponse = downloadMedicationPlanServiceBlockingStub
                        .downloadMedicationPlan(DownloadMedicationPlanRequest.newBuilder()
                        .setPatientId(1)
                        .setCurrentDate(FINAL_DATE_FORMATTER.format(newInstant))
                        .build());

                System.out.println("Response received from server:\n" + downloadMedicationPlanResponse);

                // sort intakeIntervals ascending in order of endHour
                List<IntakeIntervalComparable> intakeIntervalsComparable =
                        downloadMedicationPlanResponse.getIntakeIntervalsList().stream().map(IntakeIntervalComparable::new).sorted().collect(Collectors.toList());
                intakeIntervals = intakeIntervalsComparable.stream().map(IntakeIntervalComparable::getIntakeInterval).collect(Collectors.toList());

                pillCaseUserInterface.repaintModel(intakeIntervals);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
