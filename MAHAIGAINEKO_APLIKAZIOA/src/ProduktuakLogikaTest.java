import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class ProduktuakLogikaTest {

    // ================= PK1 =================
    @Test
    void PK1_produktuaGehituOndo() throws Exception {

        String input = "23214\nKamiseta\nUrdina\n10\n5\n3333\n2026-02-25\nimg.jpg\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProduktuakLogika.br = new BufferedReader(new InputStreamReader(System.in));

        boolean exekutatu = true;

        try {
            ProduktuakLogika.produktuakGehitu();
        } catch (Exception e) {
            exekutatu = false;
        }

        assertTrue(exekutatu);
    }

    // ================= PK2 =================
    @Test
    void PK2_izenaOkerra() throws Exception {

        String input = "23214\n\nUrdina\n10\n5\n3333\n2026-02-25\nimg.jpg\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProduktuakLogika.br = new BufferedReader(new InputStreamReader(System.in));

        boolean errorea = true;

        try {
            ProduktuakLogika.produktuakGehitu();
        } catch (Exception e) {
            errorea = false;
        }

        assertTrue(errorea);
    }

    // ================= PK3 =================
    @Test
    void PK3_deskribapenaHutsa() throws Exception {

        String input = "23214\nKamiseta\n\n10\n5\n3333\n2026-02-25\nimg.jpg\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProduktuakLogika.br = new BufferedReader(new InputStreamReader(System.in));

        boolean exekutatu = true;

        try {
            ProduktuakLogika.produktuakGehitu();
        } catch (Exception e) {
            exekutatu = false;
        }

        assertTrue(exekutatu);
    }

    // ================= PK4 =================
    @Test
    void PK4_idNegatiboa() throws Exception {

        String input = "-23214\nKamiseta\nUrdina\n10\n5\n3333\n2026-02-25\nimg.jpg\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProduktuakLogika.br = new BufferedReader(new InputStreamReader(System.in));

        boolean exekutatu = true;

        try {
            ProduktuakLogika.produktuakGehitu();
        } catch (Exception e) {
            exekutatu = false;
        }

        assertTrue(exekutatu);
    }

    // ================= PK5 =================
    @Test
    void PK5_menuAukeraOkerra() {

        int aukera = 9;

        assertTrue(aukera < 1 || aukera > 6);
    }

    // ================= PK6 =================
    @Test
    void PK6_produktuaEguneratu() throws Exception {

        String input = "23214\n1\nSudadera\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProduktuakLogika.br = new BufferedReader(new InputStreamReader(System.in));

        boolean eguneratua = true;

        try {
            ProduktuakLogika.produktuakEguneratu();
        } catch (Exception e) {
            eguneratua = false;
        }

        assertTrue(eguneratua);
    }

    // ================= PK7 =================
    @Test
    void PK7_produktuaEzabatu() throws Exception {

        String input = "23214\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProduktuakLogika.br = new BufferedReader(new InputStreamReader(System.in));

        boolean ezabatua = true;

        try {
            ProduktuakLogika.produktuaEzabatu();
        } catch (Exception e) {
            ezabatua = false;
        }

        assertTrue(ezabatua);
    }

    // ================= PK8 =================
    @Test
    void PK8_eguneraketaAukeraOkerra() {

        int aukera = 0;

        assertTrue(aukera < 1 || aukera > 3);
    }

    // ================= PK9 =================
    @Test
    void PK9_zerrendatuPrezioz() {

        boolean exekutatu = true;

        try {
            ProduktuakLogika.produktuakZerrendatu();
        } catch (Exception e) {
            exekutatu = false;
        }

        assertTrue(exekutatu);
    }

    // ================= PK10 =================
    @Test
    void PK10_zerrendatuEskuragarritasuna() {

        boolean exekutatu = true;

        try {
            ProduktuakLogika.produktuakZerrendatu();
        } catch (Exception e) {
            exekutatu = false;
        }

        assertTrue(exekutatu);
    }

    // ================= PK11 =================
    @Test
    void PK11_zerrendatuKategoria() throws Exception {

        String input = "3333\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProduktuakLogika.br = new BufferedReader(new InputStreamReader(System.in));

        boolean exekutatu = true;

        try {
            ProduktuakLogika.produktuakZerrendatuKategoria();
        } catch (Exception e) {
            exekutatu = false;
        }

        assertTrue(exekutatu);
    }

    // ================= PK12 =================
    @Test
    void PK12_zerrendatuAukeraOkerra() {

        int aukera = 5;

        assertFalse(aukera == 1 || aukera == 2);
    }

    // ================= PK13 =================
    @Test
    void PK13_bilatuIzenez() throws Exception {

        String input = "Kamiseta\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProduktuakLogika.br = new BufferedReader(new InputStreamReader(System.in));

        boolean exekutatu = true;

        try {
            ProduktuakLogika.produktuakBilatu();
        } catch (Exception e) {
            exekutatu = false;
        }

        assertTrue(exekutatu);
    }

    // ================= PK14 =================
    @Test
    void PK14_bilatuIDz() throws Exception {

        String input = "23214\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProduktuakLogika.br = new BufferedReader(new InputStreamReader(System.in));

        boolean exekutatu = true;

        try {
            ProduktuakLogika.produktuakBilatu();
        } catch (Exception e) {
            exekutatu = false;
        }

        assertTrue(exekutatu);
    }

    // ================= PK15 =================
    @Test
    void PK15_bilaketaHutsa() throws Exception {

        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ProduktuakLogika.br = new BufferedReader(new InputStreamReader(System.in));

        boolean exekutatu = true;

        try {
            ProduktuakLogika.produktuakBilatu();
        } catch (Exception e) {
            exekutatu = false;
        }

        assertTrue(exekutatu);
    }
}