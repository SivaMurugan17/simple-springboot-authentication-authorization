package com.siva.Spring.controller;

import com.siva.Spring.jwt.JwtUtils;
import com.siva.Spring.jwt.UserDetailsImpl;
import com.siva.Spring.jwt.UserDetailsServiceImpl;
import com.siva.Spring.model.LoginRequest;
import com.siva.Spring.model.SignupRequest;
import com.siva.Spring.model.User;
import com.siva.Spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@RequestBody LoginRequest loginRequest){
        log.info("Reached here"+loginRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie cookie = jwtUtils.generateJwtCookie(userDetails);
        return  ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(new User(userDetails.getId(),userDetails.getUsername(),encoder.encode(userDetails.getPassword())));
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@RequestBody SignupRequest signupRequest){
        User user = userService.addUser(new User(signupRequest.getUsername(),encoder.encode(signupRequest.getPassword())));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/signout")
    public ResponseEntity logoutUser(){
        ResponseCookie cleanCookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cleanCookie.toString()).body("You are logged out");
    }
}
