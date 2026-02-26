# Añadir imports faltantes
$javaFilePath = "MAHAIG~1\src\ProduktuakLogika.java"

Write-Host "Añadiendo imports faltantes..."

# Read the file 
$content = Get-Content $javaFilePath -Encoding UTF8 -Raw

# Add missing imports after GsonBuilder
$newContent = $content -replace (
    "import com.google.gson.GsonBuilder;",
    "import com.google.gson.GsonBuilder;`nimport com.google.gson.JsonObject;`nimport com.google.gson.JsonArray;`nimport java.io.FileWriter;"
)

# Write back
Set-Content -Path $javaFilePath -Value $newContent -Encoding UTF8

Write-Host "Imports gehituta!"
