package ServerTK.RMI;

import ServerTK.Gestori.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
}
