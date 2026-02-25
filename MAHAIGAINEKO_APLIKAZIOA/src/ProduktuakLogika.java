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
            pst = con.prepareStatement("SELECT * FROM Produktuak WHERE kat_kod = ?");
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

    public static void esportatuGehienSaldutakoak() throws IOException {
        List<Object> lista = new ArrayList<>();
        br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Sartu fitxategia gordetzeko ruta: ");
        String rutaDirektorioa = br.readLine();

        try {
            con = DriverManager.getConnection(DBurl, user, password);

            String sql = "SELECT p.ID, p.izena, SUM(pe.kopurua) AS guztira_salduta " +
                    "FROM Produktuak p " +
                    "JOIN Produktu_Eskari pe ON p.ID = pe.prod_kod " +
                    "GROUP BY p.ID " +
                    "ORDER BY guztira_salduta DESC " +
                    "LIMIT 5";

            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("ID", rs.getInt("ID"));
                obj.put("izena", rs.getString("izena"));
                obj.put("guztira_salduta", rs.getInt("guztira_salduta"));
                lista.add(obj);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Files.write(Paths.get(rutaDirektorioa + "/gehien_saldutakoak.json"), gson.toJson(lista).getBytes());

            System.out.println("Gehien saldutako produktuak esportatuta!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void esportatuEstatistikak() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Sartu fitxategia gordetzeko ruta: ");
        String rutaDirektorioa = br.readLine();

        try {
            con = DriverManager.getConnection(DBurl, user, password);

            Map<String, Object> stats = new HashMap<>();

            Statement st = con.createStatement();

            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM Produktuak");
            if (rs1.next())
                stats.put("produktu_kopurua", rs1.getInt(1));

            ResultSet rs2 = st.executeQuery("SELECT SUM(stock) FROM Produktuak");
            if (rs2.next())
                stats.put("stock_totala", rs2.getInt(1));

            ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM Eskariak");
            if (rs3.next())
                stats.put("eskari_kopurua", rs3.getInt(1));

            ResultSet rs4 = st.executeQuery("SELECT SUM(prezio_totala) FROM Eskariak");
            if (rs4.next())
                stats.put("diru_sarrera_totala", rs4.getDouble(1));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Files.write(Paths.get(rutaDirektorioa + "/estatistikak.json"), gson.toJson(stats).getBytes());

            System.out.println("Estatistikak esportatuta!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
