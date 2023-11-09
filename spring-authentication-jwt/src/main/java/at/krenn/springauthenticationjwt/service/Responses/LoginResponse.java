package at.krenn.springauthenticationjwt.service.Responses;

public record LoginResponse(String firstName, String lastName, String email, String token, String refreshToken) {
}
