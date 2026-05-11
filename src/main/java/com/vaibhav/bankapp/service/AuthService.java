package com.vaibhav.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vaibhav.bankapp.dto.AuthResponseDTO;
import com.vaibhav.bankapp.dto.LoginRequestDTO;
import com.vaibhav.bankapp.dto.RegisterRequestDTO;
import com.vaibhav.bankapp.entity.User;
import com.vaibhav.bankapp.repository.UserRepository;
import com.vaibhav.bankapp.security.JwtUtil;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ApplicationContext applicationContext;  // ← inject ApplicationContext

    // get AuthenticationManager lazily to avoid circular dependency
    private AuthenticationManager getAuthenticationManager() {
        return applicationContext.getBean(AuthenticationManager.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .roles("USER")
            .build();
    }

    public String register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        // use lazy getter instead of @Autowired field
        getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponseDTO(token, request.getEmail());
    }
}