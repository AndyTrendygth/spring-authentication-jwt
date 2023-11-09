package at.krenn.springauthenticationjwt.service;

import at.krenn.springauthenticationjwt.domain.User;
import at.krenn.springauthenticationjwt.foundation.JWTFactory;
import at.krenn.springauthenticationjwt.persistence.UserRepository;
import at.krenn.springauthenticationjwt.service.Requests.LoginRequest;
import at.krenn.springauthenticationjwt.service.Responses.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JWTFactory jwtFactory;
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.email()).orElseThrow(()-> new IllegalArgumentException("Invalid Email"));
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        String token = jwtFactory.generateToken(user);
        String refreshToken = jwtFactory.refreshToken();
        return new LoginResponse(user.getFirstName(), user.getLastName(), user.getEmail(), token, refreshToken);
    }

    public User signup(LoginRequest request){
        return null;
    }
}
