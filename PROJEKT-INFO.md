# Informace o Projektu: Tutoriál Mikroslužeb pro IntelliJ IDEA

## Přehled

Tento projekt je kompletní tutoriál pro výuku základů mikroslužeb pomocí Spring Boot, navržený primárně pro vývojové prostředí **IntelliJ IDEA**. Cílem je ukázat, jak snadno lze systém mikroslužeb spustit a ladit přímo z IDE, s Dockerem jako volitelnou alternativou.

## Struktura Projektu

Projekt je koncipován jako vícemodulový Maven projekt, který lze snadno importovat do IntelliJ.

```
microservices-tutorial/
├── README.md                          # Hlavní průvodce tutoriálem (IntelliJ-first)
├── PROJEKT-INFO.md                    # Tento soubor
├── pom.xml                            # Hlavní Maven POM pro agregaci modulů
├── basic-version/                     # Startovní verze pro studenty
│   ├── .idea/runConfigurations/      # Předpřipravené konfigurace pro spuštění v IntelliJ
│   │   ├── Order_Service.xml
│   │   └── Product_Service.xml
│   ├── product-service/              # Modul pro Product Service
│   └── order-service/                # Modul pro Order Service
└── full-version/                      # Kompletní řešení
    ├── .idea/runConfigurations/      # Konfigurace pro IntelliJ
    ├── product-service/
    └── order-service/
```

## Klíčové Koncepty

Tutoriál demonstruje následující koncepty:

1.  **Vývoj v IntelliJ IDEA:**
    *   Snadný import a správa vícemodulového Maven projektu.
    *   Současné spuštění více mikroslužeb pomocí připravených `Run Configurations`.
    *   Pohodlné ladění a prohlížení logů přímo v IDE.

2.  **Architektura Mikroslužeb:**
    *   Dvě nezávislé služby (`product-service`, `order-service`).
    *   Každá služba má vlastní in-memory databázi (H2).

3.  **Synchronní Komunikace:**
    *   REST API komunikace mezi službami (`RestTemplate`).
    *   `Order Service` volá `Product Service` přes `http://localhost`.

4.  **Kontejnerizace (Volitelně):**
    *   Projekt stále obsahuje `Dockerfile` a `docker-compose.yml` pro ty, kteří chtějí experimentovat s Dockerem.

## Jak Použít

### Primární Metoda: IntelliJ IDEA

1.  **Otevřete projekt:** V IntelliJ zvolte `File -> Open` a vyberte kořenovou složku `microservices-tutorial`.
2.  **Doplňte kód:** Pokud pracujete s `basic-version`, doplňte kód podle `TODO` komentářů v `README.md`.
3.  **Spusťte služby:** V pravém horním rohu IntelliJ najdete rozbalovací menu s konfiguracemi. Postupně spusťte `Product Service` a `Order Service`. Obě služby poběží současně.
4.  **Testujte:** Použijte `curl` nebo vestavěný HTTP klient v IntelliJ k otestování endpointů na `localhost:8081` a `localhost:8082`.

### Alternativní Metoda: Docker

1.  **Upravte kód:** V `OrderService.java` změňte `localhost` na `product-service` v `PRODUCT_SERVICE_URL`.
2.  **Spusťte Docker Compose:** V terminálu ve složce `basic-version` nebo `full-version` spusťte `docker-compose up --build`.

## Cíl Změn

Cílem této refaktorizace bylo **snížit vstupní bariéru** pro vývojáře, kteří se chtějí naučit základy mikroslužeb. Tím, že se primárně používá známé prostředí jako IntelliJ IDEA, se mohou soustředit na logiku a komunikaci služeb, aniž by museli řešit nastavení Dockeru. Docker se tak stává volitelným krokem pro nasazení, nikoliv nutností pro samotný vývoj a učení.

