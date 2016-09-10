package pl.edu.mimuw.wikiontology.ms335789.xmlparser;

public class Artykul { //do wyciągania z XML
	private String tytul;
	private String tresc;
	
	public Artykul(){}
	
	public Artykul(String tytul, String tresc){
		this.tytul = tytul;
		this.tresc = tresc;
	}
	
	public String getTytul(){
		return tytul;
	}
	
	public String getTresc(){
		return tresc;
	}
	
	public void setTytul(String tytul){
		this.tytul = tytul;
	}
	
	public void setTresc(String tresc){
		this.tresc = tresc;
	}
}
