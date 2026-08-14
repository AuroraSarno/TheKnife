package eccezioni;

/**
 * Eccezione che viene lanciata quando si tenta di accedere a una lista vuota
 * è checked in modo che l'utente possa sempre sapere il problema e correggerlo all'occorrenza
 * @author Matteo Mongelli 760960 Varese
 */
public class ListaVuotaException extends Exception {
    /**
     * Costruttore della classe con messaggio di errore
     * @param message il messaggio che spiega l'errorre
     */
    public ListaVuotaException(String message) {
        super(message);
    }
}
