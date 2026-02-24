import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class App {
    static BufferedReader br;
    static String DBurl = "jdbc:mysql://localhost:3306/erronka4";
    static String user = "root";
    static String password = "Mendikp_2007";
    static Connection con;
    static Statement st;
    static PreparedStatement pst;
    static ResultSet rs;
    static String errepikatu;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("--AUKERATU--" +
                "\n 1. Produktuak Gehitu " +
                "\n 2. CSV Fitxategia Igo" +
                "\n 3. Produktuak Eguneratu" +
                "\n 4. Produktuak Ezabatu" +
                "\n 5. Produktuak Zerrendatu" +
                "\n 6. Produktuak Bilatu" +
                "\n 7. Programa Amaitu");
        int aukeraMenu = Integer.parseInt(br.readLine());

        switch (aukeraMenu) {
            case 1:
                produktuakGehitu();
                System.out.println("Beste produktu bat gehitu nahi duzu? (bai/ez)");
                errepikatu = br.readLine();
                while (errepikatu.equals("bai")) {
                    produktuakGehitu();
                    if (errepikatu.equals("ez")) {
                        break;
                    }
                }
                break;

            case 2:
                fitxategiaIgo();
                break;

            case 3:
                produktuakEguneratu();
                break;

            case 4:
                produktuaEzabatu();
                break;

            case 5:
                System.out.println("Nola zerrendatu nahi dituzu? ");
                System.out.println("1. Produktu guztiak ikusi");
                System.out.println("2. Kategoriaren arabera");
                int aukeraZerrendatu = Integer.parseInt(br.readLine());
                if (aukeraZerrendatu == 1) {
                    produktuakZerrednatu();
                } else if (aukeraZerrendatu == 2) {
                    produktuakZerrendatuKategoria();
                }
                break;

            case 6:
                produktuakBilatu();
                break;

            case 7:
                System.out.println("PROGRAMA ITXI DUZU.");
                break;

            default:
                break;
        }
    }

    public static void produktuakGehitu() throws IOException {
        System.out.println("Sartu produktuaren datuak: ");
        System.out.println("ID-a: ");
        Produktuak.ID = Integer.parseInt(br.readLine());
        System.out.println("Izena: ");
        Produktuak.izena = br.readLine();
        System.out.println("Deskripapena: ");
        Produktuak.deskribapena = br.readLine();
        System.out.println("Prezioa: ");
        Produktuak.prezioa = Double.parseDouble(br.readLine());
        System.out.println("Stock-a");
        Produktuak.stock = Integer.parseInt(br.readLine());
        System.out.println("Kategoria");
        Produktuak.kategoria = br.readLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBurl, user, password);
            pst = con.prepareStatement(
                    "INSERT INTO Produktuak (ID, izena, deskribapena, prezioa, stock, kategoria) VALUES (?, ?, ?, ?, ?, ?)");
            pst.setInt(1, Produktuak.ID);
            pst.setString(2, Produktuak.izena);
            pst.setString(3, Produktuak.deskribapena);
            pst.setDouble(4, Produktuak.prezioa);
            pst.setInt(5, Produktuak.stock);
            pst.setString(6, Produktuak.kategoria);
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
                    "INSERT INTO Produktuak (ID, izena, deskribapena, prezioa, stock, kategoria) VALUES (?, ?, ?, ?, ?, ?)");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                pst.setInt(1, Integer.parseInt(parts[0]));
                pst.setString(2, parts[1]);
                pst.setString(3, parts[2]);
                pst.setDouble(4, Double.parseDouble(parts[3]));
                pst.setInt(5, Integer.parseInt(parts[4]));
                pst.setString(6, parts[5]);

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
        Produktuak.ID = Integer.parseInt(br.readLine());

        System.out.println("Zer datu eguneratu nahi duzu? ");
        System.out.println("1. Izena");
        System.out.println("2. Deskribapena");
        System.out.println("3. Prezioa");
        System.out.println("4. Stock");
        System.out.println("5. Kategoria");
        int aukera = Integer.parseInt(br.readLine());

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBurl, user, password);

            pst = con.prepareStatement("SELECT * FROM Produktuak WHERE ID = ?");
            pst.setInt(1, Produktuak.ID);
            rs = pst.executeQuery();

            if (rs.next()) {
                String izena = rs.getString("izena");
                String deskribapena = rs.getString("deskribapena");
                double prezioa = rs.getDouble("prezioa");
                int stock = rs.getInt("stock");
                String kategoria = rs.getString("kategoria");

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
                    default:
                        System.out.println("Ez da aukerarik aurkitu.");
                        return;
                }

                pst.close();
                pst = con.prepareStatement(
                        "UPDATE Produktuak SET izena = ?, deskribapena = ?, prezioa = ?, stock = ?, kategoria = ? WHERE ID = ?");
                pst.setString(1, izena);
                pst.setString(2, deskribapena);
                pst.setDouble(3, prezioa);
                pst.setInt(4, stock);
                pst.setString(5, kategoria);
                pst.setInt(6, Produktuak.ID);
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
        Produktuak.ID = Integer.parseInt(br.readLine());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBurl, user, password);
            pst = con.prepareStatement("DELETE FROM Produktuak WHERE ID = ?");
            pst.setInt(1, Produktuak.ID);
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

    public static void produktuakZerrednatu() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBurl, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Produktuak");
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void produktuakZerrendatuKategoria() throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Sartu nahi duzun kategoria: ");
        System.out.println("1. Zapatillak");
        System.out.println("2. Sudaderak");
        System.out.println("3. Kamisetak");
        System.out.println("4. Alkandorak");
        System.out.println("5. Galtzak");
        int aukeraKategoria = Integer.parseInt(br.readLine());

        String kategoriaNizena;
        switch (aukeraKategoria) {
            case 1:
                kategoriaNizena = "Zapatillak";
                break;
            case 2:
                kategoriaNizena = "Sudaderak";
                break;
            case 3:
                kategoriaNizena = "Kamisetak";
                break;
            case 4:
                kategoriaNizena = "Alkandorak";
                break;
            case 5:
                kategoriaNizena = "Galtzak";
                break;
            default:
                System.out.println("Aukera baliogabea.");
                return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DBurl, user, password);
            pst = con.prepareStatement("SELECT * FROM Produktuak WHERE kategoria = ?");
            pst.setString(1, kategoriaNizena);
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
        System.out.println("Sartu bilatu nahi duzun produktuaren ID-a: ");
        int bilatuID = Integer.parseInt(br.readLine());

        try {
            con = DriverManager.getConnection(DBurl, user, password);
            pst = con.prepareStatement("SELECT * FROM Produktuak WHERE ID = ?");
            pst.setInt(1, bilatuID);
            rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            boolean daturikDago = false;

            for (int i = 1; i<=columnCount; i++) {
                    System.out.print(rsmd.getColumnName(i).toUpperCase() + "  ");
                }

            System.out.println("\n");

            while (rs.next()) {
                daturikDago = true;
                for (int i = 1; i<=columnCount; i++) {
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
}
