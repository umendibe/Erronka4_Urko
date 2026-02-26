# Buscar el método produktuakEguneratu para ver el error
$content = Get-Content 'MAHAIG~1\src\ProduktuakLogika.java' -Encoding UTF8
$lines = $content -split "`n"

# Buscar la línea donde comienza produktuakEguneratu
for ($i = 0; $i -lt $lines.Count; $i++) {
    if ($lines[$i] -like "*produktuakEguneratu*" -and $lines[$i] -like "*public*") {
        Write-Host "Encontrado en línea $i"
        # Mostrar desde esa línea en adelante
        for ($j = $i; $j -lt [Math]::Min($i + 80, $lines.Count); $j++) {
            Write-Host ("Line {0:D3}: {1}" -f $j, $lines[$j])
        }
        break
    }
}
