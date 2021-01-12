package CCM.cchira.medical.system.dto.builders;

import CCM.cchira.medical.system.dto.UserDTO;
import CCM.cchira.medical.system.entity.Address;
import CCM.cchira.medical.system.entity.Gender;
import CCM.cchira.medical.system.entity.RoleName;
import CCM.cchira.medical.system.entity.User;

public class UserBuilder {

    public UserBuilder() {

    }

    public static UserDTO generateDTOFromEntity(User user) {
        String gender = user.getGender().equals(Gender.MALE) ? "male" : "female";
        String city = user.getAddress().getCity();
        String street = user.getAddress().getStreet();
        int number = user.getAddress().getNumber();

        return new UserDTO(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                gender,
                city,
                street,
                String.valueOf(number),
                user.isActive(),
                user.getEmail()
                );
    }

    public static User generateEntityFromDTO(UserDTO userDTO) {
        Address address = new Address(userDTO.getStreet(), userDTO.getCity(), Integer.parseInt(userDTO.getNumber()));
        Gender gender = Gender.MALE;

        if (userDTO.getGender().equals("female")) {
            gender = Gender.FEMALE;
        }

        return new User(
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getBirthDate(),
                gender,
                address,
                userDTO.isActive(),
                userDTO.getEmail()
        );
    }
}
