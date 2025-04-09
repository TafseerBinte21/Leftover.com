package com.tafa.LeftOver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tafa.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
