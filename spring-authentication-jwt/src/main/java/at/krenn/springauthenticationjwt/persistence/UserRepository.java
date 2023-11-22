package at.krenn.springauthenticationjwt.persistence;

import at.krenn.springauthenticationjwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find users by their ID
     * @param id the Users id
     * @return An optional user
     */
    Optional<User> findById(Long id);

    /**
     * Find all Users by a firstname
     * @param firstName Firstname to search after
     * @return a List of users
     */
    List<User> findAllByFirstNameLike(String firstName);

    /**
     * Find a user by its email
     * @param email Email of the user
     * @return an optional User
     */
    Optional<User> findByEmail(String email);
}
