package CCM.cchira.medical.system.controller;

import CCM.cchira.medical.system.dto.*;
import CCM.cchira.medical.system.entity.*;
import CCM.cchira.medical.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {

    private MedicationPlanService medicationPlanService;
    private PatientService patientService;
    private CaregiverService caregiverService;
    private DrugService drugService;
    private DoctorService doctorService;

    @Autowired
    public DoctorController(MedicationPlanService medicationPlanService, PatientService patientService,
                            CaregiverService caregiverService, DrugService drugService, DoctorService doctorService) {
        this.patientService = patientService;
        this.caregiverService = caregiverService;
        this.medicationPlanService = medicationPlanService;
        this.drugService = drugService;
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public List<DoctorDTO> getAllOrdered() {
        return doctorService.getAllDoctorsOrdered();
    }

    @PostMapping("/patient")
    public Integer insertPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.insert(patientDTO);
    }

    @PostMapping("/patient/{doctorId}")
    public Integer insertPatient(@PathVariable(name="doctorId") String doctorId, @RequestBody PatientDTO patientDTO) {
        return patientService.insertDoctorPatient(Integer.valueOf(doctorId), patientDTO);
    }

    @GetMapping("/patients/{doctorId}")
    public List<PatientViewDTO> findAllPatients(@PathVariable(value = "doctorId") Integer doctorId) {
        return patientService.findAll(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public PatientDTO findPatient(@PathVariable(name="patientId") String patientId) {
        return patientService.getPatientById(Integer.valueOf(patientId));
    }

    @PutMapping("/patient")
    public Integer updatePatient(@RequestBody PatientDTO patientDTO) {
        return patientService.update(patientDTO);
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable(name="patientId") String patientId) {
        patientService.deleteById(Integer.valueOf(patientId));
        PatientDTO patient = patientService.getPatientById(Integer.valueOf(patientId));
        Map<String, String> body = new HashMap<>();
        if(patient == null) {
            body.put("status", "Patient deleted");
        } else {
            body.put("status", "Patient not found");
        }
        return ResponseEntity.ok(body);
    }

    @PostMapping("/caregiver")
    public Integer insertCaregiver(@RequestBody CaregiverDTO caregiverDTO) {
        return caregiverService.insert(caregiverDTO);
    }

    @GetMapping("/caregivers")
    public List<CaregiverDTO> findAllCaregivers() {
        return caregiverService.findAll();
    }

    @GetMapping("/caregiver/{caregiverId}")
    public CaregiverDTO findCaregiver(@PathVariable(name="caregiverId") String caregiverId) {
        return caregiverService.getCaregiverById(Integer.valueOf(caregiverId));
    }

    @PutMapping("/caregiver")
    public Integer updateCaregiver(@RequestBody CaregiverDTO caregiverDTO) {
        return caregiverService.update(caregiverDTO);
    }

    @DeleteMapping("/caregiver")
    public ResponseEntity<?> deleteCaregiver(@RequestBody CaregiverViewDTO caregiverViewDTO) {
        caregiverService.deleteById(caregiverViewDTO.getId());
        CaregiverDTO caregiver = caregiverService.getCaregiverById(caregiverViewDTO.getId());
        Map<String, String> body = new HashMap<>();
        if(caregiver == null) {
            body.put("status", "Caregiver deleted");
        } else {
            body.put("status", "Caregiver not found");
        }
        return ResponseEntity.ok(body);
    }

    @PostMapping("/drug")
    public Integer insertDrug(@RequestBody DrugViewDTO drugViewDTO) {
        return drugService.insert(drugViewDTO);
    }

    @GetMapping("/drug/{drugId}")
    public DrugDTO findDrugById(@PathVariable(name="drugId") String drugId) {
        return drugService.getDrugById(Integer.valueOf(drugId));
    }

    @GetMapping("/drugs")
    public List<DrugDTO> findAllDrugs() {
        return drugService.findAll();
    }

    @PutMapping("/drug")
    public Integer updateDrug(@RequestBody DrugDTO drugDTO) {
        return drugService.update(drugDTO);
    }

    @DeleteMapping("/drug/{drugId}")
    public ResponseEntity<?> deleteDrug(@PathVariable(name="drugId") String drugId) {
        drugService.delete(Integer.valueOf(drugId));
        DrugDTO drug = drugService.getDrugById(Integer.valueOf(drugId));
        Map<String, String> body = new HashMap<>();
        if(drug == null) {
            body.put("status", "Drug deleted");
        } else {
            body.put("status", "Drug not found");
        }
        return ResponseEntity.ok(body);
    }

    @PostMapping("/medicationPlan")
    public Integer insertMedicationPlan(@RequestBody MedicationPlanDTO medicationPlanDTO) {
        System.out.println(medicationPlanDTO.toString());
        // prepare and insert medicationPlan
        Doctor doctor = doctorService.getById(medicationPlanDTO.getDoctorId());
        Patient patient = patientService.getById(medicationPlanDTO.getPatientId());
        MedicationPlan medicationPlan = new MedicationPlan(medicationPlanDTO.getStart(), medicationPlanDTO.getEnd(), patient, doctor);

        List<DrugsPlanInterval> drugsPlanIntervals = medicationPlanDTO.getDrugsPlanIntervals();
        List<IntakeIntervals> intakeIntervalsForMP = new ArrayList<>();
        for(DrugsPlanInterval drugsPlanInterval : drugsPlanIntervals) {
            Integer drugId = drugsPlanInterval.getDrugId();
            Drug drug = drugService.getById(drugId);

            for(IntakeIntervalsDTO intakeIntervalsDTO: drugsPlanInterval.getIntakeIntervals()) {
                IntakeIntervals intakeIntervals = new IntakeIntervals(java.sql.Time.valueOf(intakeIntervalsDTO.getStartHour()), java.sql.Time.valueOf(intakeIntervalsDTO.getEndHour()), drug, medicationPlan);
                intakeIntervalsForMP.add(intakeIntervals);
            }
        }
        medicationPlan.setIntakeIntervals(intakeIntervalsForMP);

        return medicationPlanService.save(medicationPlan);

        //return medicationPlanId;
    }
}
