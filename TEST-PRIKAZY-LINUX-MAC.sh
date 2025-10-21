#!/bin/bash

# Testovaci prikazy pro Linux/Mac
# Spustte: chmod +x TEST-PRIKAZY-LINUX-MAC.sh && ./TEST-PRIKAZY-LINUX-MAC.sh

echo "=== Test 1: Zobrazit vsechny produkty ==="
curl http://localhost:8081/products
echo -e "\n"

echo "=== Test 2: Vytvorit novy produkt (pouze v basic-version) ==="
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"Monitor","price":8000.0,"stock":15}' \
  http://localhost:8081/products
echo -e "\n"

echo "=== Test 3: Vytvorit uspesnou objednavku (5 kusu) ==="
curl -X POST -H "Content-Type: application/json" \
  -d '{"productId":1,"quantity":5}' \
  http://localhost:8082/orders
echo -e "\n"

echo "=== Test 4: Vytvorit zamitnutou objednavku (15 kusu) ==="
curl -X POST -H "Content-Type: application/json" \
  -d '{"productId":1,"quantity":15}' \
  http://localhost:8082/orders
echo -e "\n"

echo "=== Test 5: Zobrazit vsechny objednavky ==="
curl http://localhost:8082/orders
echo -e "\n"

echo "=== HOTOVO! ==="

