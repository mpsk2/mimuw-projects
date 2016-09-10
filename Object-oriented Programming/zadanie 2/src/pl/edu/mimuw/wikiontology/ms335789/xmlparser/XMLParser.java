package pl.edu.mimuw.wikiontology.ms335789.xmlparser;

import java.util.*;

import pl.edu.mimuw.wikiontology.ms335789.osoba.SzablonOsoba;

public interface XMLParser {
	Collection<SzablonOsoba> zparsuj(String sciezka);
}
