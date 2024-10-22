package com.example.productservice.Controller;

import com.example.productservice.Models.Product;
import com.example.productservice.Service.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping("/products")
    @CrossOrigin(origins = "http://localhost:3000")
    private ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(this.productService.getProducts(), HttpStatus.OK);
    }

    @RequestMapping(value="products/{prodId}",method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Product> getProductById(@PathVariable int prodId){
        Product product = this.productService.getProductById(prodId);
        if(product == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(this.productService.getProductById(prodId), HttpStatus.OK);
    }
    @PostMapping("/products")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> addProduct(
            @RequestPart Product product,
            @RequestPart MultipartFile imageFile) {
        try{
            Product product1 = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/product/{productId}/image")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<byte[]> getImage(@PathVariable int productId){
        Product product = productService.getProductById(productId);
        byte[] imageFile= product.getImageData();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }



    @PutMapping("/products/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateProduct(@PathVariable int id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile )  {
        try{
            Product productUpdated = productService.updateProduct(id, product, imageFile);
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{productId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId){
        try{
            this.productService.deleteProduct(productId);
            return new ResponseEntity<>("Product was deleted successfully", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/search")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        List<Product> products = this.productService.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }



}
