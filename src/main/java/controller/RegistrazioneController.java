package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

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
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Benvenuto.fxml"));
            javafx.scene.Parent root = loader.load();

            //Crea la nuova Scena con le stesse dimensioni della finestra
            javafx.scene.Scene homeScene = new javafx.scene.Scene(root, 1280, 720);

            //Recupera la finestra partendo dal bottone che è stato cliccato
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();
            javafx.stage.Stage stage = (javafx.stage.Stage) source.getScene().getWindow();

            //Cambia la scena e mostrala
            stage.setScene(homeScene);
            stage.show();

        } catch (Exception e) {
            // Se qualcosa va storto (es. hai sbagliato il nome del file fxml),
            // questo stamperà l'errore nella console per aiutarti a capire il problema
            System.out.println("Errore nel caricamento della pagina: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @FXML
    private void handleLogin(ActionEvent event) {
        System.out.println("Vado alla schermata di Login...");
        // Qui metterai il codice per aprire il login
    }

    @FXML
    private void handleRegistrati(ActionEvent event) {
        System.out.println("Hai cliccato Registrati!");
        // Qui leggerai tutti i txtUsername.getText(), ecc. per salvare nel database
    }

    //Metodi per i fields

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
        // Ultimo campo di testo
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
