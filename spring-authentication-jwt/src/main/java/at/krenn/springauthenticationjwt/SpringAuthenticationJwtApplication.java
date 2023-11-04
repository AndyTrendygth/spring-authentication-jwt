package at.krenn.springauthenticationjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

@SpringBootApplication(exclude = FlywayAutoConfiguration.class)
public class SpringAuthenticationJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthenticationJwtApplication.class, args);
    }

}
