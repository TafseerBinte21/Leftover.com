package com.tafa.LeftOver.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tafa.LeftOver.dto.postDTO;


public interface PostService {
    
	
    Map<String, Object> getPostsByUserId(Long userId);

}
