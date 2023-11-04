package at.krenn.springauthenticationjwt.service.Requests;

import at.krenn.springauthenticationjwt.domain.Role;

public record CreateUserRequest(String firstName, String lastName, String email, String password, Role role) {
}
