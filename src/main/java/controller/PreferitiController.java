package controller;

import ServerTK.Modelli.Ristorante;
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


public class PreferitiController {

    @FXML
    private ListView<Ristorante> listViewPreferiti;

    @FXML
    private void initialize() {
        listViewPreferiti.setPlaceholder(new Label("Non hai ancora ristoranti preferiti"));

        listViewPreferiti.setCellFactory(param -> new ListCell<Ristorante>() {
            @Override
            protected void updateItem(Ristorante ristorante, boolean empty) {
                super.updateItem(ristorante, empty);
                if (empty || ristorante == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox box = new VBox(3);
                    box.setPadding(new Insets(5));

                    Label nomeLabel = new Label(ristorante.getNome());
                    nomeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                    Label cucinaLabel = new Label("Cucina: " + ristorante.getTipoCucina());
                    Label stelleLabel = new Label("⭐ " + ristorante.getStelle());

                    box.getChildren().addAll(nomeLabel, cucinaLabel, stelleLabel);
                    setGraphic(box);
                }
            }
        });
    }

    public void setPreferiti(List<Ristorante> preferiti) {
        listViewPreferiti.setItems(FXCollections.observableArrayList(preferiti));
    }

    @FXML
    private void chiudi() {
        ((Stage) listViewPreferiti.getScene().getWindow()).close();
    }

    @FXML
    private void hand(MouseEvent mouseEvent) {
    }
}