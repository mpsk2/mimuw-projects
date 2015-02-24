package pl.edu.mimuw.wikiontology.ms335789.graf;

import java.util.*;

import pl.edu.mimuw.wikiontology.ms335789.osoba.SzablonOsoba;

public class GrafFactory {
	static public Graf liniaProdukcyjna(Collection<SzablonOsoba> szablony){ //robi graf z kolekcji szablonoosób
		Graf wynik = new Graf();
		
		for(SzablonOsoba s : szablony)
			if (s != null)
				wynik.addOsoba(s.toOsoba());
		
		wynik.zkompresujOdnosniki();
		
		return wynik;
	}

}
