package at.krenn.springauthenticationjwt.presentation.api;

import at.krenn.springauthenticationjwt.service.AuthenticationService;
import at.krenn.springauthenticationjwt.service.Requests.LoginRequest;
import at.krenn.springauthenticationjwt.service.Requests.RefreshJwtTokenRequest;
import at.krenn.springauthenticationjwt.service.Requests.RegisterRequest;
import at.krenn.springauthenticationjwt.service.Responses.LoginResponse;
import at.krenn.springauthenticationjwt.service.Responses.RefreshJwtTokenResponse;
import at.krenn.springauthenticationjwt.service.Responses.RegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CommonsLog
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public HttpEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        log.debug("Incoming Login request");
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/signup")
    public HttpEntity<RegisterResponse> signup(@RequestBody RegisterRequest registerRequest){
        log.debug("Incoming Signup request");
        return ResponseEntity.ok(authenticationService.signup(registerRequest));
    }

    @PostMapping("/refresh")
    public HttpEntity<RefreshJwtTokenResponse> refreshToken(@RequestBody RefreshJwtTokenRequest refreshJwtTokenRequest){
        log.debug("Incoming token refresh request");
        return ResponseEntity.ok(authenticationService.refreshToken(refreshJwtTokenRequest));
    }

}
