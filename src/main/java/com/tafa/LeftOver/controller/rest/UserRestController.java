package com.tafa.LeftOver.controller.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tafa.LeftOver.dto.userDTO;
import com.tafa.LeftOver.repository.UsersRepository;
import com.tafa.LeftOver.services.UserService;
import com.tafa.entity.User;


@RestController
@RequestMapping("/rest/api/user")
public class UserRestController{
	
	@Autowired
	private UserService userService;
	
	   @Autowired
	    private BCryptPasswordEncoder passwordEncoder; 
	   @Autowired
		private UsersRepository userRepository;
	   
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
	
	@RequestMapping(value = "/send-request", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> sendFriendRequest(@RequestBody Map<String, Long> requestMap) {
	    Long requesterId = requestMap.get("requesterId");
	    Long receiverId = requestMap.get("receiverId");

	    Map<String, Object> response = new HashMap<>();

	    if (requesterId == null || receiverId == null) {
	        response.put("msg", "Both requesterId and receiverId are required.");
	        response.put("data", null);
	        response.put("status", 400);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    if (requesterId.equals(receiverId)) {
	        response.put("msg", "You cannot send a request to yourself!");
	        response.put("data", null);
	        response.put("status", 400);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    User requester = userService.findById(requesterId).orElse(null);
	    User receiver = userService.findById(receiverId).orElse(null);

	    if (requester == null || receiver == null) {
	        response.put("msg", "One or both users not found.");
	        response.put("data", null);
	        response.put("status", 404);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    if (requester.getSentRequests().contains(receiver)) {
	        response.put("msg", "Friend request already sent.");
	        response.put("data", null);
	        response.put("status", 409); // Conflict
	        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	    }

	    // 💡 Call the service method instead of writing logic here
	    userService.requestFriend(requester, receiver);

	    response.put("msg", "Friend request sent successfully.");
	    response.put("data", receiver.getId());
	    response.put("status", 200);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/cancel-request", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> cancelFriendRequest(@RequestBody Map<String, Long> requestMap) {
	    Long requesterId = requestMap.get("requesterId");
	    Long receiverId = requestMap.get("receiverId");

	    Map<String, Object> response = new HashMap<>();

	    if (requesterId == null || receiverId == null) {
	        response.put("msg", "Both requesterId and receiverId are required.");
	        response.put("data", null);
	        response.put("status", 400);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    User requester = userService.findById(requesterId).orElse(null);
	    User receiver = userService.findById(receiverId).orElse(null);

	    if (requester == null || receiver == null) {
	        response.put("msg", "One or both users not found.");
	        response.put("data", null);
	        response.put("status", 404);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    if (!requester.getSentRequests().contains(receiver)) {
	        response.put("msg", "No friend request found to cancel.");
	        response.put("data", null);
	        response.put("status", 404);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    // 💡 Call the service method here
	    userService.cancelRequest(requester, receiver);

	    response.put("msg", "Friend request cancelled successfully.");
	    response.put("data", receiver.getId());
	    response.put("status", 200);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/accept-request", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> acceptFriendRequest(@RequestBody Map<String, Long> requestMap) {
	    Long requesterId = requestMap.get("requesterId");
	    Long receiverId = requestMap.get("receiverId");

	    Map<String, Object> response = new HashMap<>();

	    if (requesterId == null || receiverId == null) {
	        response.put("msg", "Both requesterId and receiverId are required.");
	        response.put("data", null);
	        response.put("status", 400);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    User requester = userService.findById(requesterId).orElse(null);
	    User receiver = userService.findById(receiverId).orElse(null);

	    if (requester == null || receiver == null) {
	        response.put("msg", "One or both users not found.");
	        response.put("data", null);
	        response.put("status", 404);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    if (!receiver.getReceivedRequests().contains(requester)) {
	        response.put("msg", "No friend request found to accept.");
	        response.put("data", null);
	        response.put("status", 404);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    // 💡 Call the service method to accept the request
	    userService.acceptRequest(requester, receiver);

	    // 💡 Update friendCount using your PostgreSQL function
	    userRepository.updateFriendCount(requester.getId());  // Call the custom DB function
	    userRepository.updateFriendCount(receiver.getId());   // Update for both users
	    
	    System.err.println("friend count increases");

	    response.put("msg", "Friend request accepted successfully.");
	    response.put("data", receiver.getId());
	    response.put("status", 200);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/reject-request", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> rejectFriendRequest(@RequestBody Map<String, Long> requestMap) {
	    Long requesterId = requestMap.get("requesterId");
	    Long receiverId = requestMap.get("receiverId");

	    Map<String, Object> response = new HashMap<>();

	    if (requesterId == null || receiverId == null) {
	        response.put("msg", "Both requesterId and receiverId are required.");
	        response.put("data", null);
	        response.put("status", 400);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    User requester = userService.findById(requesterId).orElse(null);
	    User receiver = userService.findById(receiverId).orElse(null);

	    if (requester == null || receiver == null) {
	        response.put("msg", "One or both users not found.");
	        response.put("data", null);
	        response.put("status", 404);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    if (!receiver.getReceivedRequests().contains(requester)) {
	        response.put("msg", "No friend request found to reject.");
	        response.put("data", null);
	        response.put("status", 404);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    // 💡 Call the service method to reject the request
	    userService.rejectFriendRequest(receiver, requester);

	    response.put("msg", "Friend request rejected successfully.");
	    response.put("data", requester.getId());
	    response.put("status", 200);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/delete-friend", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Map<String, Object>> deleteFriend(@RequestBody Map<String, Long> requestMap) {
	    Long userId = requestMap.get("userId");
	    Long friendId = requestMap.get("friendId");

	    Map<String, Object> response = new HashMap<>();

	    if (userId == null || friendId == null) {
	        response.put("msg", "Both userId and friendId are required.");
	        response.put("data", null);
	        response.put("status", 400);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    User user = userService.findById(userId).orElse(null);
	    User friend = userService.findById(friendId).orElse(null);

	    if (user == null || friend == null) {
	        response.put("msg", "One or both users not found.");
	        response.put("data", null);
	        response.put("status", 404);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    if (!user.getFriendlist().contains(friend)) {
	        response.put("msg", "The user and friend are not in the friend list.");
	        response.put("data", null);
	        response.put("status", 404);
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    // 💡 Call the service method to delete the friend
	    userService.delete(user, friend);

	    // 💡 Update friendCount using your PostgreSQL function
//	    userRepository.updateFriendCount(user.getId());  // Call the custom DB function
//	    userRepository.updateFriendCount(friend.getId()); // Update for the other user as well

	    response.put("msg", "Friend deleted successfully.");
	    response.put("data", friend.getId());
	    response.put("status", 200);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
//	@RequestMapping(value = "/get-user-details/{userId}", method = RequestMethod.GET, produces = "application/json")
//    public ResponseEntity<Map<String, Object>> getUserDetails(@PathVariable("userId") Long userId) {
//        Map<String, Object> response = new HashMap<>();
//
//        if (userId == null) {
//            response.put("msg", "userId is required.");
//            response.put("data", null);
//            response.put("status", 400);
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        User user = userService.findById(userId).orElse(null);
//
//        if (user == null) {
//            response.put("msg", "User not found.");
//            response.put("data", null);
//            response.put("status", 404);
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//
//        response.put("msg", "User details fetched successfully.");
//        response.put("data", user); // Returns the user object
//        response.put("status", 200);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

	 @GetMapping("/get-user-details/{userId}")
	    public ResponseEntity<Map<String, Object>> getUserDetails(@PathVariable("userId") Long userId) {
	        Map<String, Object> response = userService.getUserDetailsById(userId);
	        return new ResponseEntity<>(response, HttpStatus.valueOf((Integer) response.get("status")));
	    }


}
