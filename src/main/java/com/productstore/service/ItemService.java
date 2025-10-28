package com.productstore.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productstore.model.Item;
import com.productstore.repo.ItemRepo;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepo itemRepo;
	
	public List<Item> getAllItems() {
		return itemRepo.findAll();
	}
	
	public Optional<Item> getItemById(String id) {
		return itemRepo.findById(id);
	}
	
	public Item addItem(Item item) {
		return itemRepo.save(item);
	}
	
	public Item updateItem(String id, Item updatedItem) {
		if (itemRepo.existsById(id)) {
			updatedItem.setId(id);
			return itemRepo.save(updatedItem);
		}
		return null;
	}
	
	public boolean deleteItem(String id) {
		if (itemRepo.existsById(id)) {
			itemRepo.deleteById(id);
			return true;
		}
		return false;
	}
	
	public List<Item> searchItems(String keyword) {
		List<Item> byName = itemRepo.findByNameContainingIgnoreCase(keyword);
		List<Item> byDescription = itemRepo.findByDescriptionContainingIgnoreCase(keyword);
		
		byName.addAll(byDescription);
		return byName;
	}
}

