package at.krenn.springauthenticationjwt.presentation.api;

import at.krenn.springauthenticationjwt.service.Requests.LoginRequest;
import at.krenn.springauthenticationjwt.service.Responses.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @GetMapping("/login")
    public HttpEntity<LoginResponse> login(@RequestBody LoginRequest){
        return ResponseEntity.ok(null);
    }
}
