package controller;

import ClientTK.ConnessioneRMI;
import ServerTK.Modelli.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RegistrazioneController {
    @FXML
    private Button btnIndietro;

    @FXML
    private Hyperlink linkLogin;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private DatePicker dateBirthday;

    @FXML
    private TextField txtDomicilio;

    @FXML
    private RadioButton btnRistoratore;

    @FXML
    private RadioButton btnCliente;

    @FXML
    private ToggleGroup SceltaRuolo;

    @FXML
    private Button btnRegistrati;

    //Metodi per bottoni

    @FXML
    private void handleIndietro(ActionEvent event) {
        try {
            //Carica il file di benvenuto
            FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/graficaFXML/autenticazione/Benvenuto.fxml"));
            Parent root = loader.load();

            //Crea la nuova Scena con le stesse dimensioni della finestra
            Scene homeScene = new javafx.scene.Scene(root, 1280, 720);

            //Recupera la finestra partendo dal bottone che è stato cliccato
            Node source = (javafx.scene.Node) event.getSource();
            Stage stage = (javafx.stage.Stage) source.getScene().getWindow();

            //Cambia la scena e mostrala
            stage.setScene(homeScene);
            stage.show();

        } catch (Exception e) {
            System.out.println("Errore nel caricamento della pagina: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            //Carica il file di login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/graficaFXML/autenticazione/Login.fxml"));
            Parent root = loader.load();

            //Crea la nuova Scena con le stesse dimensioni della finestra
            Scene homeScene = new javafx.scene.Scene(root, 1280, 720);

            //Recupera la finestra partendo dal bottone che è stato cliccato
            Node source = (javafx.scene.Node) event.getSource();
            Stage stage = (javafx.stage.Stage) source.getScene().getWindow();

            //Cambia la scena e mostrala
            stage.setScene(homeScene);
            stage.show();

        } catch (Exception e) {
            System.out.println("Errore nel caricamento della pagina: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @FXML
    private void handleRegistrati(ActionEvent event) {

        //Raccolta dati

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        LocalDate dataNascita = dateBirthday.getValue();
        String domicilio = txtDomicilio.getText().trim();

        RadioButton selezionato = (RadioButton) SceltaRuolo.getSelectedToggle();

        //Validazione

            //campi vuoti

        if (username.isEmpty() || password.isEmpty() || nome.isEmpty() ||
                cognome.isEmpty() || dataNascita == null || domicilio.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campi mancanti");
            alert.setHeaderText("Attenzione!");
            alert.setContentText("Tutti i campi sono obbligatori!");
            alert.showAndWait();
            return;
        }

            //nessun ruolo selezionato

        if(selezionato == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ruolo non selezionato");
            alert.setHeaderText("Attenzione!");
            alert.setContentText("Devi selezionare un ruolo (Cliente o Ristoratore)!");
            alert.showAndWait();
            return;
        }

        //Conversione ruolo da scelta bottone a enum

        Utente.Ruolo ruolo;
        if (selezionato.getText().equalsIgnoreCase("Cliente")) {
            ruolo = Utente.Ruolo.CLIENTE;
        } else {
            ruolo = Utente.Ruolo.RISTORATORE;
        }

        //Conversione data da LocalDate a Date
        Date dataUtil = Date.from(dataNascita.atStartOfDay(ZoneId.systemDefault()).toInstant());

        //Registrazione tramite RMI dell'utente nel database

        try {
            boolean successo = ConnessioneRMI.getServer().registrazione(
                    username, nome, cognome, password, domicilio, dataUtil, ruolo
            );

            if (successo) {
                // SUCCESSO
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successo");
                alert.setHeaderText("Registrazione completata!");
                alert.setContentText("Benvenuto " + nome + "! Ora puoi effettuare il login.");
                alert.showAndWait();

                //REINDIRIZZAMENTO AL LOGIN
                handleLogin(event);
            } else {
                // ERRORE GENERICO
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Registrazione fallita");
                alert.setContentText("Non è stato possibile completare la registrazione. Riprova.");
                alert.showAndWait();
            }

        } catch (Exception e) {
            // ERRORE (es. username già esistente, problemi di rete, ecc.)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Registrazione fallita");

            // Messaggio più specifico se possibile
            String messaggio = e.getMessage();
            if (messaggio != null && messaggio.contains("duplicate key")) {
                alert.setContentText("Username già esistente! Scegline un altro.");
            } else {
                alert.setContentText("Errore: " + (messaggio != null ? messaggio : "Problema di connessione"));
            }

            alert.showAndWait();
            e.printStackTrace();
        }
    }

    //Metodi per i fields (passare dall'uno all'altro con invio)

    @FXML
    private void handleUsername(ActionEvent event) {
        //passa al campo password quando premi Invio
        txtPassword.requestFocus();
    }

    @FXML
    private void handlePassword(ActionEvent event) {
        txtNome.requestFocus();
    }

    @FXML
    private void handleNome(ActionEvent event) {
        txtCognome.requestFocus();
    }

    @FXML
    private void handleCognome(ActionEvent event) {
        dateBirthday.requestFocus();
    }

    @FXML
    private void handleData(ActionEvent event) {
        txtDomicilio.requestFocus();
    }

    @FXML
    private void handleDomicilio(ActionEvent event) {
        // Ultimo campo di testo, invia registrazione se si clicca invio
        btnRegistrati.fire();
    }

    @FXML
    private void handleRistoratore(ActionEvent event) {
        System.out.println("Hai selezionato Ristoratore");
    }

    @FXML
    private void handleCliente(ActionEvent event) {
        System.out.println("Hai selezionato Cliente");
    }


}
