package pl.edu.mimuw.wikiontology.ms335789.graf;

import java.util.*;

import pl.edu.mimuw.wikiontology.ms335789.osoba.Osoba;

public class ElementKolejki {
	final private Osoba osoba; //obecna osoba
	final private List<Osoba> doTejPory; //ścieżka do tej pory
	final private int wysokosc; //odległść osoby od źródła
	
	public ElementKolejki(Osoba osoba, List<Osoba> doTejPory, int wysokosc){
		this.osoba = osoba;
		this.doTejPory = new LinkedList<Osoba>(doTejPory);
		this.wysokosc = wysokosc;
		
		this.doTejPory.add(osoba);
	}
	
	public int getWysokosc(){
		return wysokosc;
	}
	
	
	public Osoba getOsoba(){
		return osoba;
	}
	
	public List<Osoba> getDoTejPory(){
		return doTejPory;
	}
	
	public String toString(){ //wypisuje ścieżkę między dwoma osobami i jej długość
		StringBuffer sb = new StringBuffer();
		
		sb.append("***\n");
		sb.append("Path length: ");
		sb.append(wysokosc);
		sb.append("\n");
		
		for(Osoba o : doTejPory){
			sb.append(o.getNazwa());
			sb.append("\n");
		}
		
		sb.append("***");
		
		return sb.toString();
	}
	
}

