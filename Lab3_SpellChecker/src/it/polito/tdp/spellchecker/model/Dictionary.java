package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {
	
	private ArrayList <String> dizionario = new ArrayList <String>();
	
	public void loadDictionary(String language){
		try{
			FileReader fr = null;
			if(language.compareTo("english")==0){
				fr = new FileReader("rsc/English.txt");
			}
			if(language.compareTo("italiano")==0){
				fr = new FileReader("rsc/Italian.txt");
			}
			BufferedReader br = new BufferedReader (fr);
			String word;
			while((word = br.readLine())!= null){
				 dizionario.add(word.toLowerCase().trim()); 
			}
			br.close();
		}catch(IOException e){
			System.out.println("Errore nella lettura del file");
		}
			
	}
	
	public ArrayList<RichWord> spellCheckText (ArrayList <String> inputTextList){
		ArrayList <RichWord> listaParole = new ArrayList<RichWord>();
		for(String s : inputTextList){
			if(dizionario.contains(s)){
				RichWord word = new RichWord(s, true);
				listaParole.add(word);
			}
			else{
				RichWord word = new RichWord(s, false);
				listaParole.add(word);
			}
		}
	
		return listaParole;
		
	}

}
