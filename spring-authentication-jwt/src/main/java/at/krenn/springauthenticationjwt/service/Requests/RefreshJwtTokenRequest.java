package at.krenn.springauthenticationjwt.service.Requests;

public record RefreshJwtTokenRequest(String token, String refreshToken) {
}
