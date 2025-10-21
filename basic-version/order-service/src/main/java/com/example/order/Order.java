package com.example.order;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: Přidejte atribut 'productId' typu Long
    
    // TODO: Přidejte atribut 'quantity' typu Integer
    
    // TODO: Přidejte atribut 'status' typu String (např. "PENDING", "CONFIRMED", "REJECTED")
    
    // Konstruktory
    public Order() {
    }
    
    public Order(Long productId, Integer quantity, String status) {
        // TODO: Inicializujte atributy
    }
    
    // TODO: Vygenerujte gettery a settery pro všechny atributy
}

