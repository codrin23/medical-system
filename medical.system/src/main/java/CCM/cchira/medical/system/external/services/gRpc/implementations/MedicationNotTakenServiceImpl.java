package CCM.cchira.medical.system.external.services.gRpc.implementations;

import CCM.cchira.grpc.MedicationNotTakenRequest;
import CCM.cchira.grpc.MedicationNotTakenResponse;
import CCM.cchira.grpc.MedicationNotTakenServiceGrpc;
import io.grpc.stub.StreamObserver;

public class MedicationNotTakenServiceImpl extends MedicationNotTakenServiceGrpc.MedicationNotTakenServiceImplBase {

    @Override
    public void medicationNotTaken(MedicationNotTakenRequest medicationNotTakenRequest,
                                StreamObserver<MedicationNotTakenResponse> responseObserver) {

        System.out.println("Medication Not Taken Request received from client:\n" + medicationNotTakenRequest);

        MedicationNotTakenResponse medicationNotTakenResponse = MedicationNotTakenResponse.newBuilder().setSuccess(true).build();

        responseObserver.onNext(medicationNotTakenResponse);
        responseObserver.onCompleted();
    }
}
