package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;


public class GuestController extends ClientiController {

    @FXML
    void apriLogin(ActionEvent event) {
        cambiaSchermata(event, "/graficaFXML/login/Login.fxml");
    }

    @FXML
    void apriAccedi(ActionEvent event) {
        cambiaSchermata(event, "/graficaFXML/login/Accedi.fxml");
    }


    private void cambiaSchermata(ActionEvent event, String percorsoFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(percorsoFxml));
            Parent nuovaRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(nuovaRoot);
        } catch (IOException e) {
            e.printStackTrace();
            mostraMessaggio("Errore", "Impossibile aprire la schermata: " + e.getMessage());
        }
    }

    private void mostraMessaggio(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}