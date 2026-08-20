package ServerTK.RMI;

import ServerTK.Modelli.Citta;
import ServerTK.Modelli.Utente;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface TheKnifeInterface extends Remote {
    public boolean registrazione (String username, String nome, String cognome, String password, String domicilio, Date data, Utente.Ruolo ruolo) throws RemoteException;
    public Utente login(String username, String password) throws RemoteException;
    public List<Citta> listaCittaPerNazione(String nazione) throws RemoteException;
    public List<String> listaNazione() throws RemoteException;
}
