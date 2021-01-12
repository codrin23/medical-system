package CCM.cchira.medical.system.dto.builders;

import CCM.cchira.medical.system.dto.PatientViewDTO;
import CCM.cchira.medical.system.entity.Patient;

public class PatientViewBuilder {

    public PatientViewBuilder() {

    }

    public static PatientViewDTO generateDTOFromEntity(Patient patient) {

        PatientViewDTO patientViewDTO = new PatientViewDTO();

        patientViewDTO.setId(patient.getId());
        patientViewDTO.setName(patient.getLastName() + " " + patient.getFirstName());
        patientViewDTO.setBirthDate(patient.getBirthDate());
        patientViewDTO.setEmail(patient.getEmail());
        patientViewDTO.setAddress(patient.getAddress().getCity() + " " + patient.getAddress().getStreet() + " " + patient.getAddress().getNumber());

        return patientViewDTO;
    }

}
