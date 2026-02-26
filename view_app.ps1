# Ver el archivo App.java lineas 1-50
$content = Get-Content 'MAHAIG~1\src\App.java' -Encoding UTF8
$lines = $content -split "`n"

for ($i = 0; $i -lt 50; $i++) { 
    if ($i -lt $lines.Count) {
        Write-Host ("Line {0:D3}: {1}" -f $i, $lines[$i])
    }
}
