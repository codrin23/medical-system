package CCM.cchira.medical.system.service;

import CCM.cchira.grpc.IntakeInterval;
import CCM.cchira.medical.system.dto.IntakeIntervalsDTO;
import CCM.cchira.medical.system.dto.medicationPlanPacient.DrugPlanViewIntervalDTO;
import CCM.cchira.medical.system.dto.medicationPlanPacient.MedicationPlanViewDTO;
import CCM.cchira.medical.system.entity.MedicationPlan;
import CCM.cchira.medical.system.entity.Patient;
import CCM.cchira.medical.system.repository.MedicationPlanRepository;
import CCM.cchira.medical.system.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MedicationPlanService {

    private final MedicationPlanRepository medicationPlanRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public MedicationPlanService(MedicationPlanRepository medicationPlanRepository, PatientRepository patientRepository) {
        this.medicationPlanRepository = medicationPlanRepository;
        this.patientRepository = patientRepository;
    }

    public Integer save(MedicationPlan medicationPlan) {

        return medicationPlanRepository.save(medicationPlan).getId();
    }

    public List<MedicationPlanViewDTO> getAllOrdered(Integer patientId) {
        Patient patient = patientRepository.getOne(patientId);
        List<MedicationPlan> medicationPlans = medicationPlanRepository.findMedicationPlansByPatientOrderByStartDesc(patient);
        List<MedicationPlanViewDTO> medicationPlanViewDTOList = new ArrayList<>();

        for (MedicationPlan medicationPlan : medicationPlans) {
            MedicationPlanViewDTO medicationPlanViewDTO = new MedicationPlanViewDTO();
            if (medicationPlan.getDoctor() != null) {
                medicationPlanViewDTO.setDoctorName(medicationPlan.getDoctor().getFirstName() + " " + medicationPlan.getDoctor().getFirstName());
            } else {
                medicationPlanViewDTO.setDoctorName(null);
            }
            medicationPlanViewDTO.setStart(medicationPlan.getStart());
            medicationPlanViewDTO.setEnd(medicationPlan.getEnd());
            List<DrugPlanViewIntervalDTO> drugPlanViewIntervalDTOS = new ArrayList<>();

            Map<String, List<IntakeIntervalsDTO>> finalMapping= new HashMap<>();
            medicationPlan.getIntakeIntervals().forEach(intakeIntervals -> {
                if (!finalMapping.containsKey(intakeIntervals.getDrug().getName())) {
                    finalMapping.put(intakeIntervals.getDrug().getName(), new ArrayList<>());
                }
                finalMapping.get(intakeIntervals.getDrug().getName()).add(new IntakeIntervalsDTO(intakeIntervals.getEndHour().toString(), intakeIntervals.getStartHour().toString()));
            });
            finalMapping.keySet().forEach(drugName -> drugPlanViewIntervalDTOS.add(new DrugPlanViewIntervalDTO(drugName, finalMapping.get(drugName))));


            medicationPlanViewDTO.setDrugPlanInterval(drugPlanViewIntervalDTOS);
            medicationPlanViewDTOList.add(medicationPlanViewDTO);
        }

        return medicationPlanViewDTOList;
    }

    /**
     * Return a list of intervals drugName, startHour, endHour
     * @param patientId the id of the patient
     * @param date the date for which the medication plans
     */
    public List<IntakeInterval> getMedicationPlanByPatientAndDate(Integer patientId, String date) {
        Patient patient = patientRepository.getOne(patientId);
        List<MedicationPlan> medicationPlans = medicationPlanRepository.findMedicationPlansByPatientOrderByStartDesc(patient);

        // output
        List<IntakeInterval> intakeIntervals = new ArrayList<>();

        // search for the medication plans which contain the requested date and from those collect the neccessary intake intervals and the drug name
        for (MedicationPlan medicationPlan : medicationPlans) {
            String startDateOfMedicationPlan = medicationPlan.getStart().toString().concat(" 00:00:00"),
                    endDateOfMedicationPlan = medicationPlan.getEnd().toString().concat(" 00:00:00");

            if (startDateOfMedicationPlan.compareTo(date) <= 0 &&
                    endDateOfMedicationPlan.compareTo(date) >= 0) {
                medicationPlan.getIntakeIntervals().forEach(intakeInterval -> {
                    intakeIntervals.add(IntakeInterval.newBuilder().setDrugName(intakeInterval.getDrug().getName())
                                                                    .setDosage(intakeInterval.getDrug().getDosage())
                                                                    .setStartHour(intakeInterval.getStartHour().toString())
                                                                    .setEndHour(intakeInterval.getEndHour().toString())
                                                                    .build());
                });
            }
        }

        return intakeIntervals;
    }

}
