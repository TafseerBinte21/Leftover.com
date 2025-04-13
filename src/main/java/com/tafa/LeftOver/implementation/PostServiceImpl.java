package com.tafa.LeftOver.implementation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tafa.LeftOver.dto.postDTO;
import com.tafa.LeftOver.repository.PostRepository;
import com.tafa.LeftOver.services.PostService;
import com.tafa.entity.Post;

@Service
public class PostServiceImpl implements PostService {
	
	 @Autowired
	    private PostRepository postRepository;

	 @Override
	    public Map<String, Object> getPostsByUserId(Long userId) {
	        Map<String, Object> response = new HashMap<>();

	        List<Post> posts = postRepository.findByUserId(userId);

	        if (posts.isEmpty()) {
	            response.put("msg", "No posts found for user with ID: " + userId);
	            response.put("data", Collections.emptyList());
	            response.put("status", 404);
	        } else {
	            List<postDTO> postDTOList = posts.stream().map(post -> new postDTO(
	                    post.getId(),
	                    post.getTitle(),
	                    post.getDescription(),
	                    post.getImage(),
	                    post.getCurrentTime().toString(),
	                    post.getReactCount(),
	                    post.getShareCount(),
	                    post.getUser().getId()
	            )).collect(Collectors.toList());

	            response.put("msg", "Posts fetched successfully.");
	            response.put("data", postDTOList);
	            response.put("status", 200);
	        }

	        return response;
	    }

}
