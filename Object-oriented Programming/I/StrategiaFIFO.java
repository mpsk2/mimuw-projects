/**
 *
 */
package wymiana;

import java.util.*;

/**
 * @author ms335789
 * strategia polega na usuwaniu z pamięci stron najdawniej dodanych do pamięci
 */
class StrategiaFIFO extends Strategia {
    LinkedList<Integer> kolejka;
    TreeSet<Integer> zbiór;
    
    /**
     * @param tab
     * @param rozmiar
     * tworzy kolejkę FIFO z elementami w kolejności w jakiej zostały urzyte oraz zbiór elementów w pamięci
     */
    public StrategiaFIFO(int[] tab, int rozmiar) {
        super(tab, rozmiar);
        kolejka = new LinkedList<Integer>();
        zbiór = new TreeSet<Integer>();
    }

    @Override
    protected boolean czyJestMiejsce() {
        return rozmiar > zbiór.size();
    }

    @Override
    protected boolean czyZnajdujeSieWPamieci(int numerStrony) {
        return zbiór.contains(numerStrony);
    }

    @Override
    protected int zwolnijMiejsce() {
        int numerStronyDoZwolnienia = kolejka.poll();
        zbiór.remove(numerStronyDoZwolnienia);
        return numerStronyDoZwolnienia;
    }

    @Override
    protected void dodajStronę(int numerStrony) {
        zbiór.add(numerStrony);
        kolejka.offer(numerStrony);
    }

}


