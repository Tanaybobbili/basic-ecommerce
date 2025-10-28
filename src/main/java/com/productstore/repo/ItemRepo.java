package com.productstore.repo;

import com.productstore.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends MongoRepository<Item, String> {

    List<Item> findByNameContainingIgnoreCase(String keyword);
    
    List<Item> findByDescriptionContainingIgnoreCase(String keyword);
    
    List<Item> findByBrandContainingIgnoreCase(String keyword);
    
    List<Item> findByCategoryContainingIgnoreCase(String keyword);
}
