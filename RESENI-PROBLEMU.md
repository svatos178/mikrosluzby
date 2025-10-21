# Řešení Problémů

Tento dokument obsahuje řešení nejčastějších problémů, které mohou nastat při práci s tutoriálem.

## Problém 1: Prázdný seznam produktů ve full-version

**Symptom:** Když zavoláte `http://localhost:8081/products`, vrátí se prázdný seznam `[]` nebo `{}`.

**Řešení:**
1. Zkontrolujte konzoli Product Service v IntelliJ (záložka Run)
2. Měli byste vidět zprávu: `Testovací data byla načtena do Product Service`
3. Pokud tuto zprávu nevidíte:
   - Zastavte Product Service (červené tlačítko Stop)
   - Spusťte ho znovu
   - Počkejte, až se plně nastartuje

**Příčina:** CommandLineRunner se někdy nespustí při prvním startu.

## Problém 2: Chyby v PowerShellu na Windows

**Symptom:** Při kopírování curl příkazů z README dostáváte chyby typu:
```
The term 'http://localhost:8081/products' is not recognized...
Cannot bind parameter 'Headers'...
```

**Řešení:**
PowerShell na Windows má vlastní `curl` (alias pro `Invoke-WebRequest`), který nefunguje se syntaxí pro Linux/Mac.

**Použijte místo toho:**
1. Připravený skript: `.\TEST-PRIKAZY-WINDOWS.ps1`
2. Nebo PowerShell příkazy z README (sekce "Pro Windows")

**Příklad správného PowerShell příkazu:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8081/products" -Method Get
```

## Problém 3: Port 8081 nebo 8082 je již používán

**Symptom:** Při spuštění služby vidíte chybu:
```
Port 8081 is already in use
```

**Řešení:**
1. **Zjistěte, co používá port:**
   - Windows: `netstat -ano | findstr :8081`
   - Linux/Mac: `lsof -i :8081`

2. **Zastavte proces:**
   - Windows: `taskkill /PID <číslo_procesu> /F`
   - Linux/Mac: `kill -9 <číslo_procesu>`

3. **Nebo změňte port:**
   - Otevřete `application.properties` ve službě
   - Změňte `server.port=8081` na jiný port (např. `8091`)
   - Nezapomeňte aktualizovat URL v `OrderService.java`!

## Problém 4: Order Service nemůže komunikovat s Product Service

**Symptom:** Při vytváření objednávky dostanete status `ERROR` nebo chybu v konzoli:
```
Chyba při komunikaci s Product Service: Connection refused
```

**Řešení:**
1. **Zkontrolujte, že Product Service běží:**
   - V záložce Run v IntelliJ byste měli vidět běžící Product Service
   - Otevřete `http://localhost:8081/products` v prohlížeči - měli byste vidět produkty

2. **Zkontrolujte URL v OrderService.java:**
   - Otevřete `order-service/src/main/java/com/example/order/OrderService.java`
   - Ověřte, že `PRODUCT_SERVICE_URL = "http://localhost:8081"`
   - **NE** `http://product-service:8081` (to je pro Docker!)

3. **Restartujte Order Service** po změně URL

## Problém 5: Maven závislosti se nestahují

**Symptom:** IntelliJ hlásí chyby typu "Cannot resolve symbol" nebo "Package does not exist".

**Řešení:**
1. Klikněte pravým tlačítkem na `pom.xml`
2. Zvolte `Maven` → `Reload Project`
3. Počkejte, až se závislosti stáhnou (vidíte v dolní liště)
4. Pokud to nepomůže, zkuste `File` → `Invalidate Caches...` → `Invalidate and Restart`

## Problém 6: Java 17 není nalezena

**Symptom:** IntelliJ hlásí, že nemůže najít JDK nebo že verze není kompatibilní.

**Řešení:**
1. Stáhněte a nainstalujte [Eclipse Temurin JDK 17](https://adoptium.net/temurin/releases/?version=17)
2. V IntelliJ: `File` → `Project Structure` → `Project`
3. Nastavte `SDK` na Java 17
4. Nastavte `Language level` na `17 - Sealed types, always-strict floating-point semantics`

## Problém 7: Služby se nespustí současně

**Symptom:** Když spustíte druhou službu, první se zastaví.

**Řešení:**
1. Když spouštíte druhou službu, IntelliJ se zeptá: "Do you want to run multiple instances?"
2. Klikněte na **"Run"** (ne "Stop and Run")
3. Pokud se dialog nezobrazí:
   - Jděte do `Run` → `Edit Configurations...`
   - Zaškrtněte `Allow multiple instances` pro obě konfigurace

## Stále máte problém?

Zkontrolujte:
1. Že používáte **full-version** pro testování (ne basic-version)
2. Že obě služby běží (vidíte je v záložce Run)
3. Že v konzoli nejsou červené chybové zprávy
4. Že používáte správné příkazy pro váš operační systém (Windows = PowerShell, Linux/Mac = bash)

