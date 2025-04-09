package com.tafa.LeftOver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tafa.entity.User;


@Repository
public interface UsersRepository  extends JpaRepository<User, Long>{
	
    boolean existsByEmail(String email);
    
    User findByEmail(String email);
    
    User findByUsername(String username);
    
    
    Optional<User> findById(Long id);



}
