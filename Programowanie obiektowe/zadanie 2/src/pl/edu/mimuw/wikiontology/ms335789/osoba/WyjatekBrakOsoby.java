package pl.edu.mimuw.wikiontology.ms335789.osoba;

public class WyjatekBrakOsoby extends Exception {
	final private String text;
	
	public WyjatekBrakOsoby(){
		this("");
	}
	
	public WyjatekBrakOsoby(String text){
		super();
		this.text = "Wyjątek brak osoby. " + text; 
	}
	
	public String toString(){
		return text;
	}

}
