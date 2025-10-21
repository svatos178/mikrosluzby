package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public List<Product> getAllProducts() {
        // TODO: Implementujte endpoint pro získání všech produktů
        // Hint: zavolejte productService.getAllProducts()
        return null;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        // TODO: Implementujte endpoint pro získání produktu podle ID
        // Hint: použijte productService.getProductById(id)
        // Pokud produkt existuje, vraťte ResponseEntity.ok(product)
        // Pokud ne, vraťte ResponseEntity.notFound().build()
        return null;
    }
    
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        // TODO: Implementujte endpoint pro vytvoření nového produktu
        // Hint: zavolejte productService.saveProduct(product)
        return null;
    }
    
    @GetMapping("/{id}/check-stock")
    public ResponseEntity<Boolean> checkStock(@PathVariable Long id, @RequestParam Integer quantity) {
        // TODO: Implementujte endpoint pro kontrolu skladové zásoby
        // Hint: zavolejte productService.checkStock(id, quantity)
        return null;
    }
}

