package pl.edu.mimuw.wikiontology.ms335789.zapytanie;

import java.util.Scanner;

public class ZapytanieFactory { //pobiera zapytania ze scannera. zapytanie musi się składać z 3 tekstów i kończyć znakiem nowej linii
	
	static public Zapytanie pobierz(Scanner sc) throws WyjatekBledneZapytanie{
		
		String s1, s2, s3;
		
		if(sc.hasNext())
			s1 = sc.next().toLowerCase().replace("_", " ");
		else
			throw new WyjatekBledneZapytanie("Brak filtra.");
		
		if(sc.hasNext())
			s2 = sc.next().toLowerCase().replace("_", " ");
		else
			throw new WyjatekBledneZapytanie("Brak pierwszej osoby.");
		
		if(sc.hasNext())
			s3 = sc.next().toLowerCase().replace("_", " ");
		else
			throw new WyjatekBledneZapytanie("Brak drugiej osoby.");
			
		if(s1.compareTo("all") == 0)
			return new ZapytanieAll(s1, s2, s3);
		else{	
			if(!sc.hasNextLine())
				throw new WyjatekBledneZapytanie("Brak końca linii na końcu zapytania.");
			return new ZapytanieNieAll(s1, s2, s3);
		}	
	}
}
