package it.unimol.gestione_ospedale.ui;

import it.unimol.gestione_ospedale.app.eccezioni.PatologiaNonPresenteException;
import it.unimol.gestione_ospedale.app.paziente.GestorePazienti;
import it.unimol.gestione_ospedale.app.paziente.Paziente;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TerminalUI {

    private static TerminalUI istanza = new TerminalUI();

    private TerminalUI(){
    }

    public static TerminalUI getIstanza() {
        return istanza;
    }


    public void esegui() {
        boolean uscita;
        do {
            uscita = this.stampaMenu();
        } while (!uscita);
    }

    private boolean stampaMenu() {
        String INSERISCI_PAZIENTE = "1 - inserisci nuovo paziente\n";
        String VISUALIZZA_PAZIENTI = "2 - vidualizza pazienti\n";
        String FILTRA_PAZIENTI = "3 - filtra pazienti\n";
        String USCITA = "0 - Esci dal programma\n";

        Scanner scanner = new Scanner(System.in);
        String scelta;

        System.out.println(INSERISCI_PAZIENTE +
                VISUALIZZA_PAZIENTI +
                FILTRA_PAZIENTI +
                USCITA);

        System.out.print(">>>");
        scelta = scanner.nextLine();

        switch (scelta) {
            case "0":
                System.out.println("arrivederci!");
                return true;
            case "1":
                this.inserisciNuovoPaziente();
                return false;
            case "2":
                //this.visualizzaPazienti();
                return false;
            case "3":
                this.filtraPazienti();
                return false;
            default:
                return false;
        }

    }

    private void inserisciNuovoPaziente(){
        Paziente paziente = leggiPaziente();
        try {
            GestorePazienti.getIstanza().inserisciPaziente(paziente);
            System.out.println("Paziente inserito con successo (Codice Fiscale: " + paziente.getCodiceFiscale() + ")\n");
        }catch (IOException e){
            System.out.println("ERRORE - Impossibile Inserire Paziente\n");
        }
    }

    private Paziente leggiPaziente(){
        Scanner scanner = new Scanner(System.in);
        String nome;
        String cognome;
        String eta;
        String codiceFiscale;
        List<String> patologie;

        System.out.println("\nInserisci i dati del paziente");
        System.out.print("nome: ");
        nome = scanner.nextLine();
        System.out.print("cognome: ");
        cognome = scanner.nextLine();
        System.out.print("Eta: ");
        eta = scanner.nextLine();
        System.out.print("codice fiscale: ");
        codiceFiscale = scanner.nextLine();
        System.out.print("Patologie");
        patologie= Collections.singletonList(scanner.nextLine());

        return new Paziente(nome, cognome, eta, codiceFiscale, patologie);
    }

    private Paziente filtraPazienti(){
        Scanner scanner = new Scanner(System.in);
        boolean inputErrato;
        Paziente paziente = null;
        String patologia;

        System.out.println("Inserisci la patologia da cercare");
        do {
            patologia = scanner.nextLine();
            try {
                paziente = GestorePazienti.getIstanza().filtraPazientiPerPatologia(patologia);
                inputErrato = false;
            }catch (PatologiaNonPresenteException e){
                System.out.println("Patologia non Presente! Inserisci una patologia:");
                inputErrato = true;
            }
        }while (inputErrato);
        return paziente;
    }

}
