package controller;

import ServerTK.Gestori.GestoreCitta;
import ServerTK.Gestori.GestorePreferiti;
import ServerTK.Gestori.GestoreRecensione;
import ServerTK.Gestori.GestoreRistorante;
import ServerTK.Modelli.Citta;
import ServerTK.Modelli.Ristorante;
import ServerTK.Modelli.Utente;
import eccezioni.ListaVuotaException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class ClientiController {


    private static final String CARTELLA_POPUP = "/graficaFXML/popup/";

    @FXML
    protected TextField ricercaRistoranteField;

    @FXML
    protected ComboBox<String> tipoCucinaField;

    @FXML
    protected TextField prezzoMin;

    @FXML
    protected TextField prezzoMax;

    @FXML
    protected CheckBox deliveryField;

    @FXML
    protected CheckBox prenotazioneField;

    @FXML
    protected ComboBox<String> stelleField;

    @FXML
    protected ComboBox<String> inserisciCittaField;

    @FXML
    protected ListView<Ristorante> listViewRistoranti;

    private GestoreRistorante gestoreRistorante;
    private GestoreCitta gestoreCitta;
    private Map<String, Integer> cittaIdMap;
    private GestoreRecensione gestoreRecensione;
    private Utente u;
    private GestorePreferiti gestorePreferiti;

    @FXML
    public void initialize() {

        gestoreRistorante = new GestoreRistorante();
        gestoreCitta = new GestoreCitta();
        cittaIdMap = new HashMap<>();

        tipoCucinaField.getItems().add("Tutte le cucine");
        List<String> cucineDB = gestoreRistorante.getTipiCucinaLista();
        if (cucineDB != null && !cucineDB.isEmpty()) {
            tipoCucinaField.getItems().addAll(cucineDB);
        }
        tipoCucinaField.setValue("Tutte le cucine");

        stelleField.getItems().addAll(
                "Qualsiasi punteggio",
                "1 stella",
                "2 stelle",
                "3 stelle",
                "4 stelle",
                "5 stelle"
        );
        stelleField.setValue("Qualsiasi punteggio");

        inserisciCittaField.getItems().add("Tutte le città");
        List<String> nazioni = gestoreCitta.listaNazione();
        if (nazioni != null && !nazioni.isEmpty()) {
            for (String nazione : nazioni) {
                List<Citta> cittaPerNazione = gestoreCitta.listaCittaPerNazione(nazione);
                if (cittaPerNazione != null) {
                    for (Citta citta : cittaPerNazione) {
                        inserisciCittaField.getItems().add(citta.getNome());
                        cittaIdMap.put(citta.getNome(), citta.getId());
                    }
                }
            }
        }
        inserisciCittaField.setValue("Tutte le città");

        listViewRistoranti.setCellFactory(param -> new ListCell<Ristorante>() {
            @Override
            protected void updateItem(Ristorante ristorante, boolean empty) {
                super.updateItem(ristorante, empty);
                if (empty || ristorante == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox box = new VBox(5);
                    box.setPadding(new Insets(5));

                    Label nomeLabel = new Label(ristorante.getNome());
                    nomeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                    Label tipoCucinaLabel = new Label("Cucina: " + ristorante.getTipoCucina());
                    Label prezzoLabel = new Label("Prezzo medio: €" + ristorante.getPrezzoMedio());
                    Label stelleLabel = new Label("Stelle: " + ristorante.getStelle());

                    HBox servizi = new HBox(10);
                    if (ristorante.getDelivery() != null && ristorante.getDelivery()) {
                        servizi.getChildren().add(new Label("🚚 Delivery"));
                    }
                    if (ristorante.getPrenotazione() != null && ristorante.getPrenotazione()) {
                        servizi.getChildren().add(new Label("📅 Prenotazione"));
                    }

                    box.getChildren().addAll(nomeLabel, tipoCucinaLabel, prezzoLabel, stelleLabel);
                    if (!servizi.getChildren().isEmpty()) {
                        box.getChildren().add(servizi);
                    }

                    setGraphic(box);
                }
            }
        });

        ricercaRistoranteField.textProperty().addListener((observable, oldValue, newValue) -> effettuaRicerca());

        tipoCucinaField.valueProperty().addListener((observable, oldValue, newValue) -> effettuaRicerca());
        stelleField.valueProperty().addListener((observable, oldValue, newValue) -> effettuaRicerca());
        inserisciCittaField.valueProperty().addListener((observable, oldValue, newValue) -> effettuaRicerca());
        deliveryField.selectedProperty().addListener((observable, oldValue, newValue) -> effettuaRicerca());
        prenotazioneField.selectedProperty().addListener((observable, oldValue, newValue) -> effettuaRicerca());

        prezzoMin.textProperty().addListener((observable, oldValue, newValue) -> effettuaRicerca());
        prezzoMax.textProperty().addListener((observable, oldValue, newValue) -> effettuaRicerca());

        effettuaRicerca();
    }


    private void effettuaRicerca() {
        // Raccogli i parametri di ricerca
        String parolaCercata = ricercaRistoranteField.getText();
        if (parolaCercata == null || parolaCercata.trim().isEmpty()) {
            parolaCercata = null;
        }

        String cucina = tipoCucinaField.getValue();
        if (cucina == null || cucina.equals("Tutte le cucine")) {
            cucina = null;
        }

        Boolean vuoleDelivery = deliveryField.isSelected() ? true : null;
        Boolean vuolePrenotazione = prenotazioneField.isSelected() ? true : null;

        Double prezzoMinimo = null;
        try {
            String minText = prezzoMin.getText();
            if (minText != null && !minText.trim().isEmpty()) {
                prezzoMinimo = Double.parseDouble(minText.trim().replace(',', '.'));
            }
        } catch (NumberFormatException e) {
        }

        Double prezzoMassimo = null;
        try {
            String maxText = prezzoMax.getText();
            if (maxText != null && !maxText.trim().isEmpty()) {
                prezzoMassimo = Double.parseDouble(maxText.trim().replace(',', '.'));
            }
        } catch (NumberFormatException e) {
        }

        Double stelle = null;
        String stelleValue = stelleField.getValue();
        if (stelleValue != null && !stelleValue.equals("Qualsiasi punteggio")) {
            try {
                stelle = Double.parseDouble(stelleValue.split(" ")[0]);
            } catch (NumberFormatException e) {
            }
        }

        Integer idCitta = null;
        String cittaValue = inserisciCittaField.getValue();
        if (cittaValue != null && !cittaValue.equals("Tutte le città")) {
            idCitta = cittaIdMap.get(cittaValue);
        }

        List<Ristorante> risultati = gestoreRistorante.cercaRistoranti(
                parolaCercata,
                cucina,
                prezzoMinimo,
                prezzoMassimo,
                vuoleDelivery,
                vuolePrenotazione,
                stelle,
                idCitta
        );

        if (risultati == null) {
            listViewRistoranti.setItems(FXCollections.observableArrayList());
        } else {
            listViewRistoranti.setItems(FXCollections.observableArrayList(risultati));
        }
    }


    @FXML
    void apriPreferiti(ActionEvent event) {
        mostraPopup("Preferiti.fxml", (Node) event.getSource(),
                (PreferitiController popupController) -> {
                    try {
                        gestorePreferiti.visualizzaPreferiti(u);
                    } catch (ListaVuotaException e) {
                        throw new RuntimeException(e);
                    }
                    List<Ristorante> preferiti = new ArrayList<>();
                    popupController.setPreferiti(preferiti);
                });
    }

    @FXML
    void apriRecensioni(ActionEvent event) {
        mostraPopup("Recensioni.fxml", (Node) event.getSource(),
                (RecensioniController popupController) -> {
                    List<RecensioniController.RecensioneItem> recensioni = gestoreRecensione.visualizzaRecensioniperUtente(u); //SISTEMARE
                    popupController.setRecensioni(recensioni);
                });
    }

    @FXML
    void apriDati(ActionEvent event) {
        try {
            FXMLLoader loader = creaLoader("PopupDati.fxml");
            Parent root = loader.load();
            PopupDatiController popupController = loader.getController();

            List<String> citta = new ArrayList<>(cittaIdMap.keySet());
            Collections.sort(citta);

            //SISTEMARE
            popupController.setDati("Mario", "Rossi", "Milano", citta);

            Stage popup = new Stage();
            popup.setTitle("I tuoi dati");
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(((Node) event.getSource()).getScene().getWindow());
            popup.setScene(new Scene(root));
            popup.setResizable(false);
            popup.showAndWait();

            popupController.getRisultato().ifPresent(dati -> {

                System.out.println("Nuovi dati: " + dati.nome() + " " + dati.cognome() + ", " + dati.citta());
            });

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            mostraMessaggio("Errore", "Impossibile aprire la finestra dei dati.\n" + e.getMessage());
        }
    }


    private <T> void mostraPopup(String nomeFile, Node origine, Consumer<T> inizializza) {
        try {
            FXMLLoader loader = creaLoader(nomeFile);
            Parent root = loader.load();
            T popupController = loader.getController();
            inizializza.accept(popupController);

            Scene sceneOwner = origine.getScene();
            Window finestra = sceneOwner.getWindow();

            Scene scenaPopup = new Scene(root, root.prefWidth(-1), sceneOwner.getHeight());

            Stage popup = new Stage(StageStyle.UNDECORATED);
            popup.initOwner(finestra);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setScene(scenaPopup);
            popup.setResizable(false);

            popup.setX(finestra.getX() + sceneOwner.getX());
            popup.setY(finestra.getY() + sceneOwner.getY());

            scenaPopup.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    popup.close();
                }
            });

            popup.showAndWait();

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            mostraMessaggio("Errore", "Impossibile aprire la finestra.\n" + e.getMessage());
        }
    }


    private FXMLLoader creaLoader(String nomeFile) {
        URL url = getClass().getResource(CARTELLA_POPUP + nomeFile);
        if (url == null) {
            throw new IllegalStateException("File non trovato: " + CARTELLA_POPUP + nomeFile);
        }
        return new FXMLLoader(url);
    }


    private void mostraMessaggio(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}