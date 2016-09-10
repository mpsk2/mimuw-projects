package wymiana;

/**
 * @author ms335789
 *
 */

abstract class Strategia {
	/**
	 * 
	 * @param s = nazwa strategi
	 * @param tab = tablica ze stronami do obsłużenia
	 * @param rozmiar = ilość stron, które możemy przetrzymywać
	 * @return Obiekt dziedziczący po klasie strategia, który wykona się, wg. zadanej strategi
	 */
    static Strategia fabrykaStrategia(String s, int[] tab, int rozmiar){
        switch(s){
            case "OPT":
                return new StrategiaOPT(tab, rozmiar);
            case "FIFO":
                return new StrategiaFIFO(tab, rozmiar);
            case "LRU":
                return new StrategiaLRU(tab, rozmiar);
            default:
                return new StrategiaDrugiejSzansy(tab, rozmiar);
        }
    }
    
    protected final int rozmiar;
    protected final int[] tab;
    protected int liczbaBłędów;
    
    Strategia(int[] tab, int rozmiar){
        this.rozmiar = rozmiar;
        this.tab = tab;
    }
    
    abstract protected boolean czyJestMiejsce();
    
    /**
     * Sprawdza czy strona znajduje się w pamięci
     * @param numerStrony, ta strona jest sprawdzana
     * @return true jeżeli strona jest w pamięci, false jeżeli jej tam nie ma
     */
    abstract protected boolean czyZnajdujeSieWPamieci(int numerStrony);
    
    /**
     * Zwalnia miejsce w pamięci
     * @return numer wyrzuconej z pamięci strony
     */
    abstract protected int zwolnijMiejsce();
    
    /**
     * Dodaje stronę o numerzeStrony do pamięci
     * @param numerStrony
     */
    abstract protected void dodajStronę(int numerStrony);
    
    /**
     * Jeżeli dana strategia potrzebuje przy odwołaniu się do strony będącej w pamięci coś z nią zrobić, to jest to robione
     * @param numerStrony
     */
    protected void odświeżStatusStrony(int numerStrony) {
        //Nic
    }
    
    /**
     * Dokonuje obsługi pojedyńczej strony
     * @param numerStrony
     */
    protected void obsłużStronę(int numerStrony){
        if(czyZnajdujeSieWPamieci(numerStrony))
            odświeżStatusStrony(numerStrony);
        else {
            liczbaBłędów++;
            System.out.print("Błąd: ");
            if(czyJestMiejsce()){
                dodajStronę(numerStrony);
                System.out.println("0 --> " + numerStrony);
            } else {
                int usunietaStrona = zwolnijMiejsce();
                dodajStronę(numerStrony);
                System.out.println(usunietaStrona + " --> " + numerStrony);
            }
        }
        
    }
    
    /**
     * Dokonuje symulacji urzywania przez program danych stron
     * @return liczba błędów potrzeby wymiany strony w pamięci
     */
    int wykonaj() {
        for(int numerStrony : tab)
            obsłużStronę(numerStrony);
        
        return błędy();
    }
    
    /**
     * Zwraca liczbę błędów i zeruje licznik błędów
     * @return liczba błędów podczas jednego wykonania wykonaj
     */
    protected int błędy(){
        int pom = liczbaBłędów;
        liczbaBłędów = 0;
        return pom;
    }
}


