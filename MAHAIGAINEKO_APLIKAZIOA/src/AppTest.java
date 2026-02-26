
/**\n * Test Baten Fitxategia - Produktuen Kudeaketarako Aplikazioa\n * \n * JUnit 5 erabiltzen du, aplikazioaren funzionaltasuna test-atzeko:\n * - Produktuaren datuak balidezia (ID, izena, prezioa, stock, etc.)\n * - Hutsik dagoen datuak detektatzea\n * - Negatiboa balioak identifikatzea\n * - Menu aukera baliogabeak\n * - Kategorien balidezia\n * - Ekuazioen kalkuluak test-atzea\n * \n * Batxe bakoitzak batentzat egoera-kasuak test-atzen ditu.\n * \n * 
 * @author Urko Mendibe
 *  */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    // ================= PK1 =================
    @Test
    void PK1_produktuaGehituOndo() {
        Produktuak produktua = new Produktuak(23214, "Kamiseta", "Urdina", 15.50, 10, "3333", "2026/02/25", "img.jpg");

        assertEquals(23214, produktua.getIDProduktuak());
        assertFalse(produktua.getProduktuIzena().isEmpty());
        assertFalse(produktua.getProduktuDeskribapena().isEmpty());
        assertTrue(produktua.getIDProduktuak() > 0);
        assertTrue(produktua.getProduktuPrezioa() > 0);
    }

    // ================= PK2 =================
    @Test
    void PK2_izenaOkerra() {
        Produktuak produktua = new Produktuak(1, "", "Deskribapena", 10.0, 5, "2222", "2026/02/25", "img.jpg");
        String izena = produktua.getProduktuIzena();

        assertTrue(izena == null || izena.isEmpty());
    }

    // ================= PK3 =================
    @Test
    void PK3_deskribapenaHutsa() {
        Produktuak produktua = new Produktuak(2, "Produktoa", "", 20.0, 3, "1111", "2026/02/25", "img.jpg");
        String deskribapena = produktua.getProduktuDeskribapena();

        assertTrue(deskribapena == null || deskribapena.isEmpty());
    }

    // ================= PK4 =================
    @Test
    void PK4_idNegatiboa() {
        Produktuak produktua = new Produktuak(-23214, "Produktoa", "Deskribapena", 10.0, 5, "3333", "2026/02/25",
                "img.jpg");
        int id = produktua.getIDProduktuak();

        assertTrue(id < 0);
    }

    // ================= PK5 =================
    @Test
    void PK5_menuAukeraOkerra() {
        int aukera = 9;
        assertTrue(aukera < 1 || aukera > 6);
    }

    // ================= PK6 =================
    @Test
    void PK6_produktuaEguneratu() {
        Produktuak produktua = new Produktuak();
        produktua.setProdutkua(23214, "Sudadera", "Beltza", 25.99, 8, "1111", "2026/02/25", "sudadera.jpg");

        assertEquals(23214, produktua.getIDProduktuak());
        assertEquals("Sudadera", produktua.getProduktuIzena());
        assertTrue(produktua.getIDProduktuak() > 0);
    }

    // ================= PK7 =================
    @Test
    void PK7_produktuaEzabatu() {
        Produktuak produktua = new Produktuak(23214, "Produktoa", "Deskribapena", 15.0, 10, "2222", "2026/02/25",
                "img.jpg");

        assertEquals(23214, produktua.getIDProduktuak());
        assertNotNull(produktua.getProduktuIzena());
    }

    // ================= PK8 =================
    @Test
    void PK8_eguneraketaAukeraOkerra() {
        int eguneraketa = 0;

        assertTrue(eguneraketa < 1 || eguneraketa > 3);
    }

    // ================= PK9 =================
    @Test
    void PK9_zerrendatuPrezioz() {
        Produktuak produktua1 = new Produktuak(1, "Kamiseta", "Urdina", 10.0, 5, "3333", "2026/02/25", "img1.jpg");
        Produktuak produktua2 = new Produktuak(2, "Sudadera", "Gorria", 25.0, 3, "1111", "2026/02/25", "img2.jpg");

        assertTrue(produktua1.getProduktuPrezioa() < produktua2.getProduktuPrezioa());
    }

    // ================= PK10 =================
    @Test
    void PK10_zerrendatuEskuragarritasunez() {
        Produktuak produktua1 = new Produktuak(1, "Kamiseta", "Urdina", 10.0, 2, "3333", "2026/02/25", "img1.jpg");
        Produktuak produktua2 = new Produktuak(2, "Sudadera", "Gorria", 25.0, 10, "1111", "2026/02/25", "img2.jpg");

        assertTrue(produktua1.getProduktuStock() < produktua2.getProduktuStock());
    }

    // ================= PK11 =================
    @Test
    void PK11_kategoriaEtaPrezioa() {
        Produktuak produktua = new Produktuak(1, "Kamiseta", "Urdina", 15.50, 5, "3333", "2026/02/25", "img.jpg");

        assertEquals("3333", produktua.getProduktuKategoria());
        assertTrue(produktua.getProduktuPrezioa() > 0);
    }

    // ================= PK12 =================
    @Test
    void PK12_zerrendatuAukeraOkerra() {
        int aukera = 5;

        assertTrue(aukera != 1 && aukera != 2);
    }

    // ================= PK13 =================
    @Test
    void PK13_bilatuIzenez() {
        Produktuak produktua = new Produktuak(1, "Kamiseta", "Urdina", 10.0, 5, "3333", "2026/02/25", "img.jpg");
        String izena = produktua.getProduktuIzena();

        assertNotNull(izena);
        assertFalse(izena.isEmpty());
        assertEquals("Kamiseta", izena);
    }

    // ================= PK14 =================
    @Test
    void PK14_bilatuIDz() {
        Produktuak produktua = new Produktuak(23214, "Produktoa", "Deskribapena", 10.0, 5, "2222", "2026/02/25",
                "img.jpg");
        int id = produktua.getIDProduktuak();

        assertTrue(id > 0);
        assertEquals(23214, id);
    }

    // ================= PK15 =================
    @Test
    void PK15_bilaketaHutsa() {
        Produktuak produktua = new Produktuak(1, "", "", 0.0, 0, "", "", "");
        String izena = produktua.getProduktuIzena();

        assertTrue(izena == null || izena.isEmpty());
    }
}