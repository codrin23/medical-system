package CCM.cchira.medical.system.service;

import CCM.cchira.medical.system.dto.PatientDTO;
import CCM.cchira.medical.system.dto.PatientViewDTO;
import CCM.cchira.medical.system.dto.builders.PatientBuilder;
import CCM.cchira.medical.system.dto.builders.PatientViewBuilder;
import CCM.cchira.medical.system.entity.*;
import CCM.cchira.medical.system.exception.AppException;
import CCM.cchira.medical.system.exception.ResourceNotFoundException;
import CCM.cchira.medical.system.repository.CaregiverRepository;
import CCM.cchira.medical.system.repository.DoctorRepository;
import CCM.cchira.medical.system.repository.PatientRepository;
import CCM.cchira.medical.system.repository.RoleRepository;
import CCM.cchira.medical.system.validators.UserFieldsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    private final RoleRepository roleRepository;

    private final DoctorRepository doctorRepository;

    private final CaregiverRepository caregiverRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientService(PatientRepository patientRepository, RoleRepository roleRepository, DoctorRepository doctorRepository, CaregiverRepository caregiverRepository, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.roleRepository = roleRepository;
        this.doctorRepository = doctorRepository;
        this.caregiverRepository = caregiverRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Patient getById(Integer patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }

    public PatientDTO getPatientById(Integer patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);

        return patient.map(PatientBuilder::generateDTOFromEntity).orElse(null);
    }

    public Integer insert(PatientDTO patientDTO) {

        UserFieldsValidator.validateInsertOrUpdate(patientDTO);

        Patient patient = PatientBuilder.generateEntityFromDTO(patientDTO);

        Doctor patientDoctor = doctorRepository.findById(patientDTO.getDoctorId())
                .orElseThrow(() -> new AppException("Patient's Doctor not set."));

        Caregiver patientCaregiver = caregiverRepository.findById(patientDTO.getCaregiverId())
                .orElseThrow(() -> new AppException("Patient's Caregiver not set."));



        Role patientRole = roleRepository.findByName(RoleName.ROLE_PATIENT)
                .orElseThrow(() -> new AppException("Patient Role not set."));

        patient.setRole(patientRole);
        patient.setDoctor(patientDoctor);
        patient.setCaregiver(patientCaregiver);
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));

        return patientRepository.save(patient).getId();
    }

    /**
     * Metoda este doar pt inserarea unui patient la un doctor, completeaza automat caregiver pe null
     * @param doctorId
     * @param patientDTO
     * @return
     */
    public Integer insertDoctorPatient(Integer doctorId, PatientDTO patientDTO) {

        UserFieldsValidator.validateInsertOrUpdate(patientDTO);

        Patient patient = PatientBuilder.generateEntityFromDTO(patientDTO);

        Doctor patientDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new AppException("Patient's Doctor not set."));


        Role patientRole = roleRepository.findByName(RoleName.ROLE_PATIENT)
                .orElseThrow(() -> new AppException("Patient Role not set."));

        patient.setRole(patientRole);
        patient.setDoctor(patientDoctor);
        patient.setCaregiver(null);
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));

        return patientRepository.save(patient).getId();
    }

    public Integer update(PatientDTO patientDTO) {

        Patient patient = patientRepository.getOne(patientDTO.getId());

        if (patient == null) {
            throw new ResourceNotFoundException("Patient", "user id", patientDTO.getId().toString());
        }

        UserFieldsValidator.validateInsertOrUpdate(patientDTO);

        Patient patientEntity = PatientBuilder.generateEntityFromDTO(patientDTO);

        Role patientRole = roleRepository.findByName(RoleName.ROLE_PATIENT)
                .orElseThrow(() -> new AppException("Patient Role not set."));

        Doctor doctor = null;
        Caregiver caregiver = null;
        if (patientDTO.getDoctorId() != null) {
            doctor = doctorRepository.findById(patientDTO.getDoctorId()).orElse(null);
        }
        if (patientDTO.getCaregiverId() != null) {
            caregiver = caregiverRepository.findById(patientDTO.getCaregiverId()).orElse(null);
        }

        patient.setEmail(patientEntity.getEmail());
        patient.setAddress(patientEntity.getAddress());
        patient.setRole(patientRole);
        patient.setFirstName(patientEntity.getFirstName());
        patient.setLastName(patientEntity.getLastName());
        patient.setPassword(passwordEncoder.encode(patientEntity.getPassword()));
        patient.setUserName(patientEntity.getUserName());
        patient.setDoctor(doctor);
        patient.setCaregiver(caregiver);

        return patientRepository.save(patient).getId();
    }

    public List<PatientViewDTO> findAll(Integer doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new AppException("Doctor not found."));

        List<Patient> patients = patientRepository.findAllByDoctor(doctor);

        return patients.stream()
                .map(PatientViewBuilder::generateDTOFromEntity)
                .collect(Collectors.toList());
    }

    public List<PatientViewDTO> findAllByCaregiverId(Integer caregiverId) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId).orElseThrow(() -> new AppException("Caregiver not found."));

        List<Patient> patients = patientRepository.findAllByCaregiver(caregiver);

        return patients.stream()
                .map(PatientViewBuilder::generateDTOFromEntity)
                .collect(Collectors.toList());
    }

    public void deleteById(Integer patientId) {
        this.patientRepository.deleteById(patientId);
    }

    public void save(Patient patient) {
        this.patientRepository.save(patient);
    }
}
