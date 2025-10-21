package com.example.product;

import jakarta.persistence.*;

@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: Přidejte atribut 'name' typu String
    
    // TODO: Přidejte atribut 'price' typu Double
    
    // TODO: Přidejte atribut 'stock' typu Integer (skladová zásoba)
    
    // Konstruktory
    public Product() {
    }
    
    public Product(String name, Double price, Integer stock) {
        // TODO: Inicializujte atributy
    }
    
    // TODO: Vygenerujte gettery a settery pro všechny atributy
    // Hint: V IntelliJ IDEA použijte Alt+Insert -> Getter and Setter
}

