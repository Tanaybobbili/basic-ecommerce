package com.productstore.controller;

import com.productstore.model.Item;
import com.productstore.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService service;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems(){
        return new ResponseEntity<>(service.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable String id){
        Item item = service.getItemById(id);

        if(item != null)
            return new ResponseEntity<>(item, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/item")
    public ResponseEntity<?> addItem(@RequestPart Item item,
                                        @RequestPart MultipartFile imageFile){
        try {
            Item savedItem = service.addItem(item, imageFile);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/item/{itemId}/image")
    public ResponseEntity<byte[]> getImageByItemId(@PathVariable String itemId){
        Item item = service.getItemById(itemId);
        byte[] imageFile = item.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(item.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<String> updateItem(@PathVariable String id, @RequestPart Item item,
                                                @RequestPart MultipartFile imageFile){
        Item updatedItem = null;
        try {
            updatedItem = service.updateItem(id, item, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
        if(updatedItem != null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable String id){
        Item item = service.getItemById(id);
        if(item != null) {
            service.deleteItem(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/items/search")
    public ResponseEntity<List<Item>> searchItems(@RequestParam String keyword){
        List<Item> items = service.searchItems(keyword);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
