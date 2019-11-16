package CCM.cchira.medical.system.service;

import CCM.cchira.medical.system.entity.Patient;
import CCM.cchira.medical.system.repository.MonitoredDataRepository;
import CCM.cchira.medical.system.repository.PatientRepository;
import CCM.cchira.medical.system.external.services.sensors.MonitoredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MonitoredDataService {

    private final MonitoredDataRepository monitoredDataRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public MonitoredDataService(MonitoredDataRepository monitoredDataRepository, PatientRepository patientRepository) {
        this.monitoredDataRepository = monitoredDataRepository;
        this.patientRepository = patientRepository;
    }

    public Integer save(MonitoredData monitoredData) {

        Patient patient = patientRepository.getOne((int)monitoredData.getPatientId());

        CCM.cchira.medical.system.entity.MonitoredData monitoredData1 = new CCM.cchira.medical.system.entity.MonitoredData(
                new Timestamp(monitoredData.getStartTime()),
                new Timestamp(monitoredData.getEndTime()),
                monitoredData.getActivityLabel(),
                patient);

        return monitoredDataRepository.save(monitoredData1).getId();
    }
}
