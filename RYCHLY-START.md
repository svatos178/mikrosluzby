# Rychlý Start v IntelliJ IDEA

Tento návod vám ukáže, jak co nejrychleji spustit projekt v IntelliJ IDEA.

## 1. Otevřete Projekt

1. Spusťte **IntelliJ IDEA**
2. Zvolte `File` → `Open...`
3. Vyberte složku `microservices-tutorial`
4. Klikněte `OK`

IntelliJ automaticky rozpozná Maven projekt a načte oba moduly.

## 2. Počkejte na Indexování

Po otevření projektu IntelliJ začne indexovat soubory a stahovat Maven závislosti. Počkejte, až se dokončí (vidíte v dolní liště).

## 3. Spusťte Služby

### Varianta A: Pomocí Předpřipravených Konfigurací

V pravém horním rohu najdete rozbalovací menu s konfiguracemi:

1. Vyberte **Product Service** a klikněte na zelenou šipku (Run)
2. Počkejte, až služba nastartuje (v konzoli uvidíte "Started ProductServiceApplication")
3. Vyberte **Order Service** a klikněte na Run
4. IntelliJ se zeptá, zda chcete spustit více aplikací → povolte to

### Varianta B: Manuálně

1. Najděte `product-service/src/main/java/com/example/product/ProductServiceApplication.java`
2. Klikněte pravým tlačítkem → `Run 'ProductServiceApplication.main()'`
3. Stejně spusťte `OrderServiceApplication.java`

## 4. Testování

### Windows (PowerShell)

Spusťte připravený skript:

```powershell
.\TEST-PRIKAZY-WINDOWS.ps1
```

Nebo ručně:

```powershell
# Zobrazit produkty
Invoke-RestMethod -Uri "http://localhost:8081/products" -Method Get

# Vytvořit objednávku
$body = @{productId=1; quantity=5} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8082/orders" -Method Post -Body $body -ContentType "application/json"
```

### Linux/Mac (Terminal)

Spusťte připravený skript:

```bash
./TEST-PRIKAZY-LINUX-MAC.sh
```

Nebo ručně:

```bash
# Zobrazit produkty
curl http://localhost:8081/products

# Vytvořit objednávku
curl -X POST -H "Content-Type: application/json" \
  -d '{"productId":1,"quantity":5}' \
  http://localhost:8082/orders
```

## 5. Zastavení

Klikněte na červené tlačítko **Stop** v záložce Run pro každou službu.

---

**Hotovo!** Mikroslužby běží a komunikují spolu.

