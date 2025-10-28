package com.productstore.repo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.productstore.model.Item;

@Repository
public interface ItemRepo extends MongoRepository<Item, String> {
	
	List<Item> findByNameContainingIgnoreCase(String name);
	
	List<Item> findByDescriptionContainingIgnoreCase(String keyword);
}

