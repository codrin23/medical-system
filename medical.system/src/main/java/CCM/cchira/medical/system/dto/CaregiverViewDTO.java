package CCM.cchira.medical.system.dto;

import java.sql.Date;

public class CaregiverViewDTO {

    private Integer id;
    private String name;
    private String email;
    private java.sql.Date birthDate;
    private String address;

    public CaregiverViewDTO() {
    }

    public CaregiverViewDTO(Integer id) {
        this.id = id;
    }

    public CaregiverViewDTO(Integer id, String name, String email, Date birthDate, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
