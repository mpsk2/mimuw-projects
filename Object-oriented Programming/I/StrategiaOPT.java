/**
 *
 */
package wymiana;
import java.util.*;
/**
 * @author ms335789
 * strategia polega na usuwaniu z pamięci takich stron, do których najdłużej nie będzie odwołania
 */
class StrategiaOPT extends Strategia {
    TreeSet<Integer> zbiórWPamięci;
    TreeSet<Integer> zbiórBezPrzyszłychOdwołań;
    TreeMap<Integer, LinkedList<Integer>> mapaKiedyOdwołania;
    TreeMap<Integer, Integer> mapaOdwołanieDoStrony;
    /**
     * @param tab
     * @param rozmiar
     * Tworzy zbiór obiektów w pamięci, obiektów do których już nie będzie odwołań, mapę wskazującą od czasu następnego urzycia do numeru strony
     * i mapę od numeru strony do listy chwil następnych urzyć
     */
    public StrategiaOPT(int[] tab, int rozmiar) {
        super(tab, rozmiar);
        zbiórWPamięci = new TreeSet<Integer>();
        zbiórBezPrzyszłychOdwołań = new TreeSet<Integer>();
        mapaKiedyOdwołania = new TreeMap<Integer, LinkedList<Integer>>();
        mapaOdwołanieDoStrony = new TreeMap<Integer, Integer>();
        LinkedList<Integer> pom;
        for(int i = 0; i < tab.length; i++){
            pom = mapaKiedyOdwołania.get(tab[i]);
            if(pom == null){
                pom = new LinkedList<Integer>();
                mapaKiedyOdwołania.put(tab[i], pom);
            }
            pom.add(i);
        }
    }

    @Override
    protected boolean czyJestMiejsce() {
        return rozmiar > zbiórWPamięci.size();
    }

    @Override
    protected boolean czyZnajdujeSieWPamieci(int numerStrony) {
        return zbiórWPamięci.contains(numerStrony);
    }

    /* zwalnia miejsce, za kandydata bierze
     * ->jeżeli jest taki, do którego nie będzie odwołania, to z takich takiego o najmniejszym numerze
     * ->w przeciwnym wypadku takiego o największym czasie odwołania
     */
    @Override
    protected int zwolnijMiejsce() {
        int kandydatDoUsuniecia;
        if(zbiórBezPrzyszłychOdwołań.isEmpty()){
            Map.Entry<Integer, Integer> ostatni = mapaOdwołanieDoStrony.lastEntry();
            kandydatDoUsuniecia = ostatni.getValue();
            mapaOdwołanieDoStrony.remove(ostatni.getKey());
        } else {
            kandydatDoUsuniecia = zbiórBezPrzyszłychOdwołań.first();
            zbiórBezPrzyszłychOdwołań.remove(kandydatDoUsuniecia);
        }
        zbiórWPamięci.remove(kandydatDoUsuniecia);
        return kandydatDoUsuniecia;
    }


    @Override
    protected void dodajStronę(int numerStrony) {
        zbiórWPamięci.add(numerStrony);
        LinkedList<Integer> kiedyOdwołania = mapaKiedyOdwołania.get(numerStrony);
        if(kiedyOdwołania.size() == 1)
            zbiórBezPrzyszłychOdwołań.add(numerStrony);
        else{
            kiedyOdwołania.removeFirst();
            mapaOdwołanieDoStrony.put(kiedyOdwołania.getFirst(), numerStrony);
        }
    }

    /* jeżeli urzywam strony, która już jest w pamięci, to odświerzam czas kiedy będzie następne jej odwołanie lub mówię, że takowego nie będzie
     */
    @Override
    protected void odświeżStatusStrony(int numerStrony) {
        LinkedList<Integer> pom = mapaKiedyOdwołania.get(numerStrony);
        int obecne = pom.getFirst();
        pom.removeFirst();
        if(pom.isEmpty())
            zbiórBezPrzyszłychOdwołań.add(numerStrony);
        else
            mapaOdwołanieDoStrony.put(pom.getFirst(), numerStrony);
        
        mapaOdwołanieDoStrony.remove(obecne);
    }

}


