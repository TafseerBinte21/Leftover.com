package com.tafa.LeftOver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tafa.entity.MarketPlace;
import com.tafa.entity.Post;


@Repository
public interface MarketRepository extends JpaRepository<MarketPlace, Long>{
	
    List<MarketPlace> findByName(String name);


}
