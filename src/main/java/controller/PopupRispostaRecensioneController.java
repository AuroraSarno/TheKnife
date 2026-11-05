package controller;

import ServerTK.Gestori.GestoreRecensione;
import ServerTK.Modelli.Recensione;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class PopupRispostaRecensioneController {

    @FXML
    private TextArea testoRispostaRecensione;
    private Recensione recensioneDaModificare;

    public void impostaRecensione(Recensione r){
        recensioneDaModificare = r;
        if(r.getRispostaRecensione() != null){
            testoRispostaRecensione.setText(r.getRispostaRecensione());
        }
    }

    @FXML
    public void annullaModifica(){
    Stage finestraDaChiudere = (Stage) testoRispostaRecensione.getScene().getWindow();
    finestraDaChiudere.close();
    }

    @FXML
    public void inviaRisposta(){
        String nuovoTesto = testoRispostaRecensione.getText();

        GestoreRecensione gestoreRecensione = new GestoreRecensione();
        gestoreRecensione.rispondiRecensione(recensioneDaModificare, nuovoTesto);
        Stage finestraDaChiudere = (Stage) testoRispostaRecensione.getScene().getWindow();
        finestraDaChiudere.close();
    }
}
