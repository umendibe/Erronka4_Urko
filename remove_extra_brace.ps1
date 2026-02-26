# Eliminar la llave extra en la línea 601
$javaFilePath = "MAHAIG~1\src\ProduktuakLogika.java"

Write-Host "Eliminando llave extra..."

# Read the file 
$content = Get-Content $javaFilePath -Encoding UTF8 

# Remove the extra closing brace at line 601
# Line 600 is the correct closing brace for the method
# Line 601 is extra
$lines = $content -split "`n"

# Remove line 601 (index 600)
$newLines = @()
for ($i = 0; $i -lt $lines.Count; $i++) {
    if ($i -ne 601) {  # Skip line 601 (0-indexed: 600)
        $newLines += $lines[$i]
    }
}

# Write back
$newContent = $newLines -join "`n"
Set-Content -Path $javaFilePath -Value $newContent -Encoding UTF8

Write-Host "Llave extra eliminada!"
