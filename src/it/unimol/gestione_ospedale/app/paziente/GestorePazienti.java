package it.unimol.gestione_ospedale.app.paziente;

import it.unimol.gestione_ospedale.app.eccezioni.PatologiaNonPresenteException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorePazienti {

    private static GestorePazienti istanza = new GestorePazienti();

    private final String NOME_FILE = "Lista_Pazienti.serr";

    private List<Paziente> pazientiInseriti;

    private GestorePazienti() {
        this.pazientiInseriti = new ArrayList<>();
        this.caricaDaFile();
    }

    public static GestorePazienti getIstanza() {
        return istanza;
    }

    public void inserisciPaziente(Paziente paziente) throws IOException {
        assert paziente != null;

        this.pazientiInseriti.add(paziente);
        try {
            this.salvaSuFile();
        } catch (IOException e) {
            this.pazientiInseriti.remove(this.pazientiInseriti.size()- 1);
            throw e;
        }
    }

    public Paziente filtraPazientiPerPatologia(String patologia) throws PatologiaNonPresenteException {
         for (int i = 0; i < pazientiInseriti.size(); i++){
             if (pazientiInseriti.get(i).getPatologie().equals(patologia)){
                 return pazientiInseriti.get(i);
             }
         } throw  new PatologiaNonPresenteException();


    }

    public void ordinapazienti() {
        this.pazientiInseriti.sort((s1, s2) -> s1.confrontaPerCognomeENome(s2));
    }

    private void salvaSuFile() throws IOException {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(this.NOME_FILE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.pazientiInseriti);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (objectOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    private void caricaDaFile() {
        File file = new File(this.NOME_FILE);
        if (file.exists()) {
            List<Paziente> pazientiCaricati = null;
            FileInputStream fileInputStream = null;
            ObjectInputStream objectInputStream = null;

            try {
                fileInputStream = new FileInputStream(this.NOME_FILE);
                objectInputStream = new ObjectInputStream(fileInputStream);

                pazientiCaricati = (List<Paziente>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException ignore) {
            } finally {
                try {
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                } catch (IOException ignore) {
                }
            }
            if (pazientiCaricati != null) {
                this.pazientiInseriti = new ArrayList<>(pazientiCaricati);
            }
        }
    }
}
