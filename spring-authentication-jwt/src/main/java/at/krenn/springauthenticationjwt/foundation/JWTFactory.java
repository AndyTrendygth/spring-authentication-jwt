package at.krenn.springauthenticationjwt.foundation;

import at.krenn.springauthenticationjwt.domain.User;
import at.krenn.springauthenticationjwt.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtFactory for creating, validating and handling JWT tokens
 */
@Component
@RequiredArgsConstructor
@CommonsLog
public class JWTFactory {

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    /**
     * Generate a JWT token with claims
     * @param user A user object
     * @return a signed JWT token
     */
    public String generateToken(User user){
        log.debug("Generating JWT token");
        return Jwts.builder().setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 60000*60*24))
                .claim("role", user.getRole())
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Get a signature key based on the secret
     * @return a signature Key
     */
    private Key getSignatureKey() {
        byte[] key = Decoders.BASE64.decode("S6HIBYU1SDMBNP1H33HKY48BQVHGT3FIASKJDFHTZ728472931QERFCVBNM");
        return Keys.hmacShaKeyFor(key);
    }

    /**
     * Extract from JWT token
     * @param token JWT token
     * @return Email String
     */
    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * get a specific claim from given token
     * @param token JWT token
     * @param claimsResolvers claim to extract
     * @return the requested claim
     * @param <T>
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Return all claims from a token
     * @param token JWT token
     * @return Claims object
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Extract and unformat JWT token from HTTP request
     * @param request The HTTP request
     * @return JWT token as a string
     */
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    /**
     * Checks if the given token with provided user is valid
     * @param token the JWT token
     * @param user the UserDetails object of a user
     * @return true if token is valid
     */
    public boolean isTokenValid(String token, UserDetails user){
        final String email = extractEmail(token);
        return (email.equals(user.getUsername()) && isTokenNotExpired(token));
    }

    public boolean checkValidEmail(String token, UserDetails userDetails){
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername());
    }

    /**
     * Checks if the token is not expired
     * @param token the JWT token
     * @return true if the token is not expired
     */
    public boolean isTokenNotExpired(String token) {
        boolean result =  extractClaim(token, Claims::getExpiration).after(new Date());
        log.debug("Token is not expired:" + result);
        return result;
    }

    //TODO
    public String generateRefreshToken(User user, Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 604800000))
                .claim("role", user.getRole())
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
