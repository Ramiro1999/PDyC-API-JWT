package unnoba.edu.tp2.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import unnoba.edu.tp2.Security.filter.JWTAuthenticationFilter;
import unnoba.edu.tp2.Security.filter.JWTAuthorizationFilter;
import unnoba.edu.tp2.service.UserService;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration{
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService, AuthenticationConfiguration authenticationConfiguration) {
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .userDetailsService(userDetailsService)
                .cors().and().csrf().disable().authorizeRequests()
                .requestMatchers("/", "/login").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationConfiguration.getAuthenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}
