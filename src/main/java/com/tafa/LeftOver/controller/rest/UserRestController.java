package com.tafa.LeftOver.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	   @Autowired
	    private BCryptPasswordEncoder passwordEncoder; 
	
	/**@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
			return new ResponseEntity<>(savedUser, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }**/
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        User savedUser = userService.registerUser(user);
	        
	        response.put("msg", "User is saved successfully");
	        response.put("data", savedUser);
	        response.put("status", HttpStatus.OK.value());
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.put("msg", "User registration failed");
	        response.put("error", e.getMessage());
	        response.put("status", HttpStatus.BAD_REQUEST.value());
	        
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Map<String, Object> response = new HashMap<>();

        // Find user by username
        User user = userService.findByUsername(username);
        if (user == null) {
            response.put("msg", "User not found");
            response.put("data", user);
            response.put("status", 404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Validate password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            response.put("msg", "Invalid credentials");
            response.put("data", user);
            response.put("status", 401);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // Login successful
        response.put("msg", "Login successful");
        response.put("data", user); 
        response.put("status", 200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	

}
