package CCM.cchira.medical.system.service;

import CCM.cchira.medical.system.dto.CaregiverDTO;
import CCM.cchira.medical.system.dto.CaregiverViewDTO;
import CCM.cchira.medical.system.dto.builders.CaregiverBuilder;
import CCM.cchira.medical.system.dto.builders.CaregiverViewBuilder;
import CCM.cchira.medical.system.entity.*;
import CCM.cchira.medical.system.exception.AppException;
import CCM.cchira.medical.system.exception.ResourceNotFoundException;
import CCM.cchira.medical.system.repository.CaregiverRepository;
import CCM.cchira.medical.system.repository.PatientRepository;
import CCM.cchira.medical.system.repository.RoleRepository;
import CCM.cchira.medical.system.repository.UserRepository;
import CCM.cchira.medical.system.validators.UserFieldsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CaregiverService {

    private final CaregiverRepository caregiverRepository;
    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CaregiverService(CaregiverRepository caregiverRepository, RoleRepository roleRepository, PatientRepository patientRepository, PasswordEncoder passwordEncoder) {
        this.caregiverRepository = caregiverRepository;
        this.roleRepository = roleRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CaregiverDTO getCaregiverById(Integer caregiverId) {
        Optional<Caregiver> caregiver = caregiverRepository.findById(caregiverId);
        return caregiver.map(CaregiverBuilder::generateDTOFromEntity).orElse(null);
    }

    public Integer insert(CaregiverDTO caregiverDTO) {

        UserFieldsValidator.validateInsertOrUpdate(caregiverDTO);

        Caregiver caregiver = CaregiverBuilder.generateEntityFromDTO(caregiverDTO);

        Role caregiverRole = roleRepository.findByName(RoleName.ROLE_CAREGIVER)
                .orElseThrow(() -> new AppException("Caregiver Role not set."));

        caregiver.setRole(caregiverRole);
        caregiver.setPassword(passwordEncoder.encode(caregiver.getPassword()));

        return caregiverRepository.save(caregiver).getId();
    }

    public Integer update(CaregiverDTO caregiverDTO) {

        Caregiver caregiver = caregiverRepository.getOne(caregiverDTO.getId());

        if (caregiver == null) {
            throw new ResourceNotFoundException("Caregiver", "user id", caregiverDTO.getId().toString());
        }

        UserFieldsValidator.validateInsertOrUpdate(caregiverDTO);

        Caregiver caregiverEntity = CaregiverBuilder.generateEntityFromDTO(caregiverDTO);

        Role patientRole = roleRepository.findByName(RoleName.ROLE_CAREGIVER)
                .orElseThrow(() -> new AppException("Caregiver Role not set."));

        caregiver.setEmail(caregiverEntity.getEmail());
        caregiver.setAddress(caregiverEntity.getAddress());
        caregiver.setRole(patientRole);
        caregiver.setFirstName(caregiverEntity.getFirstName());
        caregiver.setLastName(caregiverEntity.getLastName());
        caregiver.setPassword(passwordEncoder.encode(caregiverEntity.getPassword()));
        caregiver.setUserName(caregiverEntity.getUserName());

        return caregiverRepository.save(caregiver).getId();
    }

    public List<CaregiverDTO> findAll() {
        List<Caregiver> caregivers = caregiverRepository.getAllOrdered();

        return caregivers.stream()
                .map(CaregiverBuilder::generateDTOFromEntity)
                .collect(Collectors.toList());
    }

    public void deleteById(Integer caregiverId) {
        // it is sure to find one, else add extra check
        Caregiver caregiver = caregiverRepository.getOne(caregiverId);

        List<Patient> patients = patientRepository.findAllByCaregiver(caregiver);

        patients.forEach(patient -> {patient.setCaregiver(null);});

        this.caregiverRepository.deleteById(caregiverId);
    }
}
