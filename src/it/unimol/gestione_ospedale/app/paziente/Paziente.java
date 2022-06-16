package it.unimol.gestione_ospedale.app.paziente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa uno Paziente
 * @author Francesco Stasik Mancini
 */
public class Paziente implements Serializable {

    private String nome;

    private String cognome;

    private String eta;

    private String codiceFiscale;

    private List<String> patologie;

    public Paziente(){

    }

    public Paziente(String nome, String cognome, String eta, String codiceFiscale){
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.codiceFiscale = codiceFiscale;
        this.patologie = new ArrayList<>();
    }

    public Paziente(String nome, String cognome, String eta, String codiceFiscale, List<String> patologie) {
    }

    public int confrontaPerCognomeENome(Paziente altro){
        int confronto = this.cognome.compareTo(altro.cognome);
        if (confronto == 0){
            return this.nome.compareTo(altro.nome);
        } else {
            return confronto;
        }
    }

    public String getNome(){return nome;}

    public String getCognome(){return cognome;}

    public String getEta(){return eta;}

    public String getCodiceFiscale(){return codiceFiscale;}

    public List<String> getPatologie(){return patologie;}
}
