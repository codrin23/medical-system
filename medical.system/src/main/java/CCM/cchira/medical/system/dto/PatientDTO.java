package CCM.cchira.medical.system.dto;

import java.sql.Date;

public class PatientDTO extends UserDTO {

    private Integer doctorId;
    private Integer caregiverId;

    public PatientDTO() {
    }

    public PatientDTO(Integer doctorId, Integer caregiverId) {
        this.doctorId = doctorId;
        this.caregiverId = caregiverId;
    }

    public PatientDTO(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public PatientDTO(Integer id, String userName, String password, String firstName, String lastName, Date birthDate, String gender, String city, String street, String number, boolean isActive, String email, Integer doctorId) {
        super(id, userName, password, firstName, lastName, birthDate, gender, city, street, number, isActive, email);
        this.doctorId = doctorId;
    }

    public PatientDTO(Integer id, String userName, String password, String firstName, String lastName, Date birthDate, String gender, String city, String street, String number, boolean isActive, String email, Integer doctorId, Integer caregiverId) {
        super(id, userName, password, firstName, lastName, birthDate, gender, city, street, number, isActive, email);
        this.doctorId = doctorId;
        this.caregiverId = caregiverId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(Integer caregiverId) {
        this.caregiverId = caregiverId;
    }
}
