# Modificar el archivo MAHAIG~1\src\ProduktuakLogika.java
$javaFilePath = "MAHAIG~1\src\ProduktuakLogika.java"

Write-Host "Irakurri: $javaFilePath"

try {
    # Read the file
    $content = Get-Content $javaFilePath -Encoding UTF8 -Raw
    
    Write-Host "Fitxategia irakurrita: $($content.Length) karaktere"
    
    if ($content -contains "esportatuEstatistikak") {
        Write-Host "Metodoa aurkituta"
    } else {
        Write-Host "Metodoa ez dago aurkituta baina saiatu egingo dugu..."
    }
} catch {
    Write-Host "Errorea: $_"
}
