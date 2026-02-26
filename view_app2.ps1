# Ver el archivo App.java lineas 60-100 para ver el case 7
$content = Get-Content 'MAHAIG~1\src\App.java' -Encoding UTF8
$lines = $content -split "`n"

for ($i = 60; $i -lt 100; $i++) { 
    if ($i -lt $lines.Count) {
        Write-Host ("Line {0:D3}: {1}" -f $i, $lines[$i])
    }
}
