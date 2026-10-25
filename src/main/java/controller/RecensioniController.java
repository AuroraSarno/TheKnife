package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


public class RecensioniController {

    public void hand(MouseEvent mouseEvent) {
    }

    public record RecensioneItem(String ristorante, int stelle, String testo) {
    }

    @FXML
    private ListView<RecensioneItem> listViewRecensioni;

    @FXML
    private void initialize() {
        listViewRecensioni.setPlaceholder(new Label("Non hai ancora scritto recensioni"));

        listViewRecensioni.setCellFactory(param -> new ListCell<RecensioneItem>() {
            @Override
            protected void updateItem(RecensioneItem recensione, boolean empty) {
                super.updateItem(recensione, empty);
                if (empty || recensione == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox box = new VBox(3);
                    box.setPadding(new Insets(5));

                    Label ristoranteLabel = new Label(recensione.ristorante());
                    ristoranteLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                    Label stelleLabel = new Label("⭐ " + recensione.stelle());

                    // Il testo va a capo e non supera la larghezza della lista (il pop-up è stretto)
                    Label testoLabel = new Label(recensione.testo());
                    testoLabel.setWrapText(true);
                    testoLabel.maxWidthProperty().bind(listViewRecensioni.widthProperty().subtract(45));

                    box.getChildren().addAll(ristoranteLabel, stelleLabel, testoLabel);
                    setGraphic(box);
                }
            }
        });
    }

    public void setRecensioni(List<RecensioneItem> recensioni) {
        listViewRecensioni.setItems(FXCollections.observableArrayList(recensioni));
    }

    @FXML
    private void chiudi() {
        ((Stage) listViewRecensioni.getScene().getWindow()).close();
    }
}