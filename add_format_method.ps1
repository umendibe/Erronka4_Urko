# Añadir el método helper formatuEuroa() antes del cierre de la clase
$javaFilePath = "MAHAIG~1\src\ProduktuakLogika.java"

Write-Host "Añadiendo método formatuEuroa()..."

# Read the file
$content = Get-Content $javaFilePath -Encoding UTF8
$lines = $content -split "`n"

# Find the last closing brace (end of class)
$lastLine = $lines.Count - 1

# Create the helper method
$helperMethod = @"
    
    /**
     * Euroan formatuatutako balioa bueltatzea
     * @param baluoa baluoa
     * @return formatuatutako euroa (XX.XXX,XX€)
     */
    private static String formatuEuroa(double balioa) {
        // Euroa formatu lokalizatuarekin
        java.text.DecimalFormat df = new java.text.DecimalFormat("###,##0.00");
        String formatuatua = df.format(balioa);
        // Ordeztu puntua pista eta alderantziz
        formatuatua = formatuatua.replace(",", "|").replace(".", ",").replace("|", ".");
        return formatuatua + "€";
    }
"@

# Insert before the closing brace
$newLines = @()
for ($i = 0; $i -lt $lastLine; $i++) {
    $newLines += $lines[$i]
}
$newLines += $helperMethod
$newLines += $lines[$lastLine]  # Add the closing brace

# Join and write back
$newContent = $newLines -join "`n"
Set-Content -Path $javaFilePath -Value $newContent -Encoding UTF8

Write-Host "Metodo formatuEuroa() gehitua!"
