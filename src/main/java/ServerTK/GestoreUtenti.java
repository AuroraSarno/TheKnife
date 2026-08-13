package ServerTK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import ServerTK.Utente.Ruolo;

public class GestoreUtenti {

    //metodi
    //metodo per inserire utente nella tabella Utenti
    public boolean registrazione(String username, String nome, String cognome, String password, String domicilio, Date data, Ruolo ruolo) {
        String query= "INSERT into Utenti VALUES(?,?,?,?,?,?,?)";
        try(Connection connection=ConnessioneDatabase.getConnection();) {
            PreparedStatement statement=connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, nome);
            statement.setString(3, cognome);
            statement.setString(4, password);
            statement.setString(5, domicilio);
            statement.setDate(6, (java.sql.Date) data);
            statement.setString(7, ruolo.name());
            int righeModificate = statement.executeUpdate();
            return righeModificate > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    public Utente login(String username, String password) {
        String query= "SELECT * FROM Utenti WHERE username = ? AND password = ?";
        try(Connection connection= ConnessioneDatabase.getConnection();
            PreparedStatement statement= connection.prepareStatement(query);){
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()){
                return new Utente(resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("nome"),resultSet.getString("cognome"),resultSet.getString("domicilio"),resultSet.getDate("data"),Ruolo.valueOf(resultSet.getString("ruolo")));
            }else {return null;}
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
