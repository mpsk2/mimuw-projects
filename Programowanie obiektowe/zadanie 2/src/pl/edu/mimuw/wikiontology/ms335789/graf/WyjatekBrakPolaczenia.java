package pl.edu.mimuw.wikiontology.ms335789.graf;

import pl.edu.mimuw.wikiontology.ms335789.zapytanie.Zapytanie;

public class WyjatekBrakPolaczenia extends Exception { //jest rzucany jak nie ma połączenia między 2 osobami o danym filtrze
	final String text;
	
	public WyjatekBrakPolaczenia(){
		super();
		this.text = "Wyjątek brak połączenia";
	}
	
	public WyjatekBrakPolaczenia(Zapytanie zap){
		super();
		this.text = "Wyjątek brak połączenia obsługującego zapytanie: " + zap.toString();
	}
	
	public String toString(){
		return text;
	}
}
