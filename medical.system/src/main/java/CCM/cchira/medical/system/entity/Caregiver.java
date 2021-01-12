package CCM.cchira.medical.system.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Caregiver extends User {

    public Caregiver() {
    }

    public Caregiver(String userName, String password, String firstName, String lastName, Date birthDate, Gender gender, Address address, boolean isActive, String email) {
        super(userName, password, firstName, lastName, birthDate, gender, address, isActive, email);
    }
}
