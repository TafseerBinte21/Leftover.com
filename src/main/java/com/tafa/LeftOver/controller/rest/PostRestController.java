package com.tafa.LeftOver.controller.rest;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tafa.LeftOver.dto.postDTO;
import com.tafa.LeftOver.implementation.PostServiceImpl;
import com.tafa.LeftOver.repository.PostRepository;
import com.tafa.LeftOver.services.PostService;
import com.tafa.LeftOver.services.UserService;
import com.tafa.entity.Post;
import com.tafa.entity.User;

@RestController
@RequestMapping("/rest/api/user")
public class PostRestController {
	
	 @Autowired
	 private PostRepository postRepository;
	 
	 @Autowired
	 private UserService userservice;
	 
	 @Autowired
	 private PostServiceImpl postService;
	 
	 @RequestMapping(value = "/post/save", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/json")
	 public ResponseEntity<Map<String, Object>> createPost(
	         @RequestParam("title") String title,
	         @RequestParam("description") String description,
	         @RequestParam(value = "image", required = false) MultipartFile image,
	         @RequestParam("userId") Long userId
	 ) {
	     Map<String, Object> response = new HashMap<>();

	     try {
	         User user = userservice.findById(userId)
	                 .orElseThrow(() -> new RuntimeException("User not found"));

	         Post post = new Post();
	         post.setTitle(title);
	         post.setDescription(description);

	         // Only set image if present and not empty
	         System.err.println("image "+ image );
	         
	         if (image != null && !image.isEmpty()) {
	             post.setImage(image.getBytes());
		         System.err.println("imagebyte "+ image.getBytes() );

	         } else {
	             post.setImage(null); // explicitly setting to null (optional)
	         }

	         post.setUser(user);
	         post.setReactCount(0);
	         post.setShareCount(0);
	         post.setCurrentTime(java.time.LocalDateTime.now());

	         Post savedPost = postRepository.save(post);

	         // Build DTO
	         postDTO postDto = new postDTO();
	         postDto.setId(savedPost.getId());
	         postDto.setTitle(savedPost.getTitle());
	         postDto.setDescription(savedPost.getDescription());
	         postDto.setUserId(user.getId());
	         postDto.setImage(savedPost.getImage()); // may be null
	         postDto.setCurrentTime(savedPost.getCurrentTime().toString());
	         postDto.setReactCount(savedPost.getReactCount());
	         postDto.setShareCount(savedPost.getShareCount());

	         response.put("msg", "Post created successfully");
	         response.put("data", postDto);
	         response.put("status", HttpStatus.OK.value());

	         return new ResponseEntity<>(response, HttpStatus.OK);

	     } catch (IOException e) {
	         response.put("msg", "Post creation failed (IO Error)");
	         response.put("error", e.getMessage());
	         response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

	         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	     } catch (RuntimeException ex) {
	         response.put("msg", "Post creation failed");
	         response.put("error", ex.getMessage());
	         response.put("status", HttpStatus.BAD_REQUEST.value());

	         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	     }
}
	 
	 
	 @RequestMapping(value = "/postdetails", method = RequestMethod.GET)
	    public ResponseEntity<Map<String, Object>> getAllPosts() {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            List<Post> posts = postRepository.findAll();
	            List<Map<String, Object>> postData = new ArrayList<>();

	            for (Post post : posts) {
	                Map<String, Object> postMap = new HashMap<>();
	                postMap.put("id", post.getId());
	                postMap.put("title", post.getTitle());
	                postMap.put("description", post.getDescription());
	                postMap.put("reactCount", post.getReactCount());
	                postMap.put("shareCount", post.getShareCount());
	                postMap.put("createdTime", post.getCurrentTime());

	                // Get publisher username from User table
	                User user = post.getUser();
	                postMap.put("publisherName", user != null ? user.getUsername() : "Unknown");

	                // Convert image to Base64 (or return null if no image)
	                byte[] imageBytes = post.getImage();
	                postMap.put("image", imageBytes != null ? Base64.getEncoder().encodeToString(imageBytes) : null);

	                postData.add(postMap);
	            }

	            response.put("msg", "Posts fetched successfully");
	            response.put("data", postData);
	            response.put("status", HttpStatus.OK.value());

	            return ResponseEntity.ok(response);

	        } catch (Exception e) {
	            response.put("msg", "Failed to fetch posts");
	            response.put("error", e.getMessage());
	            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
	            return ResponseEntity.internalServerError().body(response);
	        }
	    }
	 
	 
	 @GetMapping("/owner-post/{userId}")
	    public Map<String, Object> getPostsByUserId(@PathVariable Long userId) {
	        return postService.getPostsByUserId(userId);
	    }
}
