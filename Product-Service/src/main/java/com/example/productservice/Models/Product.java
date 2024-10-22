package com.example.productservice.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodId;
    private String prodName;
    private BigDecimal price;
    private String brand;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date releaseDate;
    private String description;
    private boolean availability;
    private int quantity;
    private String category;

    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
}
