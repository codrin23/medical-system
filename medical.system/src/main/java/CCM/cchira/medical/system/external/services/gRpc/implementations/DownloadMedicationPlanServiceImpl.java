package CCM.cchira.medical.system.external.services.gRpc.implementations;

import CCM.cchira.grpc.DownloadMedicationPlanRequest;
import CCM.cchira.grpc.DownloadMedicationPlanResponse;
import CCM.cchira.grpc.DownloadMedicationPlanServiceGrpc;
import CCM.cchira.grpc.IntakeInterval;
import CCM.cchira.medical.system.service.MedicationPlanService;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class DownloadMedicationPlanServiceImpl extends DownloadMedicationPlanServiceGrpc.DownloadMedicationPlanServiceImplBase {

    private final MedicationPlanService medicationPlanService;

    public DownloadMedicationPlanServiceImpl(MedicationPlanService medicationPlanService) {
        this.medicationPlanService = medicationPlanService;
    }

    @Override
    public void downloadMedicationPlan(DownloadMedicationPlanRequest downloadMedicationPlanRequest,
                                       StreamObserver<DownloadMedicationPlanResponse> responseObserver) {

        System.out.println("Download Medication Plan Request received from client:\n" + downloadMedicationPlanRequest);

        List<IntakeInterval> intakeIntervals = this.medicationPlanService.getMedicationPlanByPatientAndDate(downloadMedicationPlanRequest.getPatientId(), downloadMedicationPlanRequest.getCurrentDate());

        // prepare the intakeinterval separately
        DownloadMedicationPlanResponse.Builder builder = DownloadMedicationPlanResponse.newBuilder();

        for (IntakeInterval intakeInterval : intakeIntervals) {
            builder.addIntakeIntervals(intakeInterval);
        }

        DownloadMedicationPlanResponse response = builder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
