package ServerTK.Gestori;


import ServerTK.Modelli.Ristorante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestoreRistorante{

    //Metodi

    //Ricerca ristorante con filtro
    //Capire se fare ricerca anche tramite nome ristorante
    public List<Ristorante> cercaRistoranti(String nome, String tipoCucina, Double prezzoMin, Double prezzoMax,
                                            Boolean delivery, Boolean prenotazione, Double stelle, Integer idCitta){

        List<Ristorante> risultati = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM Ristoranti WHERE 1=1");
        List<Object> valoriParametri = new ArrayList<>();

        if(nome != null && !nome.trim().isEmpty()){
            sql.append(" AND nome ILIKE ?");
            valoriParametri.add(nome);
        }

        if(tipoCucina != null && !tipoCucina.trim().isEmpty()){
            sql.append(" AND tipoCucina = ?");
            valoriParametri.add(tipoCucina);
        }

        if(prezzoMin != null){
            sql.append(" AND prezzoMedio >= ?");
            valoriParametri.add(prezzoMin);
        }

        if(prezzoMax != null){
            sql.append(" AND prezzoMedio <= ?");
            valoriParametri.add(prezzoMax);
        }

        if(delivery != null){
            sql.append(" AND delivery = ?");
            valoriParametri.add(delivery);
        }

        if(prenotazione != null){
            sql.append(" AND prenotazione = ?");
            valoriParametri.add(prenotazione);
        }

        if(stelle != null){
            sql.append(" AND stelle >= ?");
            valoriParametri.add(stelle);
        }

        if(idCitta != null){
            sql.append(" AND idCitta = ?");
            valoriParametri.add(idCitta);
        }

        try(Connection con = ConnessioneDatabase.getConnection();
            PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for(int i = 0; i < valoriParametri.size(); i++){
                ps.setObject(i+1, valoriParametri.get(i));
            }

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Ristorante r = new Ristorante(
                            rs.getInt("id_ristorante"),
                            rs.getString("nome"),
                            rs.getString("indirizzo"),
                            rs.getDouble("latitudine"),
                            rs.getDouble("longitudine"),
                            rs.getInt("prezzoMedio"),
                            rs.getBoolean("delivery"),
                            rs.getBoolean("prenotazione"),
                            rs.getString("tipoCucina"),
                            rs.getObject("stelle", Double.class),
                            rs.getString("usernameRistoratore"),
                            rs.getInt("id_citta")
                    );

                    risultati.add(r);

                }
            }


        }catch(SQLException e){
            System.out.println("Errore durante la ricerca: " + e.getMessage());
        }
        return risultati;
    }

    //Inserisci un ristorante nella tabella
    public boolean aggiungiRistorante(Ristorante r) {
        String sql = "INSERT INTO Ristoranti (nome, indirizzo, latitudine, longitudine, prezzoMedio, " +
                "delivery, prenotazione, tipoCucina, stelle, usernameRistoratore, id_citta) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try(Connection con = ConnessioneDatabase.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1,r.getNome());
            ps.setString(2,r.getIndirizzo());
            ps.setDouble(3,r.getLatitudine());
            ps.setDouble(4,r.getLongitudine());
            ps.setInt(5, r.getPrezzoMedio());
            ps.setBoolean(6,r.getDelivery());
            ps.setBoolean(7,r.getPrenotazione());
            ps.setString(8, r.getTipoCucina());
            ps.setObject(9, r.getStelle());
            ps.setString(10, r.getProprietario());
            ps.setInt(11, r.getId_citta());


            int righeModificate = ps.executeUpdate();

            return righeModificate > 0;

        }catch(SQLException e){
            System.out.println("Errore durante l'inserimento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Restituisce una lista di ristoranti di proprietà di uno specifico utente.
     * @param usernameProprietario Lo username del proprietario dei ristoranti da cercare.
     * @return Una lista di oggetti <code>Ristorante</code> appartenenti al proprietario specificato.
     */
    public List<Ristorante> getRistoranteDi(String usernameProprietario){
        List<Ristorante> risultati = new ArrayList<>();

        String sql = "SELECT * FROM Ristoranti WHERE usernameRistoratore = ?";

        try(Connection con = ConnessioneDatabase.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usernameProprietario);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Ristorante r = new Ristorante(
                            rs.getInt("id_ristorante"),
                            rs.getString("nome"),
                            rs.getString("indirizzo"),
                            rs.getDouble("latitudine"),
                            rs.getDouble("longitudine"),
                            rs.getInt("prezzoMedio"),
                            rs.getBoolean("delivery"),
                            rs.getBoolean("prenotazione"),
                            rs.getString("tipoCucina"),
                            rs.getObject("stelle", Double.class),
                            rs.getString("usernameRistoratore"),
                            rs.getInt("id_citta")
                    );

                    risultati.add(r);
                }
            }
        }catch(SQLException e){
            System.out.println("Errore durante cerca ristorante di " + usernameProprietario + ": " + e.getMessage());
        }
        return risultati;
    }

    /**
     * Restituisce una lista di tutte le tipologie di cucina presenti nel sistema, senza duplicati.
     * @return Una lista di stringhe rappresentanti i tipi di cucina unici.
     */
    public List<String> getTipiCucinaLista(){
        List<String> tipiCucina = new ArrayList<>();

        String sql = "SELECT DISTINCT tipoCucina FROM Ristoranti";

        try(Connection con = ConnessioneDatabase.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                String tC = rs.getString("tipoCucina");

                tipiCucina.add(tC);
                }
            }

        }catch(SQLException e){
            System.out.println("Errore durante cerca tipi cucina: " + e.getMessage());
        }

        return tipiCucina;

    }

}

