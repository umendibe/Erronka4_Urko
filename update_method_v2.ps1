# Actualizar el método esportatuEstatistikak en ProduktuakLogika.java
$javaFilePath = "MAHAIG~1\src\ProduktuakLogika.java"

Write-Host "Leyendo archivo: $javaFilePath"

# Read the file
$content = Get-Content $javaFilePath -Encoding UTF8
$newContent = @()
$i = 0
$inMethod = $false
$methodReplaced = $false

# Process line by line
foreach ($line in $content) {
    # Check if this is the start of the method we want to replace
    if ($line -match "public static void esportatuEstatistikak\(\) throws IOException") {
        Write-Host "Found method at line $i"
        $inMethod = $true
        
        # Add the new method
        $newContent += @"
    public static void esportatuEstatistikak() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Sartu fitxategia gordetzeko ruta (hutsik web-rako):");
        String rutaDirektorioa = br.readLine().trim();
        if (rutaDirektorioa.isEmpty()) {
            rutaDirektorioa = "../../WEB_ORRIA/htdocs/js";
        }

        try {
            con = DriverManager.getConnection(DBurl, user, password);

            JsonObject estadistikak = new JsonObject();
            Statement st = con.createStatement();

            // Irabaziak totala
            ResultSet rs1 = st.executeQuery("SELECT COALESCE(SUM(prezio_totala), 0) as irabaziak_totala FROM Eskariak");
            if (rs1.next()) {
                double irabaziak = rs1.getDouble("irabaziak_totala");
                estadistikak.addProperty("irabaziak_totala", formatuEuroa(irabaziak));
            }

            // Stock baxua (< 5)
            ResultSet rs2 = st.executeQuery("SELECT id, izena, stock FROM Produktuak WHERE stock < 5");
            JsonArray stock_baxua = new JsonArray();
            while (rs2.next()) {
                JsonObject item = new JsonObject();
                item.addProperty("izena", rs2.getString("izena"));
                item.addProperty("kantitatea", rs2.getInt("stock"));
                stock_baxua.add(item);
            }
            estadistikak.add("stock_baxua", stock_baxua);

            // Gehien saldutakoak (top 3)
            ResultSet rs3 = st.executeQuery(
                "SELECT p.id, p.izena, COUNT(e.id) as salmentak FROM Produktuak p " +
                "LEFT JOIN Eskariak e ON p.id = e.produktu_id " +
                "GROUP BY p.id ORDER BY salmentak DESC LIMIT 3");
            JsonArray gehien_saldutakoak = new JsonArray();
            while (rs3.next()) {
                JsonObject item = new JsonObject();
                item.addProperty("produktua", rs3.getString("izena"));
                item.addProperty("salmentak", rs3.getInt("salmentak"));
                gehien_saldutakoak.add(item);
            }
            estadistikak.add("gehien_saldutakoak", gehien_saldutakoak);

            // Bezero onenak (top 3)
            ResultSet rs4 = st.executeQuery(
                "SELECT bezeroa, COUNT(*) as eskaerak FROM Eskariak GROUP BY bezeroa ORDER BY eskaerak DESC LIMIT 3");
            JsonArray bezero_onenak = new JsonArray();
            while (rs4.next()) {
                JsonObject item = new JsonObject();
                item.addProperty("bezeroa", rs4.getString("bezeroa"));
                item.addProperty("eskaerak", rs4.getInt("eskaerak"));
                bezero_onenak.add(item);
            }
            estadistikak.add("bezero_onenak", bezero_onenak);

            // Hileko irabaziak
            ResultSet rs5 = st.executeQuery(
                "SELECT YEAR(data) as urtea, MONTH(data) as hila, SUM(prezio_totala) as irabazia FROM Eskariak " +
                "GROUP BY YEAR(data), MONTH(data) ORDER BY urtea, hila");
            JsonArray hileko_irabaziak = new JsonArray();
            String[] hilabeak = {"", "Urtarrila", "Otsaila", "Martxoa", "Apirila", "Maiatza", "Ekaina",
                               "Uztaila", "Abuztua", "Iraila", "Urria", "Azaroa", "Abenduak"};
            while (rs5.next()) {
                JsonObject item = new JsonObject();
                int hila = rs5.getInt("hila");
                item.addProperty("urtea", rs5.getInt("urtea"));
                item.addProperty("hila", hilabeak[hila]);
                item.addProperty("irabazia", formatuEuroa(rs5.getDouble("irabazia")));
                hileko_irabaziak.add(item);
            }
            estadistikak.add("hileko_irabaziak", hileko_irabaziak);

            // Inoiz erosi gabeak
            ResultSet rs6 = st.executeQuery(
                "SELECT izena FROM Produktuak WHERE id NOT IN (SELECT DISTINCT produktu_id FROM Eskariak)");
            JsonArray inoiz_erosi_gabeak = new JsonArray();
            while (rs6.next()) {
                inoiz_erosi_gabeak.add(rs6.getString("izena"));
            }
            estadistikak.add("inoiz_erosi_gabeak", inoiz_erosi_gabeak);

            // Balio handikoak (> 500 EUR)
            ResultSet rs7 = st.executeQuery(
                "SELECT p.izena, SUM(COALESCE(e.prezio_totala, 0)) as balioa FROM Produktuak p " +
                "LEFT JOIN Eskariak e ON p.id = e.produktu_id " +
                "GROUP BY p.id HAVING balioa > 500 ORDER BY balioa DESC");
            JsonArray balio_handikoak = new JsonArray();
            while (rs7.next()) {
                JsonObject item = new JsonObject();
                item.addProperty("izena", rs7.getString("izena"));
                item.addProperty("balioa", formatuEuroa(rs7.getDouble("balioa")));
                balio_handikoak.add(item);
            }
            estadistikak.add("balio_handikoak", balio_handikoak);

            // Fitxero idatzi
            FileWriter fw = new FileWriter(rutaDirektorioa + "/estadistikak.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fw.write(gson.toJson(estadistikak));
            fw.close();

            System.out.println("Estatistikak ongi esportatu dira");
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
"@
        
        # Skip lines until we find the closing brace of the method
        continue
    }
    
    # If we're in the method to be replaced, skip lines until the closing brace
    if ($inMethod) {
        if ($line -match "^\s*\}\s*$" -and $methodReplaced -eq $false) {
            $inMethod = $false
            $methodReplaced = $true
            continue
        }
        # Skip this line as we're replacing it
        continue
    } else {
        # Add normal lines
        $newContent += $line
    }
    
    $i++
}

# Write the updated content back to the file
$newContent | Set-Content -Path $javaFilePath -Encoding UTF8

if ($methodReplaced) {
    Write-Host "Metodo eguneratuta!"
} else {
    Write-Host "Problema: metodo ez dago eguneratuta"
}
