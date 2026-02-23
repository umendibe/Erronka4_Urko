import java.sql.Struct;

public class Produktuak {
    static int ID;
    static String izena;
    static String deskribapena;
    static double prezioa;
    static int stock;
    static String kategoria;

    public Produktuak(int ID, String izena, String deskribapena, double prezioa, int stock, String kategoria) {
        ID = this.ID;
        izena = this.izena;
        deskribapena = this.deskribapena;
        prezioa = this.prezioa;
        stock = this.stock;
        kategoria = this.kategoria;
    }

    public void setProdutkua(int prodID, String prodIzena, String prodDeskr, double prodPrezioa, int prodStock, String prodKat) {
        prodID = ID;
        prodIzena = izena;
        prodDeskr = deskribapena;
        prodPrezioa = prezioa;
        prodStock = stock;
        prodKat = kategoria;
    }

    public int getIDProduktuak() {
        return ID;
    }

    public String getProduktuIzena() {
        return izena;
    }

    public String getProduktuDeskribapena() {
        return deskribapena;
    }

    public double getProduktuPrezioa() {
        return prezioa;
    }

    public int getProduktuStock() {
        return stock;
    }

    public String getProduktuKategoria() {
        return kategoria;
    }
}
