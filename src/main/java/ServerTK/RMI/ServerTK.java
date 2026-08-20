package ServerTK.RMI;

import ServerTK.Gestori.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerTK {
    //metodo per avviare il server
    public static void main(String [] args) {
        try {
            GestoreCitta c = new GestoreCitta();
            GestoreUtenti u = new GestoreUtenti();
            GestoreRistorante r = new GestoreRistorante();
            GestoreRecensione rr = new GestoreRecensione();
            GestorePreferiti p = new GestorePreferiti();
            Registry registry = LocateRegistry.createRegistry(8888);
            TheKnifeImpl theKnifeInterface = new TheKnifeImpl(c,p,rr,r,u);
            registry.rebind("TheKnife", theKnifeInterface);
        }catch (RemoteException e) {
            System.err.println("Errore all'avvio del server" + e);
        }
    }
}
