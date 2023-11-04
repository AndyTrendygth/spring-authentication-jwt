package at.krenn.springauthenticationjwt.service.Responses;

import at.krenn.springauthenticationjwt.domain.Role;
import at.krenn.springauthenticationjwt.domain.User;

public record UserResponse(Long id, String firstName, String lastName, String email, Role role) {

    public UserResponse(User user){
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
    }
}
