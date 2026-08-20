package ServerTK.RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerTK {
    //metodo per avviare il server
    public void main(String [] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(8888);
            TheKnifeImpl theKnifeInterface = new TheKnifeImpl();
            registry.rebind("TheKnife", theKnifeInterface);
        }catch (RemoteException e) {
            System.err.println("Errore all'avvio del server" + e);
        }
    }
}
