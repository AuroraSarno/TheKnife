package controller;

import ServerTK.Modelli.Recensione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CardRecensioneController {

    @FXML
    private Label usernameUtente;
    @FXML
    private Label testoRecensione;
    @FXML
    private Label stelle;
    @FXML
    private Button btnRisposaRecensione;
    private Recensione recensione;

    public void impostaDatiRecensione(Recensione r) {
        this.recensione = r;
        usernameUtente.setText(r.getUsername());
        testoRecensione.setText(r.getTesto());
        stelle.setText((Integer.toString(r.getStelle())));

        if(r.getRispostaRecensione() != null && !r.getRispostaRecensione().isEmpty()) {
            btnRisposaRecensione.setText("Modifica Risposta");
        }else {
            btnRisposaRecensione.setText("Rispondi");
        }

    }

    @FXML
    public void apriPopUpRispostaRecensione(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/graficaFXML/ristoratore/PopupRispostaRecensione.fxml"));
            Parent root = loader.load();

            PopupRispostaRecensioneController controllerPopUpRispRecensione = loader.getController();
            controllerPopUpRispRecensione.impostaRecensione(recensione);

            Stage popUpRispRecensione = new Stage();
            popUpRispRecensione.setScene(new Scene(root));

            popUpRispRecensione.initModality(Modality.APPLICATION_MODAL);
            popUpRispRecensione.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
