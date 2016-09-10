package pl.edu.mimuw.wikiontology.ms335789.osoba;


public class Odnosnik implements Comparable<Odnosnik>{ //odnośniki do osób - inaczej krawędzie
	private Osoba osoba;
	private String nazwa;
	
	public int compareTo(Odnosnik drugi){
		if (drugi == null)
			return 1;
		return drugi.getNazwa().compareTo(getNazwa());
	}
	
	public Odnosnik(Osoba osoba){
		this.osoba = osoba;
	}
	
	public Odnosnik(String nazwa){
		this.nazwa = nazwa;
	}
	
	public Osoba getOsoba(){
		return osoba;
	}
	
	public String getNazwa(){
		if (osoba == null)
			return nazwa;
		else
			return osoba.getNazwa();
	}
	
	static public Odnosnik liniaProdukcyjna(String tresc){
		if(tresc.contains("|")){
			int i = tresc.indexOf('|');
			String a = tresc.substring(0, i);
			return new Odnosnik(a);
		} else {
			return new Odnosnik(tresc);
		}
	}
	
	public String toString(){
		if (osoba == null)
			return nazwa;
		else
			return osoba.toString();
	}
}
