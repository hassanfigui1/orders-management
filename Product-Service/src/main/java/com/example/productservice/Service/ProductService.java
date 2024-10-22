package com.example.productservice.Service;

import com.example.productservice.Models.Product;
import com.example.productservice.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getProducts() {
        return this.repo.findAll();
    }
    public Product getProductById(int id) {
        return this.repo.findById(id).orElse(null);
    }
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return this.repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException{
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return this.repo.save(product);
    }

    public void deleteProduct(int productId){
        this.repo.deleteById(productId);
    }

    public List<Product> searchProducts(String keyword){
        return this.repo.searchProducts(keyword);
    }


}
