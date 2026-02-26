# Ver líneas 595-605 para ver el error de sintaxis
$content = Get-Content 'MAHAIG~1\src\ProduktuakLogika.java' -Encoding UTF8
$lines = $content -split "`n"

for ($i = 595; $i -lt 605; $i++) { 
    if ($i -lt $lines.Count) {
        Write-Host ("Line {0:D3}: {1}" -f $i, $lines[$i])
    }
}
