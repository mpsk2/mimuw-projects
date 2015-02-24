/**
 *
 */
package wymiana;

/**
 * @author ms335789
 * klasa główna do zadania, publiczna
 */

public class Odwołania {
    
    final private int[] tab;
    final private String s;
    final private int rozmiar;
    private Strategia strategia;
    
    /**
     * tworzy obiekt klasy odwołania, który później będzie symulował dla danej strategi i ilości stron, stronicowanie
     * @param tab numery stron, do których będą odwołania
     * @param s nazwa strategi
     * @param rozmiar ilość stron
     */
    public Odwołania(int[] tab, String s, int rozmiar){
        this.tab = tab;
        this.s = s;
        this.rozmiar = rozmiar;
    }
    
    /**
     * Wykonuje symulację stronicowania, dla danych podanych w konstruktorze
     */
    public void wykonaj(){
        strategia = Strategia.fabrykaStrategia(s, tab, rozmiar);
        System.out.println("Strategia: " + s + ", ramki: " + rozmiar);
        System.out.println("Odwołania: " + tab.length + ", błędy: " + strategia.wykonaj());
    }

}


