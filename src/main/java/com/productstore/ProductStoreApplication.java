package com.productstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.productstore.model.Item;
import com.productstore.repo.ItemRepo;

@SpringBootApplication
public class ProductStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductStoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializeData(ItemRepo itemRepo) {
		return args -> {
			if (itemRepo.count() == 0) {
				Item item1 = new Item();
				item1.setName("Laptop");
				item1.setDescription("High-performance laptop");
				item1.setPrice(999.99);
				
				Item item2 = new Item();
				item2.setName("Smartphone");
				item2.setDescription("Latest model smartphone");
				item2.setPrice(499.99);
				
				Item item3 = new Item();
				item3.setName("Headphones");
				item3.setDescription("Wireless noise-cancelling headphones");
				item3.setPrice(149.99);
				
				itemRepo.save(item1);
				itemRepo.save(item2);
				itemRepo.save(item3);
				
				System.out.println("Sample data initialized!");
			}
		};
	}
}

