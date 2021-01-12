package CCM.cchira.medical.system.dto.builders;

import CCM.cchira.medical.system.dto.CaregiverDTO;
import CCM.cchira.medical.system.entity.*;

public class CaregiverBuilder {
    public CaregiverBuilder() {

    }

    public static CaregiverDTO generateDTOFromEntity(Caregiver caregiver) {
        String gender = caregiver.getGender().equals(Gender.MALE) ? "male" : "female";
        String city = caregiver.getAddress().getCity();
        String street = caregiver.getAddress().getStreet();
        int number = caregiver.getAddress().getNumber();

        // add custom fields for patientDTO, i.e., doctor and caregiver

        return new CaregiverDTO(caregiver.getId(),
                caregiver.getUserName(),
                caregiver.getPassword(),
                caregiver.getFirstName(),
                caregiver.getLastName(),
                caregiver.getBirthDate(),
                gender,
                city,
                street,
                String.valueOf(number),
                caregiver.isActive(),
                caregiver.getEmail());
    }

    public static Caregiver generateEntityFromDTO(CaregiverDTO patientDTO) {
        User user = UserBuilder.generateEntityFromDTO(patientDTO);

        // add custom field for patient
        Caregiver caregiver = new Caregiver(
                user.getUserName(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getGender(),
                user.getAddress(),
                user.isActive(),
                user.getEmail()
        );

        caregiver.setRole(new Role(RoleName.ROLE_CAREGIVER));

        return caregiver;
    }
}
