package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerBenvenuto {
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegistrati;
    @FXML
    private Button btnGuest;
    @FXML
    private AnchorPane paneSfondo;
    @FXML
    private HBox hboxBottoni;

    public void initialize() {
        // ... (qui tieni il codice che avevi già scritto per far caricare lo sfondo) ...

        // Aggiungiamo un "Listener" (ascoltatore) alla larghezza dell'AnchorPane
        paneSfondo.widthProperty().addListener((obs, oldVal, newVal) -> {

            // Calcoliamo la nuova grandezza (es. il 2% della larghezza dello schermo)
            // Sentiti libera di aumentare o diminuire questo 0.02 per tarare la grandezza
            double grandezzaDinamica = newVal.doubleValue() * 0.02;

            // Applichiamo la nuova misura all'HBox.
            // Dato che i bottoni usano gli "em", si gonfieranno automaticamente a cascata!
            hboxBottoni.setStyle("-fx-font-size: " + grandezzaDinamica + "px;");
        });
    }
    @FXML
    public void actionLogin(ActionEvent event) throws IOException {
        //carico il file FXML della schermata login
        Parent loginRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScene = new Scene(loginRoot);

        //prendo la schermata attuali tramite bottone
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //imposto la nuova schermata
        currentStage.setScene(loginScene);
        currentStage.show();
    }
    @FXML
    public void actionRegistrati(ActionEvent event) throws IOException {
        //carico il file FXML della schermata login
        Parent loginRoot = FXMLLoader.load(getClass().getResource("Registrazione.fxml"));
        Scene loginScene = new Scene(loginRoot);

        //prendo la schermata attuali tramite bottone
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //imposto la nuova schermata
        currentStage.setScene(loginScene);
        currentStage.show();
    }
    @FXML
    public void actionGuest(ActionEvent event) throws IOException {
        //carico il file FXML della schermata login
        Parent loginRoot = FXMLLoader.load(getClass().getResource("Ricerca.fxml"));
        Scene loginScene = new Scene(loginRoot);

        //prendo la schermata attuali tramite bottone
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //imposto la nuova schermata
        currentStage.setScene(loginScene);
        currentStage.show();
    }
}
