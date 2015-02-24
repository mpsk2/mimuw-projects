package pl.edu.mimuw.wikiontology.ms335789.graf;

import java.util.*;

import pl.edu.mimuw.wikiontology.ms335789.osoba.Odnosnik;
import pl.edu.mimuw.wikiontology.ms335789.osoba.Osoba;
import pl.edu.mimuw.wikiontology.ms335789.osoba.WyjatekBrakOsoby;
import pl.edu.mimuw.wikiontology.ms335789.zapytanie.Zapytanie;

public class Graf {
	private Collection<Osoba> osoby; //zbiór osób
	private Map<String, Osoba> mapaSO; //mapa String - Osoba do rozpoznawaniu osoby po stringu
	
	Graf(){
		osoby = new HashSet<Osoba>();
		mapaSO = new HashMap<String, Osoba>();
	}
	
	public int liczbaOsob(){
		return osoby.size();
	}
	
	void addOsoba(Osoba o){
		osoby.add(o);
		mapaSO.put(o.getNazwa().toLowerCase().replace("_", " "), o);
	}
	
	void zkompresujOdnosniki(){
		for(Osoba o : osoby)
			o.zkompresujeOdnosniki(mapaSO); // zmienia w odnośnikach nazwy na osoby
	}
	
	public ElementKolejki obslozZapytanie(Zapytanie z) throws WyjatekBrakOsoby, WyjatekBrakPolaczenia{ //obsługuje zaptanie miedzy 2 osobami o dany filtrze
	
		//sprawdzam czy cel i źródło istnieją
		if(!mapaSO.containsKey(z.getCel()))
			throw new WyjatekBrakOsoby("Cel wyszukiwania.");
		if(!mapaSO.containsKey(z.getZrodlo()))
			throw new WyjatekBrakOsoby("Źródło wyszukiwania.");
			
		Osoba cel = mapaSO.get(z.getCel()); //wyciągam cem (cały czas będę się do niego porównywał
		
		Map<Osoba, Integer> mapaOI = new HashMap<Osoba, Integer>(); //mapa osoba - wysokość
		mapaOI.put(mapaSO.get(z.getZrodlo()),0);
		
		Queue<ElementKolejki> kolejka = new LinkedList<ElementKolejki>(); //kolejka FIFO
		kolejka.add(new ElementKolejki(mapaSO.get(z.getZrodlo()), new LinkedList<Osoba>(), 0));
		
		ElementKolejki pom = kolejka.peek();
		
		while(!kolejka.isEmpty()){
			pom = kolejka.poll();
				
			if (pom.getOsoba() == cel)
				return pom;
				
			for(Odnosnik o : pom.getOsoba().getOdnosniki()){
			
				if(z.czyDobryFiltr(o.getOsoba().getKategorie())){ //sprawdzam tylko kiedy jest dobre typu - wyjątek cel i źródło
					int obecnaWysokosc = pom.getWysokosc() + 1;
					if(mapaOI.containsKey(o.getOsoba())){ //jeżeli osoba już odwiedzona była
						if (mapaOI.get(o.getOsoba()) > obecnaWysokosc){ //i jeżeli odwiedzenie jej się nadal opłaca
							mapaOI.put(o.getOsoba(), obecnaWysokosc);
							kolejka.add(new ElementKolejki(o.getOsoba(), pom.getDoTejPory(), obecnaWysokosc));
						}
					} else { //jeżeli osoba jeszcze nieodwiedzona
						mapaOI.put(o.getOsoba(), obecnaWysokosc);
						kolejka.add(new ElementKolejki(o.getOsoba(), pom.getDoTejPory(), obecnaWysokosc));
					}
				}
			}
		}
		
		if(pom.getOsoba() != cel)
			throw new WyjatekBrakPolaczenia(z);
		return pom;
	}

}
