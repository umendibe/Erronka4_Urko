# Ver los primeros 30 lines para ver imports
$content = Get-Content 'MAHAIG~1\src\ProduktuakLogika.java' -Encoding UTF8
$lines = $content -split "`n"

for ($i = 0; $i -lt 30; $i++) { 
    if ($i -lt $lines.Count) {
        Write-Host ("Line {0:D3}: {1}" -f $i, $lines[$i])
    }
}
