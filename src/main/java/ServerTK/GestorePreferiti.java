package ServerTK;

import eccezioni.ListaVuotaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * gestisce la logica dei ristoranti preferiti interfacciandosi con il database
 *
 * @author Matteo Mongelli 760960 Varese
 */
public class GestorePreferiti {

    //aggiunge un ristorante ai preferiti di un utente aggiungendo un record al database
    public void aggiungiPreferiti(Utente utente, Ristorante ristorante) throws NullPointerException, IllegalArgumentException {
        if (utente == null || ristorante == null) {
            throw new NullPointerException("Utente o ristorante non esistenti");
        }

        String queryAdd = "INSERT INTO Preferiti (id_ristorante, username) VALUES (?,?)";

        //uso il metodo getConnection() della classe ConnessioneDatabase invio la query tramite try-with-resources
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(queryAdd)) {
            //setto i parametri che andranno a sostituire le "?" della query
            stmt.setInt(1, ristorante.getId_ristorante());
            stmt.setString(2, utente.getUsername());
            //eseguo la query
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                throw new IllegalArgumentException("questo ristorante fa già parte dei tuoi preferiti!");
            }
            e.printStackTrace();
        }
    }

    public void cancellaPreferiti(Utente utente, Ristorante ristorante) throws NullPointerException, ListaVuotaException {
        if (utente == null || ristorante == null) {
            throw new NullPointerException("Utente o ristorante non esistenti");
        }

        String queryDelete = "DELETE FROM Preferiti WHERE id_ristorante = ? AND username = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(queryDelete)) {

            stmt.setInt(1, ristorante.getId_ristorante());
            stmt.setString(2, utente.getUsername());

            int righeCancellate = stmt.executeUpdate();
            if (righeCancellate == 0) {
                throw new ListaVuotaException("questo ristorante non fa parte dei tuoi preferiti!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<Preferito> visualizzaPreferiti(Utente utente) throws ListaVuotaException {
        if (utente == null) {
            throw new NullPointerException("Utente non esistente");
        }

        List<Preferito> listapreferiti = new ArrayList<>();

        String queryGetLista = "SELECT nome FROM Ristoranti " +
                "JOIN Preferiti ON Ristoranti.id_ristorante = Preferiti.id_ristorante " +
                "WHERE username = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(queryGetLista)) {

            stmt.setString(1, utente.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String username = utente.getUsername(); //l'utente viene già passato come parametro
                    String nomeRistorante = rs.getString("nome");
                    listapreferiti.add(new Preferito(username, nomeRistorante));
                }
            } // non serve il catch perché il try è annidato nell'altro try, il quale già catcha SQLexception
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(listapreferiti.isEmpty()){
            throw new ListaVuotaException("Non hai nessun ristorante preferito!");
        }
        return listapreferiti;
    }
}




