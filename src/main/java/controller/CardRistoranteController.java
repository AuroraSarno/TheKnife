package controller;

import ServerTK.Modelli.Ristorante;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class CardRistoranteController {

    @FXML
    private Label lblNome;

    private Ristorante mioRistorante;


    public void impostaDatiRistorante(Ristorante r){
        this.mioRistorante = r;
        lblNome.setText(r.getNome());
    }

    public void apriDettaglioRistorante() throws IOException {
        System.out.println("Ho cliccato ristorante" + this.mioRistorante.getNome());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/graficaFXML/ristoratore/DettaglioRistorante.fxml"));
        // CARICO la grafica (questo crea la tua VBox, Label, ecc.)
        Parent root = loader.load();

        // Chiedo al loader di darmi il controller appena creato
        DettaglioRistoranteController controllerPopup = loader.getController();

        // Uso il controller per passare il mio oggetto ristorante!
        // (Assumendo che nel tuo DettaglioRistoranteController tu abbia creato questo metodo)
        controllerPopup.impostaDati(this.mioRistorante);

        // Metto la grafica dentro una Scena
        Scene scena = new Scene(root);

       // Creo la nuova Finestra fisica (Lo Stage)
        Stage popupStage = new Stage();
        popupStage.setScene(scena);
        popupStage.setTitle("Dettagli Ristorante"); // Il titolo in alto nella barra della finestra

        // Dico alla finestra di bloccare tutto il resto dell'applicazione
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Mostro il popup e metto in pausa il codice qui finché l'utente non lo chiude!
        popupStage.showAndWait();
    }
}
