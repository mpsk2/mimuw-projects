package pl.edu.mimuw.wikiontology.ms335789.xmlparser;

import java.util.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import pl.edu.mimuw.wikiontology.ms335789.osoba.SzablonOsoba;
import pl.edu.mimuw.wikiontology.ms335789.osoba.SzablonOsobaFactory;

public class XMLParserSAX implements XMLParser { //wczytuje plik xml po kawałku i 
	//zmienia jego kawałki na artykuły, a potem uznaje czy to osoba czy nie i jak tak tworz z tego szablon osoby

	private class newHandler extends DefaultHandler{		
		private Collection<SzablonOsoba> wynik = new LinkedList<SzablonOsoba>();
		
		private Artykul pom;
		
		private boolean bText = false;
		private boolean bTitle = false;
		
		private SzablonOsoba so;
		private StringBuffer bf;
		
		public void characters(char[] buffer, int start, int length){
			if(bText){
				bf.append(new String(buffer, start, length));
			} else if(bTitle){
				pom.setTytul(new String(buffer, start, length));
				bTitle = false;
			}
		}
		
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if(qName.equalsIgnoreCase("page")){
				pom = new Artykul();
			} else if(qName.equalsIgnoreCase("title")){
				bTitle = true;
			} else if(qName.equalsIgnoreCase("text")){
				bf = new StringBuffer();
				bText = true;
			}
		}
		
		public void endElement(String uri, String localName, String qName) throws SAXException{
			if(qName.equalsIgnoreCase("page")){
				so = SzablonOsobaFactory.szablon(pom);
				if(so != null)
					wynik.add(so);
			}else if(qName.equalsIgnoreCase("text")){
				pom.setTresc(bf.toString());
				bText = false;
			}
		}
		
		Collection<SzablonOsoba> wynik(){
			return wynik;
		}
	}
	
	@Override
	public Collection<SzablonOsoba> zparsuj(String sciezka) {
		
		try{
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			
			newHandler handler = new newHandler();
			
			saxParser.parse(sciezka, handler);
			
			return handler.wynik();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{}
		
		return null;
	}

}

