package at.krenn.springauthenticationjwt.presentation.api;

import at.krenn.springauthenticationjwt.service.AuthenticationService;
import at.krenn.springauthenticationjwt.service.Requests.LoginRequest;
import at.krenn.springauthenticationjwt.service.Requests.RegisterRequest;
import at.krenn.springauthenticationjwt.service.Responses.LoginResponse;
import at.krenn.springauthenticationjwt.service.Responses.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public HttpEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/signup")
    public HttpEntity<RegisterResponse> signup(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.signup(registerRequest));
    }

}
