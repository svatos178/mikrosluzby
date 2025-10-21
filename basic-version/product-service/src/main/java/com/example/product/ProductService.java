package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        // TODO: Implementujte metodu pro získání všech produktů
        // Hint: použijte productRepository.findAll()
        return null;
    }
    
    public Optional<Product> getProductById(Long id) {
        // TODO: Implementujte metodu pro získání produktu podle ID
        // Hint: použijte productRepository.findById(id)
        return null;
    }
    
    public Product saveProduct(Product product) {
        // TODO: Implementujte metodu pro uložení produktu
        // Hint: použijte productRepository.save(product)
        return null;
    }
    
    public boolean checkStock(Long productId, Integer quantity) {
        // TODO: Implementujte kontrolu skladové zásoby
        // 1. Najděte produkt podle ID
        // 2. Zkontrolujte, zda má dostatečnou zásobu (stock >= quantity)
        // 3. Vraťte true/false
        return false;
    }
}

