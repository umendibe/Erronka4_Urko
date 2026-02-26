# Ver más líneas para encontrar el final del método
$content = Get-Content 'MAHAIG~1\src\ProduktuakLogika.java' -Encoding UTF8 -Raw
$lines = $content -split "`n"

for ($i = 505; $i -lt 535; $i++) { 
    if ($i -lt $lines.Count) {
        Write-Host ("Line {0:D3}: {1}" -f $i, $lines[$i])
    }
}
