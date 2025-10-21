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
        // Volání Product Service pro kontrolu skladové zásoby
        String url = PRODUCT_SERVICE_URL + "/products/" + productId + "/check-stock?quantity=" + quantity;
        
        try {
            Boolean stockAvailable = restTemplate.getForObject(url, Boolean.class);
            
            Order order;
            if (Boolean.TRUE.equals(stockAvailable)) {
                order = new Order(productId, quantity, "CONFIRMED");
                System.out.println("Objednávka potvrzena - dostatečná zásoba");
            } else {
                order = new Order(productId, quantity, "REJECTED");
                System.out.println("Objednávka zamítnuta - nedostatečná zásoba");
            }
            
            return orderRepository.save(order);
            
        } catch (Exception e) {
            System.err.println("Chyba při komunikaci s Product Service: " + e.getMessage());
            Order order = new Order(productId, quantity, "ERROR");
            return orderRepository.save(order);
        }
    }
}

