package com.example.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // TODO: Přidejte metodu pro vyhledání produktu podle jména
    // Hint: Spring Data JPA automaticky implementuje metody podle jejich názvu
    // Příklad: findByName(String name)
}

