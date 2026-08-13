package ServerTK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestoreCitta {

    public List<Citta> listaCittaPerNazione(String nazione) {
        List<Citta> citta = new ArrayList<>();
        String query = "SELECT nome FROM citta WHERE nazione=? ORDER BY nome";
        try(Connection connection= ConnessioneDatabase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);){
            preparedStatement.setString(1, nazione);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
               Citta c= new Citta(resultSet.getInt("citta_id"), resultSet.getString("nome"), resultSet. getString("nazione"),resultSet.getDouble("latitudine"), resultSet.getDouble("longitudine") );
               citta.add(c);
            }
            return citta;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public List<String> listaNazione(){
        List<String> nazioni = new ArrayList<>();
        String query = "SELECT DISTINCT nazione FROM citta ORDER BY nazione";
        try(Connection connection= ConnessioneDatabase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                nazioni.add(resultSet.getString("nazione"));
            }
            return nazioni;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
}
