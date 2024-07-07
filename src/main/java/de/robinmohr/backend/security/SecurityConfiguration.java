package de.robinmohr.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


/**
 * The SecurityConfiguration class is responsible for configuring the security settings of the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${security.basic.username}")
    private String username;

    @Value("${security.basic.password}")
    private String password;

    /**
     * Defines the security filter chain for the application.
     *
     * @param http the HttpSecurity object to configure the security filter chain
     * @return the configured SecurityFilterChain for the application
     * @throws Exception if an exception occurs while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(httpRequests -> httpRequests.requestMatchers(HttpMethod.PUT, "/api/v1/products/**")
                                                               .authenticated()
                                                               .requestMatchers(HttpMethod.POST, "/api/v1/products/**")
                                                               .authenticated()
                                                               .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**")
                                                               .authenticated()
                                                               .anyRequest()
                                                               .permitAll())
            .httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Returns an implementation of UserDetailsService that provides user details
     * for a single user with ADMIN role.
     *
     * @return an instance of UserDetailsService configured with a single user with ADMIN role
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                               .username(username)
                               .password(password)
                               .roles("USER")
                               .build();

        return new InMemoryUserDetailsManager(user);
    }
}
