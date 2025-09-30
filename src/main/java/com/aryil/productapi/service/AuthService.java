package com.aryil.productapi.service;

import com.aryil.productapi.dto.request.AuthRequest;
import com.aryil.productapi.dto.request.RegisterRequest;
import com.aryil.productapi.dto.response.AuthResponse;
import com.aryil.productapi.entity.Role;
import com.aryil.productapi.entity.User;
import com.aryil.productapi.repository.RoleRepository;
import com.aryil.productapi.repository.UserRepository;
import com.aryil.productapi.security.CustomUserDetails;

import com.aryil.productapi.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager am, UserRepository ur, RoleRepository rr,
                       PasswordEncoder enc, JwtUtil jwt) {
        this.authManager = am;
        this.userRepo = ur;
        this.roleRepo = rr;
        this.encoder = enc;
        this.jwtUtil = jwt;
    }

    public void register(RegisterRequest req) {
        if (userRepo.findByUsername(req.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        Role userRole = roleRepo.findByName("USER")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing default role USER"));

        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setEmail(req.getEmail());
        u.setRoles(Set.of(userRole));
        userRepo.save(u);
    }

    public AuthResponse login(AuthRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        User u = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserDetails ud = new CustomUserDetails(u);
        String token = jwtUtil.generateToken(ud);
        return new AuthResponse(token);
    }
}

