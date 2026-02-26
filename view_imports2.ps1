# Ver líneas 28-40 para ver los imports finales
$content = Get-Content 'MAHAIG~1\src\ProduktuakLogika.java' -Encoding UTF8
$lines = $content -split "`n"

for ($i = 28; $i -lt 40; $i++) { 
    if ($i -lt $lines.Count) {
        Write-Host ("Line {0:D3}: {1}" -f $i, $lines[$i])
    }
}
