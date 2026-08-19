package ServerTK.Modelli;

import java.util.Date;

public class Utente {
    public enum Ruolo{
        CLIENTE, RISTORATORE
    }
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String domicilio;
    private Date date;
    private Ruolo ruolo;

    public Utente(String username, String password, String nome, String cognome, String domicilio, Date date, Ruolo ruolo) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.domicilio = domicilio;
        this.date = date;
        this.ruolo = ruolo;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public String getDomicilio() {
        return domicilio;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Ruolo getRuolo() {
        return ruolo;
    }
    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }
}
