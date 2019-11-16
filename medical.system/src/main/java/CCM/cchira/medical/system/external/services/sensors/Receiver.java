package CCM.cchira.medical.system.external.services.sensors;

import CCM.cchira.medical.system.entity.Patient;
import CCM.cchira.medical.system.external.services.sensors.rules.BathroomRule;
import CCM.cchira.medical.system.external.services.sensors.rules.Context;
import CCM.cchira.medical.system.external.services.sensors.rules.LeavingRule;
import CCM.cchira.medical.system.external.services.sensors.rules.SleepingRule;
import CCM.cchira.medical.system.service.MonitoredDataService;
import CCM.cchira.medical.system.service.PatientService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static CCM.cchira.medical.system.external.services.sensors.rules.Rule.MILLIS_TO_HOURS;

@Component
public class Receiver {

    private final MonitoredDataService monitoredDataService;
    private final PatientService patientService;
    private final SimpMessagingTemplate webSocket;

    @Autowired
    public Receiver(MonitoredDataService monitoredDataService, PatientService patientService, SimpMessagingTemplate webSocket) {
        this.monitoredDataService = monitoredDataService;
        this.patientService = patientService;
        this.webSocket = webSocket;
    }

    @RabbitListener(queues="${jsa.rabbitmq.queue}", containerFactory="jsaFactory")
    public void recievedMessage(MonitoredData monitoredData) {
        System.out.println("Recieved Message: " + monitoredData);

        Patient patient = patientService.getById((int)monitoredData.getPatientId());
        // send only to a specific caregiver
        String message = "Patient " + patient.getFirstName() + " " + patient.getLastName() + " has ";
        String channel = "/topic/caregiver/" + patient.getCaregiver().getUserName();
        boolean isAnyRuleBroken = false;

        // notify the caregiver in case of a problem
        Context context = new Context();
        switch (monitoredData.getActivityLabel()) {
            case "Sleeping": {
                context.setRule(new SleepingRule());
                if (context.isRuleBroken(monitoredData)) {
                    isAnyRuleBroken = true;
                    message += "slept ";
                    System.out.println("Sleeping rule is broken!");
                }
                break;
            }
            case "Leaving": {
                context.setRule(new LeavingRule());
                if (context.isRuleBroken(monitoredData)) {
                    isAnyRuleBroken = true;
                    message += "been out for ";
                    System.out.println("Leaving rule is broken!");
                }
                break;
            }
            case "Toileting": {
                context.setRule(new BathroomRule());
                if (context.isRuleBroken(monitoredData)) {
                    isAnyRuleBroken = true;
                    message += "stayed in the bathroom for ";
                    System.out.println("Toileting rule is broken!");
                }
                break;
            }
            default:
                break;
        }

        if (isAnyRuleBroken) {
            // saved in db to a specific patient
            monitoredDataService.save(monitoredData);

            // send message to the websocket on specific channel for each caregiver
            message += monitoredData.getDuration() / MILLIS_TO_HOURS + " hours!";
            webSocket.convertAndSend(channel, message);
        }
    }
}