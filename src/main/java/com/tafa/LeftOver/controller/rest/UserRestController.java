package com.tafa.LeftOver.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tafa.LeftOver.services.UserService;
import com.tafa.entity.User;


@RestController
@RequestMapping("/rest/api/user")
public class UserRestController{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
//            return ResponseEntity.ok(savedUser);
			return new ResponseEntity<>(savedUser, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ResponseEntity<?> loginUser(Authentication authentication) {
//        if (authentication != null && authentication.isAuthenticated()) {
//            return ResponseEntity.ok("Login successful for user: " + authentication.getName());
//        }
//        return ResponseEntity.status(401).body("Invalid credentials");
//    }
	
	
	@GetMapping("/user/profile")
    public ResponseEntity<?> getProfile() {
        // Retrieve and return user profile details
        return ResponseEntity.ok("User profile data here");
    }

}
