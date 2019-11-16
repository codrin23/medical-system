package CCM.cchira.medical.system.controller;

import CCM.cchira.medical.system.dto.PatientViewDTO;
import CCM.cchira.medical.system.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/caregiver")
public class CaregiverController {

    private final PatientService patientService;

    @Autowired
    public CaregiverController(PatientService patientService) {
        this.patientService = patientService;
    }

    // TODO: plus their medication list, la cererea caregiverului (meniu in care poti cere details)
    @GetMapping("/patients/{caregiverId}")
    public List<PatientViewDTO> findAllPatients(@PathVariable(name="caregiverId") String caregiverId) {
        return patientService.findAllByCaregiverId(Integer.valueOf(caregiverId));
    }
}
