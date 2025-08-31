package com.musicly.store.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.musicly.store.Domain.User.User;
import com.musicly.store.Domain.User.UserRepository;
import com.musicly.store.Models.JwtResponse;
import com.musicly.store.Models.LoginRequest;
import com.musicly.store.Models.MessageResponse;
import com.musicly.store.Models.SignupRequest;
import com.musicly.store.authenticator.JwtUtil;
import com.musicly.store.enums.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {

        // Check if email already exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email already exists"));
        }

        // Create new user
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setUserName(signupRequest.getUserName());
        
        // FIXED: Encode the password before saving
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        // Set role
        String usrRoleStr = signupRequest.getRole();
        Roles role = Roles.CLIENT;

        if (usrRoleStr != null) {
            switch (usrRoleStr.toLowerCase()) { // FIXED: Added toLowerCase() for case insensitivity
                case "client":
                    role = Roles.CLIENT;
                    break;
                case "admin":
                    role = Roles.ADMIN;
                    break;
                default:
                    role = Roles.CLIENT;
            }
        } else {
            role = Roles.CLIENT;
        }

        user.setRole(role);
        userRepository.save(user);
        
        // FIXED: Corrected typo in response message
        return ResponseEntity.ok(new MessageResponse("Sign up successful"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) { // FIXED: Method name typo
    
        try {

            if(loginRequest.getEmail() ==null || loginRequest.getEmail().trim().isEmpty()){
                return ResponseEntity.badRequest().body(new MessageResponse("Email is Required"));
            }

            if(loginRequest.getPassword()==null || loginRequest.getPassword().trim().isEmpty()){
                return ResponseEntity.badRequest().body(new MessageResponse("Password is Required"));
            }

            String email = loginRequest.getEmail().trim();
            String password = loginRequest.getPassword().trim();

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email,password)
            );
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found after authentication"));

            String jwt = jwtUtil.generateTokenWithRole(userDetails.getUsername(),user.getRole().name());

            return ResponseEntity.ok(new JwtResponse(
                    jwt,
                    user.getEmail(),
                    user.getId(),
                    user.getRole().toString()
            ));
            
        } catch (BadCredentialsException e) {
            logger.warn("Authentiction failed for email: {}", loginRequest.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid Email or Password"));
        } catch (DisabledException e) { // FIXED: Added general exception handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Account is disabled"));
        }
        catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Account is locked"));
                
        } catch (AccountExpiredException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Account has expired"));
                
        } catch (CredentialsExpiredException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Credentials have expired"));
                
        } catch (DataAccessException e) {
        // Database related errors
            logger.error("Database error during authentication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Authentication service temporarily unavailable"));
                
        } catch (Exception e) {
        // Log unexpected errors for debugging
            logger.error("Unexpected error during authentication for email: {}",
                loginRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Authentication failed"));
    }
    }
}