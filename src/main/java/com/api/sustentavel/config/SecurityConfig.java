package com.api.sustentavel.config;

import com.api.sustentavel.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

//    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
//        this.jwtAuthFilter = jwtAuthFilter;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



//package com.api.sustentavel.config;
//
//import com.api.sustentavel.repository.UserRepository;
//import com.api.sustentavel.security.JwtAuthenticationFilter;
//import com.api.sustentavel.security.CustomAccessDeniedHandler;
//import com.api.sustentavel.security.CustomAuthenticationEntryPoint;
//import com.api.sustentavel.service.UserDetailsServiceImpl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
//    private final CustomAccessDeniedHandler accessDeniedHandler;
//    private final UserDetailsService userDetailsService;
//
//    public SecurityConfig(
//            JwtAuthenticationFilter jwtAuthenticationFilter,
//            CustomAuthenticationEntryPoint authenticationEntryPoint,
//            CustomAccessDeniedHandler accessDeniedHandler,
//            UserDetailsService userDetailsService) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//        this.authenticationEntryPoint = authenticationEntryPoint;
//        this.accessDeniedHandler = accessDeniedHandler;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/login").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(csrf -> csrf.disable())
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/api/auth/**").permitAll()
////                        .anyRequest().authenticated()
////                )
////                .exceptionHandling(ex -> {
////                    ex.authenticationEntryPoint(authenticationEntryPoint);
////                    ex.accessDeniedHandler(accessDeniedHandler);
////                })
////                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
////    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // Esta parte é FUNDAMENTAL para injetar corretamente o AuthenticationManager
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
////    @Bean
////    public UserDetailsService userDetailsService(UserRepository userRepository) {
////        return new UserDetailsServiceImpl(userRepository);
////    }
//    @Bean
//    public UserDetailsService userDetailsService(UserDetailsServiceImpl impl) {
//        return impl;
//}
//
//
//}




//package com.api.sustentavel.config;
//
//import com.api.sustentavel.security.JwtAuthenticationFilter;
//import com.api.sustentavel.security.CustomAccessDeniedHandler;
//import com.api.sustentavel.security.CustomAuthenticationEntryPoint;
//import com.api.sustentavel.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    private final JwtUtil jwtUtil;
//    private final UserDetailsService userDetailsService;
//
//    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Autowired
//    private CustomAccessDeniedHandler accessDeniedHandler;
//
//    @Autowired
//    private CustomAuthenticationEntryPoint authenticationEntryPoint;
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/acoes/**", "/api/v1/acoes").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/v1/acoes/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/acoes/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/acoes/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint(authenticationEntryPoint)
//                        .accessDeniedHandler(accessDeniedHandler)
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//}




//package com.api.sustentavel.config;
//
//import com.api.sustentavel.security.JwtAuthenticationFilter;
//import com.api.sustentavel.security.CustomAccessDeniedHandler;
//import com.api.sustentavel.security.CustomAuthenticationEntryPoint;
//import com.api.sustentavel.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity // Habilita @PreAuthorize
//public class SecurityConfig {
//
//    private final JwtUtil jwtUtil;
//    private final UserDetailsService userDetailsService;
//
//    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
//    // Autenticação em memória
//    @Bean
//    public UserDetailsService userDetailsService() {
//        var user = User.withUsername("user")
//                .password("{noop}user123") // {noop} evita necessidade de encoder
//                .roles("USER")
//                .build();
//
//        var admin = User.withUsername("admin")
//                .password("{noop}admin123")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
//    @Autowired
//    private CustomAccessDeniedHandler accessDeniedHandler;
//
//    @Autowired
//    private CustomAuthenticationEntryPoint authenticationEntryPoint;
//
////    @Autowired
////    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
////    @Bean
////    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
////        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
////    }
//
//    // Configuração de segurança HTTP
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(csrf -> csrf.disable()) // desativa CSRF em APIs REST
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/api/v1/acoes/**").authenticated() // exige login para todos os endpoints da API
////                        .anyRequest().permitAll()
////                )
////                .httpBasic(Customizer.withDefaults()); // autenticação HTTP básica
////
////        return http.build();
////    }
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(csrf -> csrf.disable())
////                .authorizeHttpRequests(auth -> auth
////                        // Endpoints abertos para qualquer usuário autenticado
////                        .requestMatchers("/api/v1/acoes", "/api/v1/acoes/categoria").hasAnyRole("USER", "ADMIN")
////
////                        // Endpoints que requerem ADMIN
////                        .requestMatchers("/api/v1/acoes/**").hasRole("ADMIN")
////
////                        // Qualquer outra requisição deve ser autenticada
////                        .anyRequest().authenticated()
////                )
////                .httpBasic(Customizer.withDefaults());
////
////        return http.build();
////    }
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(csrf -> csrf.disable())
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/api/v1/acoes", "/api/v1/acoes/categoria").hasAnyRole("USER", "ADMIN")
////                        .requestMatchers("/api/v1/acoes/**").hasRole("ADMIN")
////                        .anyRequest().authenticated()
////                )
////                .httpBasic(Customizer.withDefaults())
////                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler)); // <-- Aqui
////
////        return http.build();
////    }
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(csrf -> csrf.disable())
////                .authorizeHttpRequests(auth -> auth
////                        // GETs liberados para USER e ADMIN
////                        .requestMatchers(HttpMethod.GET, "/api/v1/acoes/**", "/api/v1/acoes").hasAnyRole("USER", "ADMIN")
////
////                        // POST, PUT, DELETE restritos ao ADMIN
////                        .requestMatchers(HttpMethod.POST, "/api/v1/acoes/**").hasRole("ADMIN")
////                        .requestMatchers(HttpMethod.PUT, "/api/v1/acoes/**").hasRole("ADMIN")
////                        .requestMatchers(HttpMethod.DELETE, "/api/v1/acoes/**").hasRole("ADMIN")
////
////                        .anyRequest().authenticated()
////                )
////                .httpBasic(Customizer.withDefaults())
//////                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler)); // handler customizado
////                .exceptionHandling(ex -> ex
////                        .authenticationEntryPoint(authenticationEntryPoint) // 401
////                        .accessDeniedHandler(accessDeniedHandler)           // 403
////                );
////        return http.build();
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/acoes/**", "/api/v1/acoes").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/v1/acoes/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/acoes/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/acoes/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
////                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
////                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
