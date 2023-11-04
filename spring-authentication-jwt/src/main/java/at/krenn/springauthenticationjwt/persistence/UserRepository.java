package at.krenn.springauthenticationjwt.persistence;

import at.krenn.springauthenticationjwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    List<User> findAllByFirstNameLike(String firstName);

    Optional<User> findByEmail(String email);
}
