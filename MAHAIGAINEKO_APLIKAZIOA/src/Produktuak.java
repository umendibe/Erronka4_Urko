/**
 * Produktuaren Entitatea - Datuak Gordetzea
 * 
 * Produktu bakoitzaren datuak gordetzeko klasea, esaterako:
 * - ID: Produktuaren identifikazioa (bakarra)
 * - izena: Produktuaren izena
 * - deskribapena: Produktuaren deskribapena
 * - prezioa: Produktuaren prezioa
 * - stock: Stock-en kantitatea
 * - kategoria: Produktuaren kategoria
 * - sorkuntzaData: Sortutako data
 * - img: Produktuaren irudiaren URL
 * 
 * Getters eta setters batekin datuak sartzeko eta biltegitik atzitzeko.
 * 
 * @author Urko Mendibe
 * @version 1.0
 * @since 2026
 */
public class Produktuak {
    int ID;
    String izena;
    String deskribapena;
    double prezioa;
    int stock;
    String kategoria;
    String sorkuntzaData;
    String img;

    public Produktuak() {
    }

    public Produktuak(int ID, String izena, String deskribapena, double prezioa, int stock, String kategoria,
            String sorkuntzaData, String img) {
        this.ID = ID;
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.prezioa = prezioa;
        this.stock = stock;
        this.kategoria = kategoria;
        this.sorkuntzaData = sorkuntzaData;
        this.img = img;
    }

    public void setProdutkua(int prodID, String prodIzena, String prodDeskr, double prodPrezioa, int prodStock,
            String prodKat, String prodSorkuntzaData, String prodImg) {
        this.ID = prodID;
        this.izena = prodIzena;
        this.deskribapena = prodDeskr;
        this.prezioa = prodPrezioa;
        this.stock = prodStock;
        this.kategoria = prodKat;
        this.sorkuntzaData = prodSorkuntzaData;
        this.img = prodImg;
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

    public String getProduktuSorkuntzaData() {
        return sorkuntzaData;
    }

    public String getProduktuImg() {
        return img;
    }
}
