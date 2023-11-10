package at.krenn.springauthenticationjwt.service;

import at.krenn.springauthenticationjwt.domain.User;
import at.krenn.springauthenticationjwt.persistence.UserRepository;
import at.krenn.springauthenticationjwt.service.Requests.CreateUserRequest;
import at.krenn.springauthenticationjwt.service.Requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /*Gets a single user by its id*/
    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {return userRepository.findByEmail(email);}
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser(CreateUserRequest createUserRequest){
        return userRepository.findByEmail(createUserRequest.email()).orElseGet(()->
                userRepository.save(User.builder()
                                .firstName(createUserRequest.firstName())
                                .lastName(createUserRequest.lastName())
                                .email(createUserRequest.email())
                                .password(createUserRequest.password())
                                .role(createUserRequest.role())
                        .build())
                );
    }

    public User createUser(RegisterRequest registerRequest){
        return userRepository.findByEmail(registerRequest.email()).orElseGet(()->
                userRepository.save(User.builder()
                                .firstName(registerRequest.firstName())
                                .lastName(registerRequest.lastName())
                                .email(registerRequest.email())
                                .password(registerRequest.password())
                                .role(registerRequest.role())
                        .build())
        );
    }
}
