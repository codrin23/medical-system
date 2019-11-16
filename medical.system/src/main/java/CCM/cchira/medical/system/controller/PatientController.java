package CCM.cchira.medical.system.controller;

import CCM.cchira.medical.system.dto.medicationPlanPacient.MedicationPlanViewDTO;
import CCM.cchira.medical.system.service.MedicationPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {

    private final MedicationPlanService medicationPlanService;

    @Autowired
    public PatientController(MedicationPlanService medicationPlanService) {
        this.medicationPlanService = medicationPlanService;
    }

    @GetMapping("/medicationPlans/{patientId}")
    public List<MedicationPlanViewDTO> getMedicationPlans(@PathVariable(name="patientId") String patientId) {
        return medicationPlanService.getAllOrdered(Integer.valueOf(patientId));
    }
}
