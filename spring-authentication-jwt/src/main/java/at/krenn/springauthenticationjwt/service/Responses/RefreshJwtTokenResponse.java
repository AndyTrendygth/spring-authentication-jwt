package at.krenn.springauthenticationjwt.service.Responses;

public record RefreshJwtTokenResponse(String token, String refreshToken) {
}
