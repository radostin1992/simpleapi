package com.myproject.simpleapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final CredentialsProperties credentials;

    public SecurityConfig(CredentialsProperties credentials) {
        this.credentials = credentials;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                .requestMatchers(HttpMethod.GET, "/", "/actuator/**", "/joke", "/user").hasAnyRole("INTERNAL", "APPAPI", "SWAGGER")
                .requestMatchers(HttpMethod.POST, "/user").hasAnyRole("INTERNAL", "APPAPI", "SWAGGER")
                .requestMatchers(HttpMethod.PATCH, "/user/**").hasAnyRole("INTERNAL", "APPAPI", "SWAGGER")
                .requestMatchers(HttpMethod.DELETE, "/user/**").hasAnyRole("INTERNAL", "APPAPI")
                .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails appapi = User.withUsername(credentials.getAppapi().getUsername())
                .password("{noop}" + credentials.getAppapi().getPassword()) // {noop} = No password encoding for simplicity
                .roles("APPAPI")
                .build();

        UserDetails internal = User.withUsername(credentials.getInternal().getUsername())
                .password("{noop}" + credentials.getInternal().getPassword())
                .roles("INTERNAL")
                .build();

        UserDetails swagger = User.withUsername(credentials.getSwagger().getUsername())
                .password("{noop}" + credentials.getSwagger().getPassword())
                .roles("SWAGGER")
                .build();

        return new InMemoryUserDetailsManager(appapi, internal, swagger);
    }
}
