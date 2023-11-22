package at.krenn.springauthenticationjwt.service.Requests;

import at.krenn.springauthenticationjwt.domain.Role;

public record RegisterRequest(String firstName, String lastName, String email, String password, Role role) {
}
