package CCM.cchira.medical.system.dto.builders;

import CCM.cchira.medical.system.dto.IntakeIntervalsDTO;
import CCM.cchira.medical.system.dto.medicationPlanPacient.DrugPlanViewIntervalDTO;
import CCM.cchira.medical.system.dto.medicationPlanPacient.MedicationPlanViewDTO;
import CCM.cchira.medical.system.entity.MedicationPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicationPlanBuilder {
    public MedicationPlanBuilder() {
    }

    public static MedicationPlanViewDTO generateDTOFromMedicalPlan(MedicationPlan medicationPlan) {
        MedicationPlanViewDTO medicationPlanViewDTO = new MedicationPlanViewDTO();

        medicationPlanViewDTO.setDoctorName(medicationPlan.getDoctor().getFirstName() + " " + medicationPlan.getDoctor().getFirstName());
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

        return medicationPlanViewDTO;
    }
}
