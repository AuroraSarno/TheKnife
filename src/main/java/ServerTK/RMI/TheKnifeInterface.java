package ServerTK.RMI;

import ServerTK.Modelli.Ristorante;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TheKnifeInterface extends Remote {

    public List<Ristorante> cercaRistoranti(String nome, String tipoCucina, Double prezzoMin, Double prezzoMax,
                                            Boolean delivery, Boolean prenotazione, Double stelle, Integer idCitta) throws RemoteException;

    public boolean aggiungiRistorante(Ristorante r) throws RemoteException;

    public List<Ristorante> getRistoranteDi(String usernameProprietario) throws RemoteException;

    public List<String> getTipiCucinaLista() throws RemoteException;


}
