package com.api.sustentavel.config;

import com.api.sustentavel.security.CustomAccessDeniedHandler;
import com.api.sustentavel.security.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita @PreAuthorize
public class SecurityConfig {

    // Autenticação em memória
    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("user")
                .password("{noop}user123") // {noop} evita necessidade de encoder
                .roles("USER")
                .build();

        var admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    // Configuração de segurança HTTP
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // desativa CSRF em APIs REST
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/acoes/**").authenticated() // exige login para todos os endpoints da API
//                        .anyRequest().permitAll()
//                )
//                .httpBasic(Customizer.withDefaults()); // autenticação HTTP básica
//
//        return http.build();
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        // Endpoints abertos para qualquer usuário autenticado
//                        .requestMatchers("/api/v1/acoes", "/api/v1/acoes/categoria").hasAnyRole("USER", "ADMIN")
//
//                        // Endpoints que requerem ADMIN
//                        .requestMatchers("/api/v1/acoes/**").hasRole("ADMIN")
//
//                        // Qualquer outra requisição deve ser autenticada
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/acoes", "/api/v1/acoes/categoria").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/api/v1/acoes/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler)); // <-- Aqui
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // GETs liberados para USER e ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/v1/acoes/**", "/api/v1/acoes").hasAnyRole("USER", "ADMIN")

                        // POST, PUT, DELETE restritos ao ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/v1/acoes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/acoes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/acoes/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
//                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler)); // handler customizado
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint) // 401
                        .accessDeniedHandler(accessDeniedHandler)           // 403
                );
        return http.build();
    }



}
