package com.tafa.LeftOver.controller.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tafa.LeftOver.dto.marketDto;
import com.tafa.LeftOver.repository.MarketRepository;
import com.tafa.entity.MarketPlace;
import com.tafa.entity.Post;
import com.tafa.entity.User;

@RestController
@RequestMapping("/rest/api/user")
public class MarketRestController {
	
	@Autowired
	private MarketRepository marketrepo ;
	
	
	@RequestMapping(value = "/marketobject/save", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/json")
	 public ResponseEntity<Map<String, Object>> createPost(
	         @RequestParam("name") String name,
	         @RequestParam("description") String description,
	         @RequestParam(value = "image", required = false) MultipartFile image,
	         @RequestParam("price") String price
	 ) {
	     Map<String, Object> response = new HashMap<>();

	     try {
	         

	    	 MarketPlace post = new MarketPlace();
	         post.setName(name); 
	         post.setDescription(description);
	         post.setPrice(price);

	         // Only set image if present and not empty
	         System.err.println("image "+ image );
	         
	         if (image != null && !image.isEmpty()) {
	             post.setImage(image.getBytes());
		         System.err.println("imagebyte "+ image.getBytes() );

	         } else {
	             post.setImage(null); // explicitly setting to null (optional)
	         }


	         MarketPlace savedPost = marketrepo.save(post);

	         // Build DTO
	         marketDto mDto = new marketDto();
	         mDto.setId(savedPost.getId());
	         mDto.setName(savedPost.getName());
	         mDto.setDescription(savedPost.getDescription());
	         mDto.setPrice(savedPost.getPrice());
	         mDto.setImage(savedPost.getImage()); // may be null
	         

	         response.put("msg", "Market Object saved successfully");
	         response.put("data", mDto);
	         response.put("status", HttpStatus.OK.value());

	         return new ResponseEntity<>(response, HttpStatus.OK);

	     } catch (IOException e) {
	         response.put("msg", "Market Object creation failed (IO Error)");
	         response.put("error", e.getMessage());
	         response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

	         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	     } catch (RuntimeException ex) {
	         response.put("msg", "Market Object creation failed");
	         response.put("error", ex.getMessage());
	         response.put("status", HttpStatus.BAD_REQUEST.value());

	         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	     }
}
	
	 
	 @RequestMapping(value = "/marketplacedetails", method = RequestMethod.GET)
	    public ResponseEntity<Map<String, Object>> getAllPosts() {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            List<MarketPlace> markets = marketrepo.findAll();
	            List<Map<String, Object>> marketData = new ArrayList<>();

	            for (MarketPlace market : markets) {
	                Map<String, Object> marketMap = new HashMap<>();
	                marketMap.put("id", market.getId());
	                marketMap.put("name", market.getName());
	                marketMap.put("description", market.getDescription());
	                marketMap.put("price", market.getPrice());
	
	                // Convert image to Base64 (or return null if no image)
	                byte[] imageBytes = market.getImage();
	                marketMap.put("image", imageBytes != null ? Base64.getEncoder().encodeToString(imageBytes) : null);

	                marketData.add(marketMap);
	            }

	            response.put("msg", "MarketPlace Object fetched successfully");
	            response.put("data", marketData);
	            response.put("status", HttpStatus.OK.value());

	            return ResponseEntity.ok(response);

	        } catch (Exception e) {
	            response.put("msg", "Failed to fetch MarketPlace Object");
	            response.put("error", e.getMessage());
	            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
	            return ResponseEntity.internalServerError().body(response);
	        }
	    }

}
