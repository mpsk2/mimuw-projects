package pl.edu.mimuw.wikiontology.ms335789.osoba;


import java.util.*;
import java.util.regex.*;

import pl.edu.mimuw.wikiontology.ms335789.xmlparser.Artykul;

public class SzablonOsobaFactory { //robi szaplony, sprawdza czy artykuł jest osobowy

	public static SzablonOsoba szablon(String tytul, String tresc){
		Collection<Odnosnik> odnosniki;
		Collection<Kategoria> kategorie = wyciagnijKategorie(tresc);
		
		if (czyJestPersondate(tresc) || czyOsoboweKategorie(kategorie)){
			odnosniki = wyciagnijOdnosniki(tresc);
			return new SzablonOsoba(tytul, kategorie, odnosniki);
		}
		return null;
	}
	
	public static SzablonOsoba szablon(Artykul art){
		return szablon(art.getTytul(), art.getTresc());
	}
	
	static private boolean czyJestPersondate(String tresc){
		return tresc.contains("Persondata");
	}
	
	static private Collection<Kategoria> wyciagnijKategorie(String tresc){
		Pattern pattern = Pattern.compile("\\[\\[Category:([^\\]]*)\\]\\]");
		Matcher matcher = pattern.matcher(tresc);
		
		Set<Kategoria> kategorie = new HashSet<Kategoria>();
		
		while(matcher.find())
			kategorie.add(new Kategoria(matcher.group(1).toLowerCase().replace("_", " ")));
		
		return kategorie;
	}
	
	static private Collection<Odnosnik> wyciagnijOdnosniki(String tresc){
		Pattern pattern = Pattern.compile("\\[\\[([^\\]]*)\\]\\]");
		Matcher matcher = pattern.matcher(tresc);
		
		Collection<Odnosnik> pom = new LinkedList<Odnosnik>();
		
		while(matcher.find()){
			String g = matcher.group(1);
			Odnosnik p = Odnosnik.liniaProdukcyjna(g);
			if (p != null)
				pom.add(p);
		}
		
		return pom;
	}
	
	static private boolean czyOsoboweKategorie(Collection<Kategoria> kategorie){
		for(Kategoria k : kategorie)
			if (k.czyOsobowa())
				return true;
		return false;
	}
	
	
}
