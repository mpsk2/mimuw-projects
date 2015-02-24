package pl.edu.mimuw.wikiontology.ms335789.osoba;

import java.util.*;

public class SzablonOsoba { //to to co jest wyciągnięte z pliku xml - nazwa, odnośniki i kategorie
	final private String nazwa;
	final private Collection<Kategoria> kategorie;
	final private Collection<Odnosnik> odnosniki;
	
	SzablonOsoba(String nazwa, Collection<Kategoria> kategorie, Collection<Odnosnik> odnosniki){
		this.nazwa = nazwa;
		this.kategorie = kategorie;
		this.odnosniki = odnosniki;
	}
	
	public String toString(){
		StringBuffer bf = new StringBuffer();
		
		bf.append(nazwa);
		bf.append("\n");
		bf.append(kategorie.toString());
		bf.append("\n");
		bf.append(odnosniki.toString());
		bf.append("\n\n");
		
		return bf.toString();
	}
	
	public Osoba toOsoba(){
		return new Osoba(nazwa, kategorie, odnosniki);
	}
	

}
