package CCM.cchira.medical.system.dto;

import java.sql.Date;

public class DoctorDTO extends UserDTO {

    public DoctorDTO() {
    }

    public DoctorDTO(Integer id, String userName, String password, String firstName, String lastName, Date birthDate, String gender, String city, String street, String number, boolean isActive, String email) {
        super(id, userName, password, firstName, lastName, birthDate, gender, city, street, number, isActive, email);
    }
}
