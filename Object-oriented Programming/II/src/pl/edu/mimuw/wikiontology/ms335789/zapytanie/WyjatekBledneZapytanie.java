package pl.edu.mimuw.wikiontology.ms335789.zapytanie;

public class WyjatekBledneZapytanie extends Exception { //jeżeli zapytanie jest błędne z jakiegoś powodu
	final private String tresc;
	
	public WyjatekBledneZapytanie(){
		super();
		this.tresc = "";
	}

	public WyjatekBledneZapytanie(String tresc){
		super();
		this.tresc = ": " + tresc;
	}
	
	public String toString(){
		return "Wyjątek: Błędne zapytanie" + tresc;
	}
}
