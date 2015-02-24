package pl.edu.mimuw.wikiontology.ms335789;

import java.util.*;

import pl.edu.mimuw.wikiontology.ms335789.graf.Graf;
import pl.edu.mimuw.wikiontology.ms335789.graf.GrafFactory;
import pl.edu.mimuw.wikiontology.ms335789.osoba.SzablonOsoba;
import pl.edu.mimuw.wikiontology.ms335789.xmlparser.XMLParser;
import pl.edu.mimuw.wikiontology.ms335789.xmlparser.XMLParserDOM;
import pl.edu.mimuw.wikiontology.ms335789.xmlparser.XMLParserSAX;
import pl.edu.mimuw.wikiontology.ms335789.zapytanie.Zapytanie;
import pl.edu.mimuw.wikiontology.ms335789.zapytanie.ZapytanieFactory;

public class WikiImporter {

	public static void main(String[] args) { //jako pierwszy argument bierze odnośnik do pliku xml
		WikiImporter w = new WikiImporter();
		
		Scanner sc = new Scanner(System.in);
		
		Graf graf = w.importujSAX(args[0]); //pobiera nasz graf z pliku
		
		System.out.println("Wczytano " + graf.liczbaOsob() + " encji."); //wypisuje ile jest encji
		
		try{
			while(sc.hasNext()){
				try{ //obsługuje zapytania
					Zapytanie z = ZapytanieFactory.pobierz(sc);
					System.out.println(graf.obslozZapytanie(z).toString());
				}catch(Exception e){ //jeżeli złe zapytanie lub błąd wewnątrz grafu
					System.err.println(e.toString());
				}finally{}
			}
		} catch(Exception e){
			System.err.println(e.toString());
		} finally{
		}
		
		sc.close();

	}
	
	private Graf importuj(XMLParser parser, String sciezka){ //importuje graf z pliku
		Collection<SzablonOsoba> so = parser.zparsuj(sciezka);
		
		
		return GrafFactory.liniaProdukcyjna(so);
	}
	
	public Graf importujDOM(String sciezka){ 
		return importuj(new XMLParserDOM(), sciezka);
	}
	
	public Graf importujSAX(String sciezka){
		return importuj(new XMLParserSAX(), sciezka);
	}

}
