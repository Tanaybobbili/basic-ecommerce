package com.productstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    private String id;
    
    private String name;
    
    private String description;
    
    private String brand;
    
    private BigDecimal price;
    
    private String category;
    
    private Date releaseDate;
    
    private boolean itemAvailable;
    
    private int stockQuantity;
    
    private String imageName;
    
    private String imageType;
    
    private byte[] imageData;

}
