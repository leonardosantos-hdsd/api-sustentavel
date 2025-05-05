package com.api.sustentavel.controller;

import com.api.sustentavel.dto.AuthenticationRequest;
import com.api.sustentavel.dto.AuthenticationResponse;
import com.api.sustentavel.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        // Autentica as credenciais
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Carrega os dados do usuário
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // Gera o token JWT com base no nome do usuário
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}



//package com.api.sustentavel.controller;
//
//import com.api.sustentavel.dto.AuthenticationRequest;
//import com.api.sustentavel.dto.AuthenticationResponse;
//import com.api.sustentavel.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/login")
//    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//        );
//
////        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
////        String token = jwtUtil.generateToken(userDetails);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
//        String jwt = jwtUtil.generateToken(userDetails.getUsername()); // ✅ Correto
//
//
//        return new AuthenticationResponse(token);
//    }
//}



//package com.api.sustentavel.controller;
//
//import com.api.sustentavel.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody Map<String, String> body) {
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(body.get("username"), body.get("password"))
//        );
//
//        String token = jwtUtil.generateToken(auth.getName());
//        return Map.of("token", token);
//    }
//}
