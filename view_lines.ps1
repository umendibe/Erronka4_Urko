# Ver las líneas 480-510 del archivo
$content = Get-Content 'MAHAIG~1\src\ProduktuakLogika.java' -Encoding UTF8 -Raw
$lines = $content -split "`n"

for ($i = 480; $i -lt 510; $i++) { 
    if ($i -lt $lines.Count) {
        Write-Host "Line $i`: $($lines[$i])"
    }
}
