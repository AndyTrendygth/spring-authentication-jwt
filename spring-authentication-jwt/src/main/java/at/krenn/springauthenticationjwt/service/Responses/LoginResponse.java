package at.krenn.springauthenticationjwt.service.Responses;

import at.krenn.springauthenticationjwt.domain.Role;

public record LoginResponse(String firstName, String lastName, String email, String token, String refreshToken, Role role) {
}
