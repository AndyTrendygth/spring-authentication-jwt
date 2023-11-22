package at.krenn.springauthenticationjwt.service;

import at.krenn.springauthenticationjwt.domain.Role;
import at.krenn.springauthenticationjwt.domain.User;
import at.krenn.springauthenticationjwt.foundation.JWTFactory;
import at.krenn.springauthenticationjwt.persistence.UserRepository;
import at.krenn.springauthenticationjwt.service.Requests.LoginRequest;
import at.krenn.springauthenticationjwt.service.Requests.RefreshJwtTokenRequest;
import at.krenn.springauthenticationjwt.service.Requests.RegisterRequest;
import at.krenn.springauthenticationjwt.service.Responses.LoginResponse;
import at.krenn.springauthenticationjwt.service.Responses.RefreshJwtTokenResponse;
import at.krenn.springauthenticationjwt.service.Responses.RegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@CommonsLog
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final CustomUserDetailsService customUserDetailsService;

    private final JWTFactory jwtFactory;


    /**
     * Log in a user
     * @param request The login request
     * @return a LoginResponse
     */
    public LoginResponse login(LoginRequest request){
        log.debug("Logging in user " + request.email());

        User user = userService.getUserByEmail(request.email()).orElseThrow(()-> new IllegalArgumentException("Invalid Email"));
        UserDetails cUser = customUserDetailsService.loadUserByUsername(request.email());

        Authentication authentication = authenticateCredentials(request.email(), request.password(), (Collection<GrantedAuthority>) cUser.getAuthorities());
        log.debug("Authenticated user " + authentication.isAuthenticated());

        List<String> tokens = getJwtTokens(user);

        return new LoginResponse(user.getFirstName(), user.getLastName(), user.getEmail(), tokens.get(0), tokens.get(1), user.getRole());
    }

    //TODO
    public RegisterResponse signup(RegisterRequest request){
        User user = userService.createUser(request);
        //Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        //String token = jwtFactory.generateToken(user);
        //String refreshToken = jwtFactory.generateRefreshToken();
        return new RegisterResponse(user.getFirstName(), user.getLastName(), user.getEmail());
    }

    /**
     * Authenticate user credentials against db
     * @param email users email
     * @param password users password
     * @param roles roles of user
     * @return Authentication object
     */
    private Authentication authenticateCredentials(String email, String password, Collection<GrantedAuthority> roles){
        try {
            log.debug("Authenticating user " + email);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password, roles)
            );
        } catch (BadCredentialsException e) {
            log.warn("User Authentication failed. Reason: Invalid username or password");
            throw new BadCredentialsException("Invalid username or password", e);
        }
    }

    /**
     * Get jwt token and refresh token for a user
     * @param user the user for who you want the tokens
     * @return List of the tokens
     */
    private List<String> getJwtTokens(User user){
        List<String> tokenPair = new ArrayList<>();

        String token = jwtFactory.generateToken(user);
        String refreshToken = jwtFactory.generateRefreshToken(user, new HashMap<>());

        tokenPair.add(token);
        tokenPair.add(refreshToken);

        log.debug("Generated jwt token "+ token);
        log.debug("Generated jwt refresh token" + refreshToken);

        return tokenPair;
    }

    /**
     * Generate a refreshed token pair for an expired jwt token
     * @param refreshJwtTokenRequest
     * @return
     */
    public RefreshJwtTokenResponse refreshToken(RefreshJwtTokenRequest refreshJwtTokenRequest) {
        String email = jwtFactory.extractEmail(refreshJwtTokenRequest.token());
        UserDetails customUser = customUserDetailsService.loadUserByUsername(email);
        User user = userService.getUserByEmail(email).get();

        if(jwtFactory.checkValidEmail(refreshJwtTokenRequest.token(), customUser) && !jwtFactory.isTokenNotExpired(refreshJwtTokenRequest.token()) && jwtFactory.isTokenNotExpired(refreshJwtTokenRequest.refreshToken())){
            List<String> tokens = getJwtTokens(user);
            return new RefreshJwtTokenResponse(tokens.get(0), tokens.get(1));
        }
        throw new BadCredentialsException("Token is still valid, no need to refresh");
    }
}
