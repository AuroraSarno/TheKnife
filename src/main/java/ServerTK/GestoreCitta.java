package ServerTK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * gestisce la logica delle città interfacciandosi con il database
 *
 * @author Matteo Mongelli 760960 Varese
 * @author Aurora Sarno 763021 VA
 */
public class GestoreCitta {

    public List<Citta> listaCittaPerNazione(String nazione) {
        List<Citta> citta = new ArrayList<>();
        String query = "SELECT nome FROM citta WHERE nazione=? ORDER BY nome";
        try (Connection connection = ConnessioneDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, nazione);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Citta c = new Citta(resultSet.getInt("citta_id"), resultSet.getString("nome"), resultSet.getString("nazione"), resultSet.getDouble("latitudine"), resultSet.getDouble("longitudine"));
                citta.add(c);
            }
            return citta;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public List<String> listaNazione() {
        List<String> nazioni = new ArrayList<>();
        String query = "SELECT DISTINCT nazione FROM citta ORDER BY nazione";
        try (Connection connection = ConnessioneDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                nazioni.add(resultSet.getString("nazione"));
            }
            return nazioni;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * metodo utility che calcola la distanza tra due punti geografici usando la formula di Haversine
     */
    public static double calcolaDistanza(double lat1, double lon1, double lat2, double lon2) {
        final int RAGGIO_TERRA_KM = 6371;

        //conversione in radianti per i successivi calcoli
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        //calcolo della differenza tra le coordinate
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        //formula di Haversine
        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.pow(Math.sin(deltaLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // conversione in Km della distanza
        return RAGGIO_TERRA_KM * c;
    }
}
