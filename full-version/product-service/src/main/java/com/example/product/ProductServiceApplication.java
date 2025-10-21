package com.example.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner loadData(ProductRepository repository) {
        return args -> {
            repository.save(new Product("Laptop", 25000.0, 10));
            repository.save(new Product("Mys", 500.0, 50));
            repository.save(new Product("Klavesnice", 1200.0, 30));
            System.out.println("Testovaci data byla nactena do Product Service");
        };
    }
}

