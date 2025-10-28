package com.productstore.service;

import com.productstore.model.Item;
import com.productstore.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepo repo;

    public List<Item> getAllItems() {
        return repo.findAll();
    }

    public Item getItemById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Item addItem(Item item, MultipartFile imageFile) throws IOException {
        item.setImageName(imageFile.getOriginalFilename());
        item.setImageType(imageFile.getContentType());
        item.setImageData(imageFile.getBytes());
        return repo.save(item);
    }

    public Item updateItem(String id, Item item, MultipartFile imageFile) throws IOException {
        item.setImageData(imageFile.getBytes());
        item.setImageName(imageFile.getOriginalFilename());
        item.setImageType(imageFile.getContentType());
        item.setId(id);
        return repo.save(item);
    }

    public void deleteItem(String id) {
        repo.deleteById(id);
    }

    public List<Item> searchItems(String keyword) {
        List<Item> results = repo.findByNameContainingIgnoreCase(keyword);
        results.addAll(repo.findByDescriptionContainingIgnoreCase(keyword));
        results.addAll(repo.findByBrandContainingIgnoreCase(keyword));
        results.addAll(repo.findByCategoryContainingIgnoreCase(keyword));
        return results.stream().distinct().toList();
    }
}
