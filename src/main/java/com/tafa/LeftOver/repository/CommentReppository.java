package com.tafa.LeftOver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tafa.LeftOver.dto.userSummaryDto;
import com.tafa.entity.Comment;

@Repository
public interface CommentReppository extends JpaRepository<Comment, Long>{
	


}
