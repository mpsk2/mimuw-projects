package pl.edu.mimuw.wikiontology.ms335789.osoba;

import java.util.*;

public class Osoba implements Comparable<Osoba> {
	
	final private String nazwa;
	final private Collection<Kategoria> kategorie;
	private Collection<Odnosnik> odnosniki;
	
	public int compareTo(Osoba druga){
		if(druga == null)
			return 1;
		return nazwa.toLowerCase().replace("_", " ").compareTo(druga.nazwa.toLowerCase().replace("_", " "));
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append(nazwa);
		sb.append(" ");
		sb.append(kategorie.size());
		sb.append(" ");
		sb.append(odnosniki.size());
		
		return sb.toString();
	}
	
	public Osoba(String nazwa, Collection<Kategoria> kategorie, Collection<Odnosnik> odnosniki){
		this.nazwa = nazwa;
		this.kategorie = kategorie;
		this.odnosniki = odnosniki;
	}
	
	public String getNazwa(){
		return nazwa;
	}
	
	public void zkompresujeOdnosniki(Map<String, Osoba> mapa){ //zmienia odnośniki stringowe na odnośnik osobowe
		Collection<Odnosnik> pom = new LinkedList<Odnosnik>();
		try{
			for(Odnosnik o : odnosniki){
				String nz = o.getNazwa().toLowerCase().replace("_", " ");
				if (mapa.containsKey(nz)){
					pom.add(new Odnosnik(mapa.get(nz)));
				}
			}
			odnosniki = pom;
		} catch(Exception e){
			System.err.println(e.toString());
		}finally {
		}
			
	}
	
	public boolean czyJestKategorii(Kategoria kat){
		return kategorie.contains(kat);
	}
	
	public Collection<Kategoria> getKategorie(){
		return kategorie;
	}
	
	public Collection<Odnosnik> getOdnosniki(){
		return odnosniki;
	}
	
	
	
}

