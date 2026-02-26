/**
 * Produktuen Data-basearen Negozio-Logika
 * 
 * Kodearen funtzionalitate nagusia, zaharren artean:
 * - MySQL datu-basea konektatu eta produktuak kudeatu
 * - CRUD eragiketak (Create, Read, Update, Delete) produktuekin
 * - JSON fitxategian produktuak esportatu
 * - CSV fitxategiak datu-basera igo
 * - Estatistikak kalkulatu eta metatatu
 * - Kategoriak eta bilakatak
 * 
 * JDBC eta GSON liburutegiak erabiltzen du.
 * 
 * @author Urko Mendibe
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.FileWriter;

// Business logic class for product operations
public class ProduktuakLogika {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static String DBurl = "jdbc:mysql://localhost:3306/erronka4";
    static String user = "root";
    static String password = "Mendikp_2007";
    static Connection con;
    static Statement st;
    static PreparedStatement pst;
    static ResultSet rs;

    public static void produktuakGehitu() throws IOException {
        System.out.println("Sartu produktuaren datuak: ");
        System.out.println("ID-a: ");
        int ID = Integer.parseInt(br.readLine());
        System.out.println("Izena: ");
        String izena = br.readLine();
        System.out.println("Deskripapena: ");
        String deskribapena = br.readLine();
        System.out.println("Prezioa: ");
        double prezioa = Double.parseDouble(br.readLine());
        System.out.println("Stock-a");
        int stock = Integer.parseInt(br.readLine());
        System.out.println("Kategoria (sartu ID-a): ");
        System.out.println("1111 - Sudaderak");
        System.out.println("2222 - Alkandorak");
        System.out.println("3333 - Kamisetak");
        System.out.println("4444 - Galtzak");
        System.out.println("5555 - Zapatillak");
        int kategoria = Integer.parseInt(br.readLine());
        System.out.println("Sorkuntza-data (yyyy/mm/dd):");
        String sorkuntzaData = br.readLine();
        System.out.println("Irudiaren URL-a: ");
        String irudiaURL = br.readLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBurl, user, password);
            pst = con.prepareStatement(
                    "INSERT INTO Produktuak (ID, izena, deskribapena, prezioa, stock, kat_kod, sorkuntza_data, irudia) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setInt(1, ID);
            pst.setString(2, izena);
            pst.setString(3, deskribapena);
            pst.setDouble(4, prezioa);
            pst.setInt(5, stock);
            pst.setInt(6, kategoria);
            pst.setString(7, sorkuntzaData);
            pst.setString(8, irudiaURL);
            pst.executeUpdate();
            System.out.println("Produktua ondo gehitu da!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null)
                    pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void fitxategiaIgo() throws IOException {
        System.out.println("Sartu fitxategiaren ruta lokala: ");
        String fitxategia = br.readLine();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fitxategia))) {
            con = DriverManager.getConnection(DBurl, user, password);
            pst = con.prepareStatement(
                    "INSERT INTO Produktuak (ID, izena, deskribapena, prezioa, stock, kat_kod, sorkuntza_data, irudia) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] parts = line.split(",");
                int ID = Integer.parseInt(parts[0].trim());
                String izena = parts[1].trim();
                String deskribapena = parts[2].trim();
                double prezioa = Double.parseDouble(parts[3].trim());
                int stock = Integer.parseInt(parts[4].trim());
                int kat_kod = Integer.parseInt(parts[5].trim());
                java.sql.Date sorkuntzaData = java.sql.Date.valueOf(parts[6].trim());
                String irudia = parts[7].trim();

                pst.setInt(1, ID);
                pst.setString(2, izena);
                pst.setString(3, deskribapena);
                pst.setDouble(4, prezioa);
                pst.setInt(5, stock);
                pst.setInt(6, kat_kod);
                pst.setDate(7, sorkuntzaData);
                pst.setString(8, irudia);
                pst.executeUpdate();
            }
            System.out.println("Fitxategia ondo igo da!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null)
                    pst.close();
            } catch (Exception e) {
            }
            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
            }
        }
    }

    public static void produktuakEguneratu() throws IOException {
        System.out.println("Sartu eguneratu nahi duzun produktuaren ID-a: ");
        int ID = Integer.parseInt(br.readLine());

        System.out.println("Zer datu eguneratu nahi duzu? ");
        System.out.println("1. Izena");
        System.out.println("2. Deskribapena");
        System.out.println("3. Prezioa");
        System.out.println("4. Stock");
        System.out.println("5. Kategoria");
        System.out.println("6. Sorkuntza-data");
        System.out.println("7. Irudiaren URL-a");
        int aukera = Integer.parseInt(br.readLine());

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBurl, user, password);

            pst = con.prepareStatement("SELECT * FROM Produktuak WHERE ID = ?");
            pst.setInt(1, ID);
            rs = pst.executeQuery();

            if (rs.next()) {
                String izena = rs.getString("izena");
                String deskribapena = rs.getString("deskribapena");
                double prezioa = rs.getDouble("prezioa");
                int stock = rs.getInt("stock");
                String kategoria = rs.getString("kat_kod");
                String sorkuntzaData = rs.getString("sorkuntza_data");
                String irudiaURL = rs.getString("irudia");

                switch (aukera) {
                    case 1:
                        System.out.println("Sartu nahi duzun izena: ");
                        izena = br.readLine();
                        break;
                    case 2:
                        System.out.println("Sartu nahi duzun deskribapena: ");
                        deskribapena = br.readLine();
                        break;
                    case 3:
                        System.out.println("Sartu nahi duzun prezioa: ");
                        prezioa = Double.parseDouble(br.readLine());
                        break;
                    case 4:
                        System.out.println("Sartu nahi duzun stock-a: ");
                        stock = Integer.parseInt(br.readLine());
                        break;
                    case 5:
                        System.out.println("Sartu nahi duzun kategoria: ");
                        kategoria = br.readLine();
                        break;
                    case 6:
                        System.out.println("Sartu nahi duzun sorkuntza-data: ");
                        sorkuntzaData = br.readLine();
                        break;
                    case 7:
                        System.out.println("Sartu nahi duzun irudiaren URL-a: ");
                        irudiaURL = br.readLine();
                        break;
                    default:
                        System.out.println("Ez da aukerarik aurkitu.");
                        return;
                }

                pst.close();
                pst = con.prepareStatement(
                        "UPDATE Produktuak SET izena = ?, deskribapena = ?, prezioa = ?, stock = ?, kat_kod = ?, sorkuntza_data = ?, irudia = ? WHERE ID = ?");
                pst.setString(1, izena);
                pst.setString(2, deskribapena);
                pst.setDouble(3, prezioa);
                pst.setInt(4, stock);
                pst.setString(5, kategoria);
                pst.setInt(6, ID);
                pst.setString(7, sorkuntzaData);
                pst.setString(8, irudiaURL);
                pst.executeUpdate();

                System.out.println("Produktua ondo eguneratu da!");
            } else {
                System.out.println("Produktu hau ez da aurkitu.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pst != null)
                    pst.close();
                if (con != null)
                    con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void produktuaEzabatu() throws IOException {
        System.out.println("Sartu ezabatu nahi duzun produktuaren ID-a: ");
        int ID = Integer.parseInt(br.readLine());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBurl, user, password);
            pst = con.prepareStatement("DELETE FROM Produktuak WHERE ID = ?");
            pst.setInt(1, ID);
            pst.executeUpdate();
            System.out.println("Produktua ondo ezabatu da!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null)
                    pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void produktuakZerrendatu() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBurl, user, password);
            st = con.createStatement();
            rs = st.executeQuery(
                    "SELECT ID, izena, deskribapena, prezioa, stock, kat_kod, sorkuntza_data FROM Produktuak");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            boolean daturikDago = false;

            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i).toUpperCase() + " ");
            }
            System.out.println();
            while (rs.next()) {
                daturikDago = true;
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + ", ");
                }
                System.out.println();
            }

            if (!daturikDago) {
                System.out.println("Ez dago produkturik datu-basean.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void produktuakZerrendatuKategoria() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Sartu nahi duzun kategoria: ");
        System.out.println("1111 - Sudaderak");
        System.out.println("2222 - Alkandorak");
        System.out.println("3333 - Kamisetak");
        System.out.println("4444 - Galtzak");
        System.out.println("5555 - Zapatillak");
        int aukeraKategoria = Integer.parseInt(br.readLine());

        String kategoriaNizena;
        switch (aukeraKategoria) {
            case 1111:
                kategoriaNizena = "Sudaderak";
                break;
            case 2222:
                kategoriaNizena = "Alkandorak";
                break;
            case 3333:
                kategoriaNizena = "Kamisetak";
                break;
            case 4444:
                kategoriaNizena = "Galtzak";
                break;
            case 5555:
                kategoriaNizena = "Zapatillak";
                break;
            default:
                System.out.println("Aukera baliogabea.");
                return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBurl, user, password);
            pst = con.prepareStatement(
                    "SELECT ID, izena, deskribapena, prezioa, stock, kat_kod, sorkuntza_data FROM Produktuak WHERE kat_kod = ?");
            pst.setInt(1, aukeraKategoria);
            rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            System.out.println("\n--- " + kategoriaNizena.toUpperCase() + " ---");

            boolean daturikDago = false;
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i).toUpperCase() + " ");
            }
            System.out.println();
            while (rs.next()) {
                daturikDago = true;
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + ", ");
                }
                System.out.println();
            }

            if (!daturikDago) {
                System.out.println("Ez dago produkturik kategoria honetan.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void produktuakBilatu() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Sartu bilatu nahi duzun produktuaren Izena: ");
        String bilatuIzena = br.readLine();

        try {
            con = DriverManager.getConnection(DBurl, user, password);
            pst = con.prepareStatement("SELECT * FROM Produktuak WHERE izena = ?");
            pst.setString(1, bilatuIzena);
            rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            boolean daturikDago = false;

            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i).toUpperCase() + "  ");
            }

            System.out.println("\n");

            while (rs.next()) {
                daturikDago = true;
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + ", ");
                }
                System.out.println();
            }

            if (!daturikDago) {
                System.out.println("Ez dago produkturik kategoria honetan.");
            }

        } catch (Exception e) {
            System.out.println("ERROREA");
            e.printStackTrace();
        }

    }

    public static void informazioaEsportatu() throws IOException {
        List<Produktuak> produktuenLista = new ArrayList<>();
        br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Sartu fitxategia gordetzeko ruta: ");
        String rutaDirektorioa = br.readLine();
        try {
            con = DriverManager.getConnection(DBurl, user, password);
            pst = con.prepareStatement("SELECT * FROM Produktuak");
            rs = pst.executeQuery();
            while (rs.next()) {
                Produktuak produktua = new Produktuak();
                produktua.ID = rs.getInt("ID");
                produktua.izena = rs.getString("izena");
                produktua.deskribapena = rs.getString("deskribapena");
                produktua.prezioa = rs.getDouble("prezioa");
                produktua.stock = rs.getInt("stock");
                produktua.kategoria = rs.getString("kat_kod");
                produktua.sorkuntzaData = rs.getString("sorkuntza_data");
                produktua.img = rs.getString("irudia");
                produktuenLista.add(produktua);
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(produktuenLista);
            Path path = Paths.get(rutaDirektorioa + "/produktuak.json");
            Files.write(path, json.getBytes());
            System.out.println("Fitxategia ondo esportatu da!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
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
    
    /**
     * Euroan formatuatutako balioa bueltatzea
     * @param baluoa baluoa
     * @return formatuatutako euroa (XX.XXX,XXâ‚¬)
     */
    private static String formatuEuroa(double balioa) {
        // Euroa formatu lokalizatuarekin
        java.text.DecimalFormat df = new java.text.DecimalFormat("###,##0.00");
        String formatuatua = df.format(balioa);
        // Ordeztu puntua pista eta alderantziz
        formatuatua = formatuatua.replace(",", "|").replace(".", ",").replace("|", ".");
        return formatuatua + "â‚¬";
    }
}

