# Tutorial: Uvod do Mikrosluzeb v IntelliJ IDEA

Tento tutorial vas provede vytvorenim a spustenim jednoducheho systemu mikrosluzeb pomoci **Spring Boot** primo ve vasem vyvojovem prostredi **IntelliJ IDEA**. Naucite se zakladni principy mikrosluzeb, jejich komunikaci a ladeni v realnem vyvojovem prostredi.

## Co se naucite

*   Otevrit a spustit vicemodul Maven projekt v IntelliJ IDEA
*   Spustit a ladit dve mikrosluzby soucasne
*   Pochopit synchronni REST komunikaci mezi sluzbami
*   Testovat mikrosluzby pomoci HTTP pozadavku

## Struktura Projektu

Projekt obsahuje dva moduly reprezentujici mikrosluzby:

*   **product-service** - Spravuje informace o produktech (nazev, cena, skladova zasoba)
*   **order-service** - Zpracovava objednavky a komunikuje s Product Service

Existuji dve verze projektu:

*   **basic-version/** - Startovni bod s TODO komentari, ktere budete postupne doplnovat
*   **full-version/** - Kompletni funkcni reseni pro kontrolu

## Tutorial Krok za Krokem

### Krok 1: Import Projektu do IntelliJ

Po rozbaleni archivu postupujte nasledovne:

1. Spustte **IntelliJ IDEA**
2. Zvolte `File` → `Open...`
3. Najdete a vyberte slozku `microsluzby-master`
4. Kliknete `OK`

IntelliJ automaticky rozpozna Maven projekt a nacte oba moduly. V dolni casti obrazovky uvidite prubeh indexovani a stahovani Maven zavislosti. Pockejte, az se tento proces dokonci.

### Krok 2: Doplneni Kodu - Product Service

Nyni zacneme doplnovat chybejici casti kodu. Pracujte ve slozce `basic-version`.

#### Product.java (Entita)

Otevrete soubor `basic-version/product-service/src/main/java/com/example/product/Product.java` a doplnte **KOMPLETNI KOD**:

```java
package com.example.product;

import jakarta.persistence.*;

@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private Double price;
    private Integer stock;
    
    public Product() {
    }
    
    public Product(String name, Double price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
```

#### ProductRepository.java

Otevrete `ProductRepository.java` a **NAHRADTE OBSAH**:

```java
package com.example.product;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
```

#### ProductService.java

Doplnte implementaci metod v `ProductService.java` - **NAHRADTE METODY**:

```java
public List<Product> getAllProducts() {
    return productRepository.findAll();
}

public Optional<Product> getProductById(Long id) {
    return productRepository.findById(id);
}

public Product saveProduct(Product product) {
    return productRepository.save(product);
}

public boolean checkStock(Long productId, Integer quantity) {
    Optional<Product> productOpt = productRepository.findById(productId);
    if (productOpt.isPresent()) {
        Product product = productOpt.get();
        return product.getStock() >= quantity;
    }
    return false;
}
```

#### ProductController.java

Implementujte REST endpointy - **NAHRADTE METODY**:

```java
@GetMapping
public List<Product> getAllProducts() {
    return productService.getAllProducts();
}

@GetMapping("/{id}")
public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    return productService.getProductById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

@PostMapping
public Product createProduct(@RequestBody Product product) {
    return productService.saveProduct(product);
}

@GetMapping("/{id}/check-stock")
public ResponseEntity<Boolean> checkStock(@PathVariable Long id, @RequestParam Integer quantity) {
    boolean available = productService.checkStock(id, quantity);
    return ResponseEntity.ok(available);
}
```

### Krok 3: Doplneni Kodu - Order Service

Podobne doplnte kod pro Order Service.

#### Order.java (Entita)

Otevrete `basic-version/order-service/src/main/java/com/example/order/Order.java` a doplnte **KOMPLETNI KOD**:

```java
package com.example.order;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long productId;
    private Integer quantity;
    private String status;
    
    public Order() {
    }
    
    public Order(Long productId, Integer quantity, String status) {
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
```

#### OrderService.java

Zde implementujeme klicovou logiku - volani Product Service. **NAHRADTE METODU createOrder**:

```java
public Order createOrder(Long productId, Integer quantity) {
    String url = PRODUCT_SERVICE_URL + "/products/" + productId + "/check-stock?quantity=" + quantity;
    
    try {
        Boolean stockAvailable = restTemplate.getForObject(url, Boolean.class);
        
        Order order;
        if (Boolean.TRUE.equals(stockAvailable)) {
            order = new Order(productId, quantity, "CONFIRMED");
        } else {
            order = new Order(productId, quantity, "REJECTED");
        }
        
        return orderRepository.save(order);
        
    } catch (Exception e) {
        System.err.println("Chyba pri komunikaci s Product Service: " + e.getMessage());
        Order order = new Order(productId, quantity, "ERROR");
        return orderRepository.save(order);
    }
}
```

#### OrderController.java

Implementujte endpoint - **NAHRADTE METODU createOrder**:

```java
@PostMapping
public Order createOrder(@RequestBody OrderRequest request) {
    return orderService.createOrder(request.getProductId(), request.getQuantity());
}
```

### Krok 4: Spusteni Mikrosluzeb

Nyni spustime obe sluzby v IntelliJ IDEA.

#### Spusteni Product Service

1. V projektovem stromu najdete `basic-version` → `product-service` → `src` → `main` → `java` → `com.example.product` → `ProductServiceApplication.java`
2. Kliknete pravym tlacitkem na soubor
3. Zvolte **Run 'ProductServiceApplication.main()'**
4. V konzoli uvidite, ze sluzba nastartovala na portu **8081**

#### Spusteni Order Service

1. Stejnym zpusobem najdete a otevrete `OrderServiceApplication.java` v modulu `order-service` v **basic-version**
2. Kliknete pravym tlacitkem a zvolte **Run 'OrderServiceApplication.main()'**
3. IntelliJ se zepta, zda chcete spustit vice aplikaci soucasne - **povolte to**
4. Sluzba nastartuje na portu **8082**

V zalozce **Run** dole v IntelliJ nyni vidite obe bezici aplikace.

### Krok 5: Testovani Systemu

#### Pro Windows (PowerShell)

V korenove slozce projektu najdete soubor `TEST-PRIKAZY-WINDOWS.ps1`. Spustte ho v PowerShellu:

```powershell
.\TEST-PRIKAZY-WINDOWS.ps1
```

Nebo pouzijte tyto prikazy rucne:

```powershell
# Zobrazit produkty
Invoke-RestMethod -Uri "http://localhost:8081/products" -Method Get

# Vytvorit produkt
$body = @{name="Laptop"; price=25000.0; stock=10} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8081/products" -Method Post -Body $body -ContentType "application/json"

# Vytvorit objednavku
$body = @{productId=1; quantity=5} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8082/orders" -Method Post -Body $body -ContentType "application/json"

# Zobrazit objednavky
Invoke-RestMethod -Uri "http://localhost:8082/orders" -Method Get
```

#### Pro Linux/Mac (Bash)

V korenove slozce projektu najdete soubor `TEST-PRIKAZY-LINUX-MAC.sh`. Spustte ho:

```bash
./TEST-PRIKAZY-LINUX-MAC.sh
```

Nebo pouzijte tyto curl prikazy:

```bash
# Zobrazit produkty
curl http://localhost:8081/products

# Vytvorit produkt
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"Laptop","price":25000.0,"stock":10}' \
  http://localhost:8081/products

# Vytvorit objednavku
curl -X POST -H "Content-Type: application/json" \
  -d '{"productId":1,"quantity":5}' \
  http://localhost:8082/orders

# Zobrazit objednavky
curl http://localhost:8082/orders
```

### Krok 6: Zastaveni Sluzeb

Pro zastaveni sluzebjednoduse kliknete na cervene tlacitko **Stop** v zalozce **Run** pro kazdou sluzbu.

## Jak Funguje Komunikace Mezi Sluzbami

Order Service komunikuje s Product Service pomoci HTTP pozadavku. Kdyz vytvarite objednavku, Order Service:

1. Zavola endpoint `http://localhost:8081/products/{id}/check-stock?quantity={quantity}`
2. Product Service zkontroluje skladovou zasobu
3. Vrati `true` nebo `false`
4. Order Service na zaklade odpovedi vytvori objednavku se statusem `CONFIRMED` nebo `REJECTED`

Toto je priklad **synchronni komunikace** mezi mikrosluzebami pomoci REST API.

## Kompletni Reseni

Ve slozce `full-version` najdete kompletni a funkcni kod. Muzete jej pouzit pro kontrolu nebo jako referenci. V teto verzi je navic v `ProductServiceApplication.java` pridan `CommandLineRunner`, ktery pri startu automaticky vlozi do databaze tri testovaci produkty (Laptop, Mys, Klavesnice).

## Reseni Problemu

### Prazdny seznam produktu v basic-version

V basic-version musite produkty vytvorit rucne pomoci POST pozadavku. Full-version ma produkty vytvorene automaticky pri startu.

### Chyby v PowerShellu

Na Windows **nepouzivejte** CMD nebo Git Bash s curl prikazy - ty jsou pro Linux/Mac. Pouzijte bud PowerShell prikazy vyse, nebo spustte skript `TEST-PRIKAZY-WINDOWS.ps1`.

### Sluzby se nespusti soucasne

Kdyz spoustite druhou sluzbu, IntelliJ se zepta: "Do you want to run multiple instances?" Kliknete na **"Run"** (ne "Stop and Run").

## Zaver

Gratulujeme! Uspesne jste vytvorili a spustili system mikrosluzeb v IntelliJ IDEA. Naucili jste se, jak vytvorit dve Spring Boot aplikace, ktere spolu komunikuji pres REST API, a jak je spustit a ladit v realnem vyvojovem prostredi. Tento pristup je zakladem pro budovani slozitejsich distribuovanych systemu.

