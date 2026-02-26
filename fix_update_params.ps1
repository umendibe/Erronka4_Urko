# Corregir el orden de parámetros en el UPDATE
$javaFilePath = "MAHAIG~1\src\ProduktuakLogika.java"

Write-Host "Corrigiendo orden de parámetros en UPDATE..."

# Read the file 
$content = Get-Content $javaFilePath -Encoding UTF8 -Raw

# Replace the incorrect parameter order
$oldPattern = 'pst\.setString\(5, kategoria\);[\s\n]+pst\.setInt\(6, ID\);[\s\n]+pst\.setString\(7, sorkuntzaData\);[\s\n]+pst\.setString\(8, irudiaURL\);'

$newCode = @"
pst.setString(5, kategoria);
                pst.setString(6, sorkuntzaData);
                pst.setString(7, irudiaURL);
                pst.setInt(8, ID);
"@

$newContent = $content -replace $oldPattern, $newCode

# Write back
Set-Content -Path $javaFilePath -Value $newContent -Encoding UTF8

Write-Host "Parámetros corregidos!"
