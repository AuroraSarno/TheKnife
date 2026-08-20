package ServerTK.RMI;

import ServerTK.Gestori.*;
import ServerTK.Modelli.Citta;
import ServerTK.Modelli.Utente;
import ServerTK.Modelli.Ristorante;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Date;
import java.util.List;

public class TheKnifeImpl extends UnicastRemoteObject implements TheKnifeInterface {
    private static final long serialVersionUID = 1L;
    private GestoreCitta gestoreCitta;
    private GestorePreferiti gestorePreferiti;
    private GestoreRecensione gestoreRecensione;
    private GestoreRistorante gestoreRistorante;
    private GestoreUtenti gestoreUtenti;
    public TheKnifeImpl(GestoreCitta gestoreCitta, GestorePreferiti gestorePreferiti, GestoreRecensione gestoreRecensione,GestoreRistorante gestoreRistorante, GestoreUtenti gestoreUtenti) throws RemoteException{
        super();
        this.gestoreCitta = gestoreCitta;
        this.gestorePreferiti = gestorePreferiti;
        this.gestoreRecensione = gestoreRecensione;
        this.gestoreRistorante = gestoreRistorante;
        this.gestoreUtenti = gestoreUtenti;
    }

    public List<Ristorante> cercaRistoranti(String nome, String tipoCucina, Double prezzoMin, Double prezzoMax,
                                            Boolean delivery, Boolean prenotazione, Double stelle, Integer idCitta) throws RemoteException{
        return gestoreRistorante.cercaRistoranti(nome, tipoCucina, prezzoMin, prezzoMax, delivery, prenotazione, stelle, idCitta);
    }

    public boolean aggiungiRistorante(Ristorante r) throws RemoteException{
        return gestoreRistorante.aggiungiRistorante(r);
    }

    public List<Ristorante> getRistoranteDi(String usernameProprietario) throws RemoteException{
        return gestoreRistorante.getRistoranteDi(usernameProprietario);
    }

    public List<String> getTipiCucinaLista() throws RemoteException{
        return gestoreRistorante.getTipiCucinaLista();
    }

    public boolean registrazione(String username, String nome, String cognome, String password, String domicilio, Date data, Utente.Ruolo ruolo) throws RemoteException {
        return gestoreUtenti.registrazione(username, nome, cognome, password, domicilio, data, ruolo);
    }
    public Utente login(String username, String password) throws RemoteException {
        return gestoreUtenti.login(username, password);
    }
    public List<Citta> listaCittaPerNazione(String nazione) throws RemoteException {
        return gestoreCitta.listaCittaPerNazione(nazione);
    }
    public List<String> listaNazione() throws RemoteException {
        return gestoreCitta.listaNazione();
    }
}

