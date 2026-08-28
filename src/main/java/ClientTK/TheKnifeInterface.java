package ClientTK;

import ServerTK.Modelli.Citta;
import ServerTK.Modelli.Recensione;
import ServerTK.Modelli.Ristorante;
import ServerTK.Modelli.Utente;
import eccezioni.ListaVuotaException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface TheKnifeInterface extends Remote {


    public List<Ristorante> cercaRistoranti(String nome, String tipoCucina, Double prezzoMin, Double prezzoMax,
                                            Boolean delivery, Boolean prenotazione, Double stelle, Integer idCitta) throws RemoteException;

    public boolean aggiungiRistorante(Ristorante r) throws RemoteException;

    public List<Ristorante> getRistoranteDi(String usernameProprietario) throws RemoteException;

    public List<String> getTipiCucinaLista() throws RemoteException;


    public boolean registrazione (String username, String nome, String cognome, String password, String domicilio, Date data, Utente.Ruolo ruolo) throws RemoteException;
    public Utente login(String username, String password) throws RemoteException;
    public List<Citta> listaCittaPerNazione(String nazione) throws RemoteException;
    public List<String> listaNazione() throws RemoteException;

    //METODI GESTORE PREFERITI
    public void aggiungiPreferiti(Utente utente, Ristorante ristorante) throws RemoteException, NullPointerException, IllegalArgumentException;

    public void cancellaPreferiti(Utente utente, Ristorante ristorante) throws RemoteException, NullPointerException, ListaVuotaException;

    public List<Ristorante> visualizzaPreferiti(Utente utente) throws RemoteException, ListaVuotaException;

    //METODI GESTORE RECENSIONE
    public List<Recensione> getRecensioni() throws RemoteException;
    public void modificaRecensione(Recensione rec, String testoMod, int stelleMod, Utente utente) throws RemoteException, IllegalArgumentException;
    public void rispondiRecensione(Recensione rec, String risposta) throws RemoteException, IllegalArgumentException;
    public void inserisciRecensione(Ristorante ris, int id_recensione, String testo, int stelle, String username) throws RemoteException, IllegalArgumentException;
    public void eliminaRecensione(Recensione rec, Utente utente) throws RemoteException, IllegalArgumentException;
    public void visualizzaRiepilogo(Ristorante ris, String testo, int stelle) throws IllegalArgumentException, RemoteException;
    public List<Recensione> visualizzaRecensioniperUtente(Utente u) throws IllegalArgumentException, RemoteException;
    public List<Recensione> visualizzaRecensioniPerRistoratore(Ristorante ris) throws IllegalArgumentException, RemoteException;
    public boolean haLasciatoRecensione(Utente u, Ristorante ris) throws IllegalArgumentException, RemoteException;
}
