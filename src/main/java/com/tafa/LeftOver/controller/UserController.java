package com.tafa.LeftOver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/user/profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok("User profile data here");
    }

}
