package controller;

import ClientTK.SessioneUtente;
import ServerTK.Gestori.GestoreCitta;
import ServerTK.Gestori.GestoreRecensione;
import ServerTK.Modelli.Recensione;
import ServerTK.Modelli.Ristorante;
import ServerTK.Modelli.Utente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class DettaglioRistoranteController {

    @FXML
    private Label lblTitolo;
    @FXML
    private Label lblCategoriaPrezzo;
    @FXML
    private Label lblStelle;
    @FXML
    private Label lblIndirizzo;
    @FXML
    private Label lblServizi;
    @FXML
    private Button btnLasciaRecensione;
    @FXML
    private VBox contenitoreRecensioni;
    private Ristorante ristoranteCorrente;


    public void impostaDati(Ristorante ristorante) {
        GestoreRecensione gestoreRecensione = new GestoreRecensione();
        GestoreCitta gestoreCitta = new GestoreCitta();
        //Aggiorno i testi semplici della colonna fissa
        lblTitolo.setText(ristorante.getNome());
        lblIndirizzo.setText("📍 " + gestoreCitta.getNomeCittaDaID(ristorante.getId_citta()) + " - " + ristorante.getIndirizzo());
        lblCategoriaPrezzo.setText(ristorante.getTipoCucina() + " • ~" + ristorante.getPrezzoMedio() + "€");
        lblStelle.setText("⭐ " + ristorante.getStelle());
        ristoranteCorrente = ristorante;
        //Converto i true/false in parole per i servizi
        String testoDelivery = ristorante.getDelivery() ? "Sì" : "No";
        String testoPrenotazione = ristorante.getPrenotazione() ? "Sì" : "No";
        lblServizi.setText("🛵 Consegna: " + testoDelivery + "  |  📅 Prenotazione: " + testoPrenotazione);

        if(SessioneUtente.getUtenteLoggato().getRuolo() == Utente.Ruolo.RISTORATORE) {
            btnLasciaRecensione.setVisible(false);
            btnLasciaRecensione.setManaged(false);
        }

        stampaGrigliaRecensioni(gestoreRecensione.visualizzaRecensioniPerRistoratore(ristorante));

    }

    public void stampaGrigliaRecensioni(List<Recensione> recensioni){

        contenitoreRecensioni.getChildren().clear();

        for(Recensione r : recensioni){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/graficaFXML/ristoratore/CardRecensione.fxml"));
                Parent cardGrafica =  loader.load();

                CardRecensioneController cardRecensioneController = loader.getController();
                cardRecensioneController.impostaDatiRecensione(r);

                contenitoreRecensioni.getChildren().add(cardGrafica);

            } catch (Exception e) {
                System.out.println("Errore nel caricamento della card");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void tornaIndietro(){
         try{
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/graficaFXML/ristoratore/AreaRistoratore.fxml"));
             Parent root = loader.load();

             Scene scena = new Scene(root);

             Stage stageAttuale = (Stage) contenitoreRecensioni.getScene().getWindow();
             stageAttuale.setScene(scena);


         }catch(Exception e){
             e.printStackTrace();
         }
    }

    @FXML
    public void btnLasciaRecensione(){}

}
