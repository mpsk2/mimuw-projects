package pl.edu.mimuw.wikiontology.ms335789.zapytanie;

import java.util.*;

import pl.edu.mimuw.wikiontology.ms335789.osoba.Kategoria;

public class ZapytanieAll extends Zapytanie { //zapytanie dla filtra all
	
	public ZapytanieAll(String filtr, String zrodlo, String cel){
		super(filtr, zrodlo, cel);
	}
	
	@Override
	public boolean czyDobryFiltr(Collection<Kategoria> filtry) {
		return true;
	}

}
