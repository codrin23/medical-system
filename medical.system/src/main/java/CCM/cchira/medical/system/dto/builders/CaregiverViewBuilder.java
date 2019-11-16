package CCM.cchira.medical.system.dto.builders;

import CCM.cchira.medical.system.dto.CaregiverViewDTO;
import CCM.cchira.medical.system.entity.Caregiver;

public class CaregiverViewBuilder {


    public static CaregiverViewDTO generateDTOFromEntity(Caregiver caregiver) {
        CaregiverViewDTO caregiverViewDTO = new CaregiverViewDTO();

        caregiverViewDTO.setId(caregiver.getId());
        caregiverViewDTO.setName(caregiver.getLastName() + " " + caregiver.getFirstName());
        caregiverViewDTO.setBirthDate(caregiver.getBirthDate());
        caregiverViewDTO.setEmail(caregiver.getEmail());
        caregiverViewDTO.setAddress(caregiver.getAddress().getCity() + " " + caregiver.getAddress().getStreet() + " " + caregiver.getAddress().getNumber());

        return caregiverViewDTO;
    }
}
