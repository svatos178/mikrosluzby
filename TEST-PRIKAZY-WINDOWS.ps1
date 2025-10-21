# Testovaci prikazy pro Windows PowerShell
# Spustte tyto prikazy v PowerShell (ne v CMD)

Write-Host "=== Test 1: Zobrazit vsechny produkty ===" -ForegroundColor Green
$products = Invoke-RestMethod -Uri "http://localhost:8081/products" -Method Get
$products | ConvertTo-Json -Depth 10
Write-Host ""

Write-Host "=== Test 2: Vytvorit novy produkt (pouze v basic-version) ===" -ForegroundColor Green
$productBody = @{
    name = "Monitor"
    price = 8000.0
    stock = 15
} | ConvertTo-Json

$newProduct = Invoke-RestMethod -Uri "http://localhost:8081/products" -Method Post -Body $productBody -ContentType "application/json"
$newProduct | ConvertTo-Json -Depth 10
Write-Host ""

Write-Host "=== Test 3: Vytvorit uspesnou objednavku (5 kusu) ===" -ForegroundColor Green
$orderBody1 = @{
    productId = 1
    quantity = 5
} | ConvertTo-Json

$order1 = Invoke-RestMethod -Uri "http://localhost:8082/orders" -Method Post -Body $orderBody1 -ContentType "application/json"
$order1 | ConvertTo-Json -Depth 10
Write-Host ""

Write-Host "=== Test 4: Vytvorit zamitnutou objednavku (15 kusu) ===" -ForegroundColor Green
$orderBody2 = @{
    productId = 1
    quantity = 15
} | ConvertTo-Json

$order2 = Invoke-RestMethod -Uri "http://localhost:8082/orders" -Method Post -Body $orderBody2 -ContentType "application/json"
$order2 | ConvertTo-Json -Depth 10
Write-Host ""

Write-Host "=== Test 5: Zobrazit vsechny objednavky ===" -ForegroundColor Green
$orders = Invoke-RestMethod -Uri "http://localhost:8082/orders" -Method Get
$orders | ConvertTo-Json -Depth 10
Write-Host ""

Write-Host "=== HOTOVO! ===" -ForegroundColor Cyan
Write-Host "Zkontrolujte vysledky vyse." -ForegroundColor Cyan

