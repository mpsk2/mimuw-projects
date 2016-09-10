package pl.edu.mimuw.wikiontology.ms335789.osoba;

import java.util.*;

public class Kategoria implements Comparable<Kategoria>{ //kategoria
	
	final private String tresc;
	
	final static Collection<String> zPrefiksem = new LinkedList<String>(Arrays.asList("births", "deaths", "People")); //kryterium z prefiksem
	final static Collection<String> dowolne = new LinkedList<String>(Arrays.asList("physicians","mathematicians", "gamers", "theorists", "philosophers")); //kryterium dowolne
	
	public Kategoria(String tresc){
		this.tresc = tresc;
	}
	
	public boolean czyOsobowa(){
		for(String s : zPrefiksem){
			String[] st = tresc.split(" ");
			if(st.length > 1)
				if(st[st.length-1].contains(s))
					return true;
		}
		
		for(String s : dowolne)
			if(tresc.contains(s))
				return true;
				
		return false;
	}
	
	public String getTresc(){
		return tresc;
	}
	
	public int compareTo(Kategoria drugi){
		if(drugi == null)
			return 1;
		String t1 = drugi.tresc.toLowerCase().replace('_',' ');
		String t2 = tresc.toLowerCase().replace('_',' ');
		if (t1.contains(t2) || t2.contains(t1))
			return 0;
		return t1.compareTo(t2);
	}
	
	public String toString(){
		return "Kategoria: " + tresc;
	}

}
