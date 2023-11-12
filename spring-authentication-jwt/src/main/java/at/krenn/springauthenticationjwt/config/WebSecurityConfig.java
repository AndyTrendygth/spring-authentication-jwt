package at.krenn.springauthenticationjwt.config;

import at.krenn.springauthenticationjwt.domain.Role;
import at.krenn.springauthenticationjwt.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Spring filterChain Method that handles security for the entire application
     * @param http HttpSecurity object
     * @param introspector Introspector for MvcMatcher
     * @return SecurityFilterChain instance
     * @throws Exception on Error
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcRequestMatcher = new MvcRequestMatcher.Builder(introspector);
       http.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(request ->
                       request.requestMatchers(mvcRequestMatcher.pattern("/api/auth/**")).permitAll()
                               .requestMatchers(PathRequest.toH2Console()).permitAll()
                               .requestMatchers(mvcRequestMatcher.pattern("/api/users/**")).hasRole(Role.ADMIN.name())
                               .anyRequest().authenticated())
               .headers(headers -> headers.frameOptions((frameOptions) -> frameOptions.disable()))
               .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
       return http.build();
    }

    /**
     * PasswordEncoder that encrypts the Database passwords
     * @return a BCryptPasswordEncoder Instance
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Instantiate the AuthenticationManager
     * @param http HttpSecurity Object
     * @return AuthenticationManager Instance
     * @throws Exception on Error
     */
    @Bean
     public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

}
