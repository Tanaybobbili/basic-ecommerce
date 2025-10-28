package com.productstore.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.productstore.model.Item;
import com.productstore.service.ItemService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	private final Path uploadDir = Paths.get("uploads");
	
	@GetMapping("/items")
	public List<Item> getAllItems() {
		return itemService.getAllItems();
	}
	
	@GetMapping("/item/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable String id) {
		Optional<Item> item = itemService.getItemById(id);
		return item.map(ResponseEntity::ok)
				   .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping(value = "/item", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Item> addItem(@RequestParam("name") String name,
									   @RequestParam("description") String description,
									   @RequestParam("price") double price,
									   @RequestParam(value = "image", required = false) MultipartFile image) {
		Item item = new Item();
		item.setName(name);
		item.setDescription(description);
		item.setPrice(price);

		Item saved = itemService.addItem(item);

		if (image != null && !image.isEmpty()) {
			try {
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}
				String originalFilename = StringUtils.cleanPath(image.getOriginalFilename() == null ? "image" : image.getOriginalFilename());
				String ext = originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf('.')) : "";
				String filename = saved.getId() + "_" + UUID.randomUUID() + ext;
				Path destination = uploadDir.resolve(filename);
				Files.copy(image.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
				saved.setImagePath(filename);
				saved = itemService.addItem(saved);
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}

		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}
	
	@PutMapping("/item/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody Item item) {
		Item updatedItem = itemService.updateItem(id, item);
		if (updatedItem != null) {
			return ResponseEntity.ok(updatedItem);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/item/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable String id) {
		// delete image file if present
		itemService.getItemById(id).ifPresent(item -> {
			if (item.getImagePath() != null) {
				Path imagePath = uploadDir.resolve(item.getImagePath());
				try {
					Files.deleteIfExists(imagePath);
				} catch (IOException ignored) {}
			}
		});
		boolean deleted = itemService.deleteItem(id);
		if (deleted) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/items/search")
	public List<Item> searchItems(@RequestParam String keyword) {
		return itemService.searchItems(keyword);
	}

	@GetMapping("/item/{id}/image")
	public ResponseEntity<Resource> getItemImage(@PathVariable String id) {
		Optional<Item> item = itemService.getItemById(id);
		if (item.isEmpty() || item.get().getImagePath() == null) {
			return ResponseEntity.notFound().build();
		}
		try {
			Path imagePath = uploadDir.resolve(item.get().getImagePath());
			Resource resource = new UrlResource(imagePath.toUri());
			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}
			String contentType = Files.probeContentType(imagePath);
			if (contentType == null) {
				contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
			}
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
					.contentType(MediaType.parseMediaType(contentType))
					.body(resource);
		} catch (MalformedURLException e) {
			return ResponseEntity.notFound().build();
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}

