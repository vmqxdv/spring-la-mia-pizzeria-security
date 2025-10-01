package org.lessons.java.spring_la_mia_pizzeria_relazioni.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/pizzas/create", "/pizzas/edit/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.POST, "/pizzas/**").hasAuthority("ADMIN")
            .requestMatchers("/pizzas", "/pizzas/**").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers("/ingredients/create", "/ingredients/edit/**").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.POST, "/ingredients/**").hasAuthority("ADMIN")
            .requestMatchers("/ingredients", "/ingredients/**").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers("/", "/**").permitAll())
        .formLogin(Customizer.withDefaults())
        .logout(Customizer.withDefaults())
        .exceptionHandling(Customizer.withDefaults());

    return http.build();

  }

  @Bean
  @SuppressWarnings("deprecation")
  DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailService());

    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  DatabaseUserDetailService userDetailService() {
    return new DatabaseUserDetailService();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
