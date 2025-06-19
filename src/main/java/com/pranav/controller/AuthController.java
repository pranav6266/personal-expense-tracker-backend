package com.pranav.controller;

import com.pranav.dto.AppUserDTO;
import com.pranav.dto.AuthDTO;
import com.pranav.dto.AuthResponseDTO;
import com.pranav.model.AppUser;
import com.pranav.service.AuthService;
import com.pranav.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@RequestBody AppUserDTO appUserDTO){
       AuthResponseDTO response = authService.registerUser(appUserDTO);
       if("SUCCESS".equals(response.getMessage())){
           return ResponseEntity.ok(response);
       }else {
           return ResponseEntity.badRequest().body(response);
       }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO authDTO){
        AuthResponseDTO response = authService.loginUser(authDTO);
        if("SUCCESS".equals(response.getMessage())){
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }
}
