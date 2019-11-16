package CCM.cchira.medical.system.controller;

import CCM.cchira.medical.system.entity.User;
import CCM.cchira.medical.system.exception.ResourceNotFoundException;
import CCM.cchira.medical.system.payload.UserIdentityAvailability;
import CCM.cchira.medical.system.payload.UserProfile;
import CCM.cchira.medical.system.payload.UserSummary;
import CCM.cchira.medical.system.repository.UserRepository;
import CCM.cchira.medical.system.security.CurrentUser;
import CCM.cchira.medical.system.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        String role = "false";
        for (GrantedAuthority authority : currentUser.getAuthorities()) {
            role = authority.getAuthority();
        }
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), role);
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUserName(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));


        return new UserProfile(user.getId(), user.getUserName(), user.getFirstName() + " " + user.getLastName(), user.getEmail(), user.getBirthDate().toString(), user.getAddress().toString());
    }
}
