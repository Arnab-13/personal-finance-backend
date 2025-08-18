package com.example.personalFiManager.controller;

import com.example.personalFiManager.dto.AuthRequest;
import com.example.personalFiManager.dto.UserDto;
import com.example.personalFiManager.model.User;
import com.example.personalFiManager.repository.UserRepository;
import com.example.personalFiManager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserDto userDto) {
        // Check if user already exists
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return "Error: Username is already taken!";
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        // Scramble the password before saving!
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);

        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return jwt; // Return the access badge
    }
}
