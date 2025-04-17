package com.tafa.LeftOver.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.tafa.LeftOver.dto.userSummaryDto;

@Service
public class CommentService {
	
	
	   @PersistenceContext
	    private EntityManager entityManager;

	    public List<userSummaryDto> getCommentersByPostId(Long postId) {
	        String sql = "SELECT u.username,  encode(u.image, 'base64') as image " +
	                     "FROM leftover.comment c " +
	                     "JOIN leftover.users u ON c.user_id = u.id " +
	                     "WHERE c.post_id = :postId";

	        javax.persistence.Query query = entityManager.createNativeQuery(sql);
	        query.setParameter("postId", postId);

	        List<Object[]> results = query.getResultList();
	        List<userSummaryDto> commenterList = new ArrayList<>();

	        for (Object[] row : results) {
	            String username = (String) row[0];
	            String image = (String) row[1];

	            commenterList.add(new userSummaryDto(username,image));
	        }

	        return commenterList;
	    }

}
