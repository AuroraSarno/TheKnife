package ServerTK.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TheKnifeImpl extends UnicastRemoteObject implements TheKnifeInterface {
    private static final long serialVersionUID = 1L;
    public TheKnifeImpl() throws RemoteException{
        super();
    }
}
