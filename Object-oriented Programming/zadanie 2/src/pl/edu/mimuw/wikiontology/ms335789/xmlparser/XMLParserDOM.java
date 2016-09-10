package pl.edu.mimuw.wikiontology.ms335789.xmlparser;


import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import pl.edu.mimuw.wikiontology.ms335789.osoba.SzablonOsoba;
import pl.edu.mimuw.wikiontology.ms335789.osoba.SzablonOsobaFactory;

import java.io.File;

public class XMLParserDOM implements XMLParser { //wyciąga artykuły z pliku xml i zmienia je na artykuły - raczej niezbyt dobre, bo wczytuje cały plik do pamięci

	@Override
	public Collection<SzablonOsoba> zparsuj(String sciezka) {
		Collection<SzablonOsoba> wynik = new HashSet<SzablonOsoba>();
		try{
		
			File plik = new File(sciezka);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(plik);
			
			doc.getDocumentElement().normalize();
		
			
			NodeList nList = doc.getElementsByTagName("page");
			
			Artykul pom;
			
			for (int i = 0; i < nList.getLength(); i++){
				Node nNode = nList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					Element eElement = (Element) nNode;
					pom = new Artykul(eElement.getElementsByTagName("title").item(0).getTextContent(),eElement.getElementsByTagName("text").item(0).getTextContent());
					
					SzablonOsoba pom2 = SzablonOsobaFactory.szablon(pom);
					if (pom2 != null)
						wynik.add(pom2);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{}
		
		return wynik;
	}

}

