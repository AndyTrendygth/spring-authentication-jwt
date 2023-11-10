package at.krenn.springauthenticationjwt.service;

import at.krenn.springauthenticationjwt.domain.User;
import at.krenn.springauthenticationjwt.foundation.JWTFactory;
import at.krenn.springauthenticationjwt.persistence.UserRepository;
import at.krenn.springauthenticationjwt.service.Requests.LoginRequest;
import at.krenn.springauthenticationjwt.service.Requests.RegisterRequest;
import at.krenn.springauthenticationjwt.service.Responses.LoginResponse;
import at.krenn.springauthenticationjwt.service.Responses.RegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@CommonsLog
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JWTFactory jwtFactory;


    public LoginResponse login(LoginRequest request){
        log.info("Starting login service");
        User user = userService.getUserByEmail(request.email()).orElseThrow(()-> new IllegalArgumentException("Invalid Email"));
        log.info("Login for user" + user.getEmail());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        log.info("Authenticated user " + authenticate.isAuthenticated());
        String token = jwtFactory.generateToken(user);
        log.info("jwt token"+ token);
        String refreshToken = jwtFactory.generateRefreshToken(user, new HashMap<>());
        return new LoginResponse(user.getFirstName(), user.getLastName(), user.getEmail(), token, refreshToken, user.getRole());
    }

    //TODO
    public RegisterResponse signup(RegisterRequest request){
        User user = userService.createUser(request);
        //Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        //String token = jwtFactory.generateToken(user);
        //String refreshToken = jwtFactory.generateRefreshToken();
        return new RegisterResponse(user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
