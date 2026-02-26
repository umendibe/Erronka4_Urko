# Ver las líneas 228-240 para ver el cierre del UPDATE
$content = Get-Content 'MAHAIG~1\src\ProduktuakLogika.java' -Encoding UTF8
$lines = $content -split "`n"

for ($i = 228; $i -lt 240; $i++) { 
    if ($i -lt $lines.Count) {
        Write-Host ("Line {0:D3}: {1}" -f $i, $lines[$i])
    }
}
