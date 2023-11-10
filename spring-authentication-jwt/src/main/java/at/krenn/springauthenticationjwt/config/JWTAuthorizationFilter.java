package at.krenn.springauthenticationjwt.config;

import at.krenn.springauthenticationjwt.domain.User;
import at.krenn.springauthenticationjwt.foundation.JWTFactory;
import at.krenn.springauthenticationjwt.service.CustomUserDetailsService;
import at.krenn.springauthenticationjwt.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTFactory jwtFactory;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*try {
            String jwtToken = jwtFactory.resolveToken(request);
            if (jwtToken==null){
                return;
            }
            String email = jwtFactory.extractEmail(jwtToken);
            if (!email.isEmpty() && SecurityContextHolder.getContext().getAuthentication()  ==null){
                UserDetails user = customUserDetailsService.loadUserByUsername(email);
               if(jwtFactory.isTokenValid(jwtToken, user)){
                   Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getAuthorities());
                   SecurityContextHolder.getContext().setAuthentication(authentication);
               }
            }
        } catch (Exception e){

        }
        filterChain.doFilter(request,response);

    */

        try {
            String jwtToken = jwtFactory.resolveToken(request);
            if (jwtToken != null) {
                String email = jwtFactory.extractEmail(jwtToken);
                if (!email.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails user = customUserDetailsService.loadUserByUsername(email);
                    if (jwtFactory.isTokenValid(jwtToken, user)) {
                        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error processing JWT token", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        filterChain.doFilter(request, response);

    }

}
