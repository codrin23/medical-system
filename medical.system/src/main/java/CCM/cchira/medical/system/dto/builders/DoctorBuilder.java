package CCM.cchira.medical.system.dto.builders;

import CCM.cchira.medical.system.dto.DoctorDTO;
import CCM.cchira.medical.system.entity.Address;
import CCM.cchira.medical.system.entity.Doctor;
import CCM.cchira.medical.system.entity.Gender;

public class DoctorBuilder {

    public DoctorBuilder() {

    }

    public static DoctorDTO generateDTOFromEntity(Doctor doctor) {
        String gender = doctor.getGender().equals(Gender.MALE) ? "male" : "female";
        String city = doctor.getAddress().getCity();
        String street = doctor.getAddress().getStreet();
        int number = doctor.getAddress().getNumber();

        return new DoctorDTO(
                doctor.getId(),
                doctor.getUserName(),
                doctor.getPassword(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getBirthDate(),
                gender,
                city,
                street,
                String.valueOf(number),
                doctor.isActive(),
                doctor.getEmail()
        );
    }

    public static Doctor generateEntityFromDTO(DoctorDTO doctorDTO) {
        Address address = new Address(doctorDTO.getStreet(), doctorDTO.getCity(), Integer.parseInt(doctorDTO.getNumber()));
        Gender gender = Gender.MALE;

        if (doctorDTO.getGender().equals("female")) {
            gender = Gender.FEMALE;
        }

        return new Doctor(
                doctorDTO.getUserName(),
                doctorDTO.getPassword(),
                doctorDTO.getFirstName(),
                doctorDTO.getLastName(),
                doctorDTO.getBirthDate(),
                gender,
                address,
                doctorDTO.isActive(),
                doctorDTO.getEmail(),
                null
        );
    }
}
