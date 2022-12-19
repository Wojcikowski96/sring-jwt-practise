package com.example.demo.controller;

import com.example.demo.configuration.AuthenticationRequest;
import com.example.demo.configuration.JwtUtils;
import com.example.demo.configuration.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final AuthenticationManager authenticationManager;

    private final UserDao userDao;
    private final JwtUtils jwtUtils;
    @GetMapping("/greeting")
    public String greetings(){
        return "Hello";
    }
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        final UserDetails user = userDao.findUserByEmail(request.getEmail());
        if(user !=null){
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        return ResponseEntity.status(400).body("Some error happened");
    };
}
