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
                "\n 1. Produktuak Gehitu +" +
                "\n 2. CSV Fitxategia Igo" +
                "\n 3. Produktuak Egineratu" +
                "\n 4. Produktuak Zerrendatu" +
                "\n 5. Produktuak Bilatu" +
                "\n 6. Programa Amaitu");
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

                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

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

    public void produktuakEguneratu() {

    }

    public void produktuakZerrednatu() {

    }

    public void produktuakBilatu() {

    }
}
