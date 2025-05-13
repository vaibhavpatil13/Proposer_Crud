package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.UserLogInRepository;

@Service
public class UserService {

    public UserService(UserLogInRepository userRepo, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	@Autowired
    private UserLogInRepository userRepo;  // Repository to interact with DB

    @Autowired
    private PasswordEncoder passwordEncoder;  // For password hashing

    @Autowired
    private AuthenticationManager authenticationManager;  // For authentication

    @Autowired
    private JwtUtil jwtUtil;  // JWT utility class for generating tokens
   
    public String register(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password must not be null");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        userRepo.save(user);

        return "User registered successfully!";
    }


    public String login(String username, String password) {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)  
        );

        final UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username, password, new ArrayList<>()
        );

        return jwtUtil.generateToken(userDetails);
    }

}
