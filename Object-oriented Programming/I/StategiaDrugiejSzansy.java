/**
 *
 */
package wymiana;
import java.util.*;

/**
 * @author ms335789
 * strategia polega na usuwaniu z pamięci stron najdawniej dodanych do pamięci i nieposiadających "drugiej szansy"
 * druga szansa jest dawana na początku i resetowana za każdym odwołaniem do strony
 */
class StrategiaDrugiejSzansy extends Strategia {

    LinkedList<Integer> kolejka;
    TreeMap<Integer, Boolean> mapa;
    
    /**
     * @param tab
     * @param rozmiar
     * tworzy kolejkę, kiedy zostało uzyte oraz mapę czy dana strona w pamięci posiada drugą szansę
     */
    public StrategiaDrugiejSzansy(int[] tab, int rozmiar) {
        super(tab, rozmiar);
        kolejka = new LinkedList<Integer>();
        mapa = new TreeMap<Integer, Boolean>();
    }

    @Override
    protected boolean czyJestMiejsce() {
        return rozmiar > mapa.size();
    }

    @Override
    protected boolean czyZnajdujeSieWPamieci(int numerStrony) {
        return mapa.containsKey(numerStrony);
    }


    @Override
    protected int zwolnijMiejsce() {
        int doUsuniecia;
        boolean bitDrugiejSzansy;
        do{
            doUsuniecia = kolejka.poll();
            bitDrugiejSzansy = mapa.get(doUsuniecia);
            if(bitDrugiejSzansy){
                kolejka.offer(doUsuniecia);
                mapa.put(doUsuniecia, false);
            } else
                mapa.remove(doUsuniecia);
        }while(bitDrugiejSzansy);
        return doUsuniecia;
    }

    @Override
    protected void dodajStronę(int numerStrony) {
        kolejka.offer(numerStrony);
        mapa.put(numerStrony, true);
    }
    
    /* odświeża "drugą szansę" danej strony
     */
    protected void odświeżStatusStrony(int numerStrony) {
        mapa.put(numerStrony, true);
    }

}


