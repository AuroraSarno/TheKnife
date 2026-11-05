package controller;

import ClientTK.SessioneUtente;
import ServerTK.Gestori.GestoreRistorante;
import ServerTK.Modelli.Ristorante;
import ServerTK.Modelli.Utente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

public class AreaRistoratoreController {

    @FXML
    private TilePane grigliaRistoranti;

    @FXML
    private TextField ricerca;

    private List<Ristorante> ristorantiProprietario;


    @FXML
    public void initialize(){
        /*
        Utente utenteLoggato = SessioneUtente.getUtenteLoggato();
        GestoreRistorante gestoreRistorante = new GestoreRistorante();

        ristorantiProprietario = gestoreRistorante.getRistoranteDi(utenteLoggato.getUsername());

        stampaGrigliaRistoranti(ristorantiProprietario);*/
        List<Ristorante> listaFinta = new ArrayList<>();

        // Inserisci dati a caso rispettando il tuo costruttore Ristorante
        Ristorante finto = new Ristorante(1,"Pizzeria Test", "Via Finta 123", 1, 1, 15,
                true, true,"Italiana", 5.0, "Luca", 5 );

        listaFinta.add(finto);

        // 3. PASSA LA LISTA FINTA AL TUO METODO (che ora non andrà più in crash se hai messo l'fx:id!)
        stampaGrigliaRistoranti(listaFinta);

        ricerca.textProperty().addListener((observable,testoVecchio, testoNuovo) -> {
            List<Ristorante> filtrati = new ArrayList<>();

            for(Ristorante r : ristorantiProprietario){
                if(r.getNome().toLowerCase().contains(testoNuovo.toLowerCase())){
                    filtrati.add(r);
                }
            }
            stampaGrigliaRistoranti(filtrati);
        });
    }

    // Metodo che prende in input una lista di ristoranti e li disegna
    public void stampaGrigliaRistoranti(List<Ristorante> listaDaStampare) {

        //Pulise griglia
        grigliaRistoranti.getChildren().clear();

        for(Ristorante r: listaDaStampare){

            try{
                //Punto schermata dove stampare le card
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/graficaFXML/ristoratore/CardRistorante.fxml"));

                VBox cardRistorante = loader.load();

                //Prendo controller della card
                CardRistoranteController cardRistoranteController = loader.getController();
                cardRistoranteController.impostaDatiRistorante(r);//Passo ristorante corrente
                grigliaRistoranti.getChildren().add(cardRistorante);//Metto card nel TilePane

            }catch(Exception e){
                e.printStackTrace();
            }
        }


    }

    @FXML
    public void tastoLogout(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/graficaFXML/autenticazione/Benvenuto.fxml"));
            // CARICO la grafica (questo crea la tua VBox, Label, ecc.)
            Parent root = loader.load();

            // Metto la grafica dentro una Scena
            Scene scena = new Scene(root);

            // Creo la nuova Finestra fisica (Lo Stage)
            Stage stageAttuale = (Stage) grigliaRistoranti.getScene().getWindow();
            stageAttuale.setScene(scena);

        }catch(Exception e){
            e.printStackTrace();
        }


    }

    @FXML
    public void btnAggRistorante(){

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/graficaFXML/autenticazione/PopupAggiungiRistorante.fxml"));
            Parent root = loader.load();

            Stage popupAggRist = new Stage();
            popupAggRist.setScene(new Scene(root));

            popupAggRist.initModality(Modality.APPLICATION_MODAL);
            popupAggRist.showAndWait();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
