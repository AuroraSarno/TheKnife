package ServerTK.RMI;

import ServerTK.Modelli.Ristorante;

import ServerTK.Modelli.Citta;
import ServerTK.Modelli.Utente;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.rmi.RemoteException;
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
}
