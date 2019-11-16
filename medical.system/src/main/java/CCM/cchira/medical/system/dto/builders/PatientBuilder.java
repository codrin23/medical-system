package CCM.cchira.medical.system.dto.builders;

import CCM.cchira.medical.system.dto.PatientDTO;
import CCM.cchira.medical.system.entity.*;

public class PatientBuilder {

    public PatientBuilder() {

    }

    public static PatientDTO generateDTOFromEntity(Patient patient) {

        String gender = patient.getGender().equals(Gender.MALE) ? "male" : "female";
        String city = patient.getAddress().getCity();
        String street = patient.getAddress().getStreet();
        int number = patient.getAddress().getNumber();
        // add custom fields for patientDTO, i.e., doctor and caregiver

        Integer caregiverId = null,
        doctorId = null;

        if (patient.getCaregiver() != null) {
            caregiverId = patient.getCaregiver().getId();
        }

        if (patient.getDoctor() != null) {
            doctorId = patient.getDoctor().getId();
        }

        return new PatientDTO(
                patient.getId(),
                patient.getUserName(),
                patient.getPassword(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getBirthDate(),
                gender,
                city,
                street,
                String.valueOf(number),
                patient.isActive(),
                patient.getEmail(),
                doctorId,
                caregiverId
        );
    }

    public static Patient generateEntityFromDTO(PatientDTO patientDTO) {
        User user = UserBuilder.generateEntityFromDTO(patientDTO);

        // add custom field for patient
        Patient patient = new Patient(
                user.getUserName(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getGender(),
                user.getAddress(),
                user.isActive(),
                user.getEmail(),
                null,
                null
        );

        patient.setRole(new Role(RoleName.ROLE_PATIENT));

        return patient;
    }

}
