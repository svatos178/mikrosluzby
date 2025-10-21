package com.example.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    // Pro lokální spuštění v IntelliJ používáme localhost
    // Pro Docker změňte na: http://product-service:8081
    private static final String PRODUCT_SERVICE_URL = "http://localhost:8081";
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Order createOrder(Long productId, Integer quantity) {
        // TODO: Implementujte vytvoření objednávky s kontrolou skladové zásoby
        // Krok 1: Zavolejte Product Service pro kontrolu zásoby
        // URL: PRODUCT_SERVICE_URL + "/products/" + productId + "/check-stock?quantity=" + quantity
        // Hint: použijte restTemplate.getForObject(url, Boolean.class)
        
        // Krok 2: Pokud je zásoba dostačující (true), vytvořte objednávku se statusem "CONFIRMED"
        // Pokud ne, vytvořte objednávku se statusem "REJECTED"
        
        // Krok 3: Uložte a vraťte objednávku
        // Hint: použijte orderRepository.save(order)
        
        return null;
    }
}

