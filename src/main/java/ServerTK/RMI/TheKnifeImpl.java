package ServerTK.RMI;

import ServerTK.Modelli.Ristorante;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TheKnifeImpl extends UnicastRemoteObject implements TheKnifeInterface {
    private static final long serialVersionUID = 1L;
    public TheKnifeImpl() throws RemoteException{
        super();
    }

    public List<Ristorante> cercaRistoranti(String nome, String tipoCucina, Double prezzoMin, Double prezzoMax,
                                            Boolean delivery, Boolean prenotazione, Double stelle, Integer idCitta) throws RemoteException;

    public boolean aggiungiRistorante(Ristorante r) throws RemoteException{}

    public List<Ristorante> getRistoranteDi(String usernameProprietario) throws RemoteException;

    public List<String> getTipiCucinaLista() throws RemoteException
}
