package at.krenn.springauthenticationjwt.presentation.api;

import at.krenn.springauthenticationjwt.domain.User;
import at.krenn.springauthenticationjwt.service.Responses.UserResponse;
import at.krenn.springauthenticationjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;

    @RequestMapping("/{id}")
    public HttpEntity<UserResponse> fetchSingleUser(@PathVariable Long id){
        return userService.getUser(id)
                .map(UserResponse::new)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @RequestMapping("/")
    public HttpEntity<List<UserResponse>> fetchAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers()
                .stream()
                .map(UserResponse::new)
                .toList());
    }
}
