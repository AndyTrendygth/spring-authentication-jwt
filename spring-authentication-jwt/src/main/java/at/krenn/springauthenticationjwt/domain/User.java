package at.krenn.springauthenticationjwt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "u_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractPersistable<Long>  {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Role role;

}
