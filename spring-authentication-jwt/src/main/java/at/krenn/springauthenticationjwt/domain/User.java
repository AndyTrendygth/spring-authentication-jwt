package at.krenn.springauthenticationjwt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "u_user")
public class User extends AbstractPersistable<Long> {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Role role;
}
