/**
 *
 */
package wymiana;
import java.util.*;

/**
 * @author ms335789
 * Strategia polega na usuwaniu z pamięci stron najdawniej urzytych
 */
class StrategiaLRU extends Strategia {
	
    TreeMap<Integer, Integer> mapaNumerStronyDoCzasuOdwołania;
    TreeMap<Integer, Integer> mapaCzasOdwołaniaDoNumeruStrony;
    private int czas;
    
    /**
     * @param tab
     * @param rozmiar
     * Strategia będzie urzywać czasu, aby znać kolejności wrzucania do pamięci,
     * urzywa też 2 map, jedna wskazuje od numeru strony na ostatni czas urzycia, 
     * druga od czasu ostatniego urzycia do numeru strony
     */
    public StrategiaLRU(int[] tab, int rozmiar) {
        super(tab, rozmiar);
        mapaNumerStronyDoCzasuOdwołania = new TreeMap<Integer, Integer>();
        mapaCzasOdwołaniaDoNumeruStrony = new TreeMap<Integer, Integer>();
        czas = -1000;
    }

    @Override
    protected boolean czyJestMiejsce() {
        return rozmiar > mapaNumerStronyDoCzasuOdwołania.size();
    }

    @Override
    protected boolean czyZnajdujeSieWPamieci(int numerStrony) {
        return mapaNumerStronyDoCzasuOdwołania.containsKey(numerStrony);
    }

    @Override
    protected int zwolnijMiejsce() {
        int najdawniej = mapaCzasOdwołaniaDoNumeruStrony.firstKey();
        int doUsuniecia = mapaCzasOdwołaniaDoNumeruStrony.remove(najdawniej);
        mapaNumerStronyDoCzasuOdwołania.remove(doUsuniecia);
        
        return doUsuniecia;
    }

    @Override
    protected void dodajStronę(int numerStrony) {
        mapaNumerStronyDoCzasuOdwołania.put(numerStrony, czas);
        mapaCzasOdwołaniaDoNumeruStrony.put(czas,numerStrony);
    }

    /* jeżeli urzywam danej strony, która jest w pamięci, to aktualizuję, że została teraz urzyta
     */
    @Override
    protected void odświeżStatusStrony(int numerStrony) {
        int czasStrony = mapaNumerStronyDoCzasuOdwołania.get(numerStrony);
        mapaNumerStronyDoCzasuOdwołania.put(numerStrony, czas);
        mapaCzasOdwołaniaDoNumeruStrony.put(czas, numerStrony);
        mapaCzasOdwołaniaDoNumeruStrony.remove(czasStrony);
    }

    /* przy wykonywaniu tej wersji wykonaj aktualizuję czas jako odności
     */
    @Override
    int wykonaj() {
        for(int numerStrony : tab){
            czas++;
            obsłużStronę(numerStrony);
        }
        
        return błędy();
    }

}


