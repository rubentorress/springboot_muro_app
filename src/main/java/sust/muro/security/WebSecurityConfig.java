package sust.muro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

@Bean
  public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((request) -> request
            .requestMatchers("/").authenticated()
            .requestMatchers("/api/**").authenticated()
            .anyRequest().permitAll()
        )
        .formLogin((form) -> form
            .loginPage("/login").permitAll().defaultSuccessUrl("/")
        )
        .logout((logout) -> logout.permitAll());
    
    return http.build();
    }
}
