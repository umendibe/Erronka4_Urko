import java.io.*;

/**
 * Klase Nagusia - Produktuen Kudeaketarako Aplikazioa
 * 
 * Menu interaktibo bat ematen du, erabiltzaileari produktuak kudeatzeko aukera:
 * - Produktuak gehitu edo eguneratu
 * - CSV fitxategiak igo
 * - Produktuak ezabatu edo bilatu
 * - Datu-estatistikak esportatu
 * - Zerrendata kategorien arabera
 * 
 * @author Urko Mendibe
 */
public class App {
    static String errepikatu;
    static BufferedReader br;  

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        int aukeraMenu = 0;
        while (aukeraMenu != 8) {
            System.out.println("--AUKERATU--" +
                    "\n 1. Produktuak Gehitu " +
                    "\n 2. CSV Fitxategia Igo" +
                    "\n 3. Produktuak Eguneratu" +
                    "\n 4. Produktuak Ezabatu" +
                    "\n 5. Produktuak Zerrendatu" +
                    "\n 6. Produktuak Bilatu" +
                    "\n 7. Informazioa Esportatu" +
                    "\n 8. Programa Amaitu");
            aukeraMenu = Integer.parseInt(br.readLine());

            switch (aukeraMenu) {
                case 1:
                    ProduktuakLogika.produktuakGehitu();
                    System.out.println("Beste produktu bat gehitu nahi duzu? (bai/ez)");
                    errepikatu = br.readLine();
                    while (errepikatu.equals("bai")) {
                        ProduktuakLogika.produktuakGehitu();
                        if (errepikatu.equals("ez")) {
                            break;
                        }
                    }
                    break;

                case 2:
                    ProduktuakLogika.fitxategiaIgo();
                    break;

                case 3:
                    ProduktuakLogika.produktuakEguneratu();
                    break;

                case 4:
                    ProduktuakLogika.produktuaEzabatu();
                    break;

                case 5:
                    System.out.println("Nola zerrendatu nahi dituzu? ");
                    System.out.println("1. Produktu guztiak ikusi");
                    System.out.println("2. Kategoriaren arabera");
                    int aukeraZerrendatu = Integer.parseInt(br.readLine());
                    if (aukeraZerrendatu == 1) {
                        ProduktuakLogika.produktuakZerrendatu();
                    } else if (aukeraZerrendatu == 2) {
                        ProduktuakLogika.produktuakZerrendatuKategoria();
                    }
                    break;

                case 6:
                    ProduktuakLogika.produktuakBilatu();
                    break;

                case 7:
                    System.out.println("Zer esportatu nahi duzu?");
                    System.out.println("1. Gehien saldutako produktuak");
                    System.out.println("2. Estatistikak");
                    System.out.println("3. Produktu guztien informazioa");
                    int aukeraEsportatu = Integer.parseInt(br.readLine());
                    if (aukeraEsportatu == 1) {
                        ProduktuakLogika.esportatuGehienSaldutakoak();
                    } else if (aukeraEsportatu == 2) {
                        ProduktuakLogika.esportatuEstatistikak();
                    } else if (aukeraEsportatu == 3) {
                        ProduktuakLogika.informazioaEsportatu();
                    }
                    break;

                case 8:
                    System.out.println("PROGRAMA ITXI DUZU.");
                    break;
                default:
                    System.out.println("Aukera baliogabea.");
                    break;
            }
        }
    }

}
