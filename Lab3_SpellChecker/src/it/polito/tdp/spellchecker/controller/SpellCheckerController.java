package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {
	
	private Dictionary model;
	
	ArrayList <String> testoInput = new ArrayList <String>();
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label txtTempo;

    @FXML
    private ComboBox<String> cmbLingua;

    @FXML
    private TextArea txtDaTradurre;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextArea txtWrongWords;

    @FXML
    private Label txtResult;

    @FXML
    private Button btnClear;

    @FXML
    void doClearText(ActionEvent event) {
    	txtDaTradurre.clear();
    	txtWrongWords.clear();
    	txtTempo.setVisible(false);
    	txtResult.setVisible(false);
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	//Inizializzazione
    	//Rendi l'interfaccia visibile (in SceneBuilder erano settati non visibili)
    	txtTempo.setVisible(true);
    	txtResult.setVisible(true);
    	testoInput.clear();
    	
    	
    	//Gestisco la selezione della lingua
    	model.loadDictionary(cmbLingua.getValue());
    	
    	//Prendo il testo da corregere
    	String testo = txtDaTradurre.getText();
    	if( testo.isEmpty()){
    		return;
    	}
    	
    	//Divido il testo usando gli spazi e elimino la punteggiatura
    	String array [] = testo.split(" ");
    	for(int i = 0; i< array.length; i++){
    		testoInput.add(array[i].replaceAll("[ \\p{Punct}]", "").trim().toLowerCase());
    	}
    	
    	//Chiamo la funzione per la correzione del testo
    	long t1 = System.nanoTime();
    	ArrayList <RichWord> listaParole = model.spellCheckText(testoInput);
    	long t2 = System.nanoTime();
    	
    	
    	//Stampo l'analisi
    	ArrayList <String> paroleErrate = new ArrayList <String>();
    	for(int i = 0; i<listaParole.size(); i++ ){
    		if(listaParole.get(i).isCorretta() == false){
    			String parola = listaParole.get(i).getParola();
    			paroleErrate.add(parola);   
    		}
    	}
    	String elenco = "";
    	for(String s : paroleErrate){
    		elenco = elenco + s + "\n"; }
    	txtWrongWords.setText(elenco);
    	
    	//Risultato: numero di errori
    	int errori = paroleErrate.size();
    	txtResult.setText("The text contains " + errori + " errors");
    	
    	//Stampo tempo di completamento 
    	txtTempo.setText("Spell check completed in " + (t2 - t1) / 1e9 + " secondi");
    
    }
   
    @FXML
    void initialize() {
        assert txtTempo != null : "fx:id=\"txtTempo\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert cmbLingua != null : "fx:id=\"cmbLingua\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtDaTradurre != null : "fx:id=\"txtDaTradurre\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtWrongWords != null : "fx:id=\"txtWrongWords\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        
        cmbLingua.getItems().addAll("English", "Italian");
        if(cmbLingua.getItems().size() >0){
            cmbLingua.setValue(cmbLingua.getItems().get(0));  
        }
        
    }
    
    public void setModel(Dictionary m){
    	this.model= m;
    }
}

