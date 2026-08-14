package ServerTK;

import ServerTK.ConnessioneDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class GestoreRecensione {
    public GestoreRecensione() {
    }

    private Recensione estraiRec(ResultSet rs) throws SQLException {
        int idRecensione = rs.getInt("id_recensione");
        int id_ristorante = rs.getInt("id_ristorante");
        String testo = rs.getString("testo");
        int stelle = rs.getInt("stelle");
        String rispostaRecensione = rs.getString("risposta_recensione");
        String username = rs.getString("username");

        Recensione rec = new Recensione(id_ristorante, testo, stelle, rispostaRecensione, username);
        rec.setIdRecensione(idRecensione);
        return rec;
    }
    public List<Recensione> getRecensioni() {
        List<Recensione> recensioni = new ArrayList<>();
        String query = "SELECT * FROM Recensioni";

        try (Connection conn = ConnessioneDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                recensioni.add(estraiRec(rs));
            }
        } catch (SQLException e) {
            System.out.println("Errore nel recupero delle recensioni: " + e.getMessage());
        }
        return recensioni;
    }

    public void modificaRecensione(Recensione rec, String testoMod, int stelleMod, Utente utente) throws IllegalArgumentException {
        if (!utente.getUsername().equals(rec.getUsername())) {
            throw new IllegalArgumentException("Utente non autorizzato a modificare questa recensione");
        }

        boolean haTesto = (testoMod != null && !testoMod.trim().isEmpty());
        boolean haStelle = (stelleMod >= 1 && stelleMod <= 5);

        if (!haTesto && !haStelle){ return;} // Nulla da modificare

        StringBuilder queryBuilder = new StringBuilder("UPDATE Recensioni SET ");
        if (haTesto) queryBuilder.append("testo = ?");
        if (haTesto && haStelle) queryBuilder.append(", ");
        if (haStelle) queryBuilder.append("stelle = ?");
        queryBuilder.append(" WHERE id_recensione = ? AND username = ?");

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {

            int paramIndex = 1;
            if (haTesto) pstmt.setString(paramIndex++, testoMod);
            if (haStelle) pstmt.setInt(paramIndex++, stelleMod);

            pstmt.setInt(paramIndex++, rec.getIdRecensione());
            pstmt.setString(paramIndex, utente.getUsername());

            int righeMod = pstmt.executeUpdate();
            if (righeMod == 0) {
                throw new IllegalArgumentException("Recensione non trovata");
            }

            // Aggiorno l'oggetto locale
            if (haTesto) rec.setTesto(testoMod);
            if (haStelle) rec.setStelle(stelleMod);

        } catch (SQLException e) {
            System.out.println("Errore nella modifica della recensione: " + e.getMessage());
        }
    }

    public void rispondiRecensione(Recensione rec, String risposta) throws IllegalArgumentException {
        String query = "UPDATE Recensioni SET risposta = ? WHERE id_recensione = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, risposta);
            pstmt.setInt(2, rec.getIdRecensione());

            int righeMod = pstmt.executeUpdate();
            if (righeMod == 0) {
                throw new IllegalArgumentException("Recensione non trovata nel database.");
            }

            // Aggiorno l'oggetto in locale
            rec.setRispostaRecensione(risposta);

        } catch (SQLException e) {
            System.out.println("Errore durante il salvataggio della risposta: " + e.getMessage());
        }
    }

    public void inserisciRecensione(Ristorante ris, String testo, int stelle, String username) {
        String countQuery = "SELECT COALESCE(MAX(id_recensione), 0) + 1 AS next_id FROM Recensioni";
        String insertQuery = "INSERT INTO Recensioni (id_recensione, testo, stelle, risposta, id_ristorante, username)" +
                " VALUES (?, ?, ?, ?, ?, ?)";

        Recensione nuova = new Recensione(ris.getId_ristorante(), testo, stelle, null, username);

        try (Connection conn = ConnessioneDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(countQuery)) {

            int nextId = 1;
            if (rs.next()) {
                nextId = rs.getInt("next_id");
            }

            nuova.setIdRecensione(nextId);

            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setInt(1, nextId);
                pstmt.setString(2, testo);
                pstmt.setInt(3, stelle);
                pstmt.setNull(4, Types.VARCHAR); // Nessuna risposta inizialmente
                pstmt.setInt(5, ris.getId_ristorante()); // Richiede getIdRistorante() in Ristorante
                pstmt.setString(6, username);

                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Errore nell'inserimento della recensione: " + e.getMessage());
        }
    }

    public void eliminaRecensione(Recensione rec) throws IllegalArgumentException {
        String query = "DELETE FROM Recensioni WHERE id_recensione = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, rec.getIdRecensione());
            int rigaMod = pstmt.executeUpdate();

            if (rigaMod == 0) {
                throw new IllegalArgumentException("Recensione non trovata");
            }

        } catch (SQLException e) {
            System.out.println("Errore nell'eliminazione della recensione: " + e.getMessage());
        }
    }

    public void visualizzaRiepilogo(Ristorante ris) throws IllegalArgumentException {
        String query = "SELECT COUNT(*) AS num_recensioni, AVG(stelle) " +
                "AS media_stelle FROM Recensioni WHERE id_ristorante = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, ris.getId_ristorante());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int cont = rs.getInt("n_recensioni");
                    if (cont == 0) {
                        throw new IllegalArgumentException("Nessuna recensione presente per questo ristorante.");
                    }
                    double media = rs.getDouble("media_stelle");
                    System.out.println("Numero di recensioni: " + cont);
                    System.out.println("Valutazione media: " + String.format("%.1f", media));
                }
            }

        } catch (SQLException e) {
            System.out.println("Errore nel calcolo del riepilogo: " + e.getMessage());
        }
    }

    public List<Recensione> visualizzaRecensioniperUtente(Utente u) throws IllegalArgumentException {
        List<Recensione> recensioniUtente = new ArrayList<>();
        String query = "SELECT * FROM Recensioni WHERE username = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, u.getUsername());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    recensioniUtente.add(estraiRec(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Errore nel recupero delle recensioni per l'utente: " + e.getMessage());
        }

        if (recensioniUtente.isEmpty()) {
            throw new IllegalArgumentException("Nessuna recensione presente per questo utente.");
        }
        return recensioniUtente;
    }

    public List<Recensione> visualizzaRecensioniPerRistoratore(Ristorante ris) {
        List<Recensione> recensioniPerRistoratore = new ArrayList<>();
        String query = "SELECT * FROM Recensioni WHERE id_ristorante = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query){

            pstmt.setInt(1, ris.getId_ristorante());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    recensioniPerRistoratore.add(estraiRec(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Errore nel recupero delle recensioni per il ristoratore: " + e.getMessage());
        }
        return recensioniPerRistoratore;
    }
    public boolean haLasciatoRecensione(Utente u, Ristorante ris) {
        String query = "SELECT 1 FROM Recensioni WHERE username = ? AND id_ristorante = ?";
        boolean haLasciato = false;

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, u.getUsername());
            pstmt.setInt(2, ris.getId_ristorante());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    haLasciato = true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Errore nella verifica della recensione: " + e.getMessage());
        }

        return haLasciato;
    }

    public static String gestisciInputOpzionale(String msg, Scanner sc, boolean blank) {
        System.out.println(msg);
        return sc.nextLine();
    }
}


