package ServerTK.Gestori;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static String dbUser = "";
    private static String dbPassword = "";

    //metodo iniziale per definire le credenziali della connessione al database
    public static void setCredenziali(String username, String password) {
        dbUser = username;
        dbPassword = password;
    }

    //metodo per generare una connessione al database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, dbUser, dbPassword);
    }
}
