package com.tafa.LeftOver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tafa.entity.User;


@Repository
public interface UsersRepository  extends JpaRepository<User, Long>{
	
    boolean existsByEmail(String email);
    
    User findByEmail(String email);


}
