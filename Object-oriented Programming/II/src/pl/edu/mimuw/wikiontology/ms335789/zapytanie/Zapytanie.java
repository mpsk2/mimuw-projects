package pl.edu.mimuw.wikiontology.ms335789.zapytanie;

import java.util.*;

import pl.edu.mimuw.wikiontology.ms335789.osoba.Kategoria;

abstract public class Zapytanie { //zapytanie to zapytanie, 3 teksty oddzielone spacjami pobrane z jakiegoś wejścia w kolejności filtr źródło cel
	final protected String filtr;
	final private String zrodlo;
	final private String cel;
	
	protected Zapytanie(String filtr, String zrodlo, String cel){
		this.filtr = filtr;
		this.zrodlo = zrodlo;
		this.cel = cel;
	}
	
	public String getFiltr(){
		return filtr;
	}
	
	public String getZrodlo(){
		return zrodlo;
	}
	
	public String getCel(){
		return cel;
	}
	
	abstract public boolean czyDobryFiltr(Collection<Kategoria> filtry);
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append(filtr);
		sb.append(" ");
		sb.append(zrodlo);
		sb.append(" ");
		sb.append(cel);
		sb.append(" ");
		
		return sb.toString();
	}

}
