import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
        BufferedReader breader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Sartu fitxategiaren ruta lokala: ");
        String fitxategia = breader.readLine();
        br = new BufferedReader(new FileReader(fitxategia));
        try {
            con = DriverManager.getConnection(DBurl, user, password);
            String line;
            pst = con.prepareStatement(
                    "INSERT INTO Produktuak (ID, izena, deskribapena, prezioa, stock, kat_kod, sorkuntza_data, irudia) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                pst.setInt(1, Integer.parseInt(parts[0]));
                pst.setString(2, parts[1]);
                pst.setString(3, parts[2]);
                pst.setDouble(4, Double.parseDouble(parts[3]));
                pst.setInt(5, Integer.parseInt(parts[4]));
                pst.setString(6, parts[5]);
                pst.setString(7, parts[6]);
                pst.setString(8, parts[7]);
                pst.executeUpdate();
            }
            br.close();
            pst.close();
            con.close();

            System.out.println("Fitxategia ondo igo da!");
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void informazioaEsportatu() {
        List<Produktuak> produktuenLista = new ArrayList<>();
        String rutaDirektorioa = "C:\\Users\\ikumendibe25\\Documents\\ERRONKAK\\Erronka4_Urko\\WEB_ORRIA\\htdocs\\js";
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
}
