package CCM.cchira.medical.system.payload;

public class UserProfile {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String birthDate;
    private String address;

    public UserProfile(Integer id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public UserProfile(Integer id, String username, String name, String email, String birthDate, String address) {
        this.id = id;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
