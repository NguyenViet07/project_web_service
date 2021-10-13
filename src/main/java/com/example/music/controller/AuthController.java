/*
 * Created by duydatpham@gmail.com on 18/04/2021
 * Copyright (c) 2021 duydatpham@gmail.com
 */
package com.example.music.controller;



import com.example.music.dto.request.AuthenticationRequest;
import com.example.music.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity loginAction(@RequestBody AuthenticationRequest input) {
        return ResponseEntity.ok(authService.login(input));
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        return ResponseEntity.ok(authService.logout(request));
    }
}
