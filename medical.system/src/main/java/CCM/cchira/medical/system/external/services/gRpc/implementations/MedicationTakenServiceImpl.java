package CCM.cchira.medical.system.external.services.gRpc.implementations;

import CCM.cchira.grpc.MedicationTakenRequest;
import CCM.cchira.grpc.MedicationTakenResponse;
import CCM.cchira.grpc.MedicationTakenServiceGrpc;
import io.grpc.stub.StreamObserver;

public class MedicationTakenServiceImpl extends MedicationTakenServiceGrpc.MedicationTakenServiceImplBase {

    @Override
    public void medicationTaken(MedicationTakenRequest medicationTakenRequest,
                                StreamObserver<MedicationTakenResponse> responseObserver) {

        System.out.println("Medication Taken Request received from client:\n" + medicationTakenRequest);

        MedicationTakenResponse medicationTakenResponse = MedicationTakenResponse.newBuilder().setSuccess(true).build();

        responseObserver.onNext(medicationTakenResponse);
        responseObserver.onCompleted();
    }
}
