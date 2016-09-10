package pl.edu.mimuw.wikiontology.ms335789.zapytanie;

import java.util.*;

import pl.edu.mimuw.wikiontology.ms335789.osoba.Kategoria;

public class ZapytanieNieAll extends Zapytanie { //zapytanie z filtrem nie all

	public ZapytanieNieAll(String filtr, String zrodlo, String cel) {
		super(filtr, zrodlo, cel);
	}
	
	@Override
	public boolean czyDobryFiltr(Collection<Kategoria> filtry) {
		for(Kategoria k : filtry)
			if(k.getTresc().contains(filtr))
				return true;
		return false;
	}

}
