package CCM.cchira.medical.system.external.services.gRpc;

import CCM.cchira.medical.system.external.services.gRpc.implementations.DownloadMedicationPlanServiceImpl;
import CCM.cchira.medical.system.external.services.gRpc.implementations.MedicationNotTakenServiceImpl;
import CCM.cchira.medical.system.external.services.gRpc.implementations.MedicationTakenServiceImpl;
import CCM.cchira.medical.system.service.MedicationPlanService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PillcaseServer implements ApplicationListener<ApplicationReadyEvent> {

    private final MedicationPlanService medicationPlanService;

    @Autowired
    public PillcaseServer(MedicationPlanService medicationPlanService) {
        this.medicationPlanService = medicationPlanService;
    }

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        Server server = ServerBuilder.forPort(8081)
                .addService(new DownloadMedicationPlanServiceImpl(this.medicationPlanService))
                .addService(new MedicationTakenServiceImpl())
                .addService(new MedicationNotTakenServiceImpl())
                .build();

        System.out.println("Starting server...");
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started!");
        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
