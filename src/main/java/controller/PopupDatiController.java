package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class PopupDatiController {

    public void Annulla(ActionEvent actionEvent) {
    }

    public record DatiModificati(String nome, String cognome, String nuovaPassword, String citta) {
    }

    private static final int LUNGHEZZA_MINIMA_PASSWORD = 8;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField cognomeField;

    @FXML
    private ComboBox<String> cittaField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confermaPasswordField;

    @FXML
    private Label erroreLabel;

    private DatiModificati risultato;


    public void setDati(String nome, String cognome, String citta, List<String> cittaDisponibili) {
        nomeField.setText(nome);
        cognomeField.setText(cognome);
        cittaField.setItems(FXCollections.observableArrayList(cittaDisponibili));
        cittaField.setValue(citta);
    }

    public Optional<DatiModificati> getRisultato() {
        return Optional.ofNullable(risultato);
    }

    @FXML
    void salva(ActionEvent event) {
        String nome = nomeField.getText() == null ? "" : nomeField.getText().trim();
        String cognome = cognomeField.getText() == null ? "" : cognomeField.getText().trim();
        String citta = cittaField.getValue() == null ? "" : cittaField.getValue().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();
        String conferma = confermaPasswordField.getText() == null ? "" : confermaPasswordField.getText();

        if (nome.isEmpty() || cognome.isEmpty()) {
            erroreLabel.setText("Nome e cognome non possono essere vuoti.");
            return;
        }
        if (citta.isEmpty()) {
            erroreLabel.setText("Seleziona una città.");
            return;
        }
        if (!password.isEmpty()) {
            if (password.length() < LUNGHEZZA_MINIMA_PASSWORD) {
                erroreLabel.setText("La password deve avere almeno " + LUNGHEZZA_MINIMA_PASSWORD + " caratteri.");
                return;
            }
            if (!password.equals(conferma)) {
                erroreLabel.setText("Le due password non coincidono.");
                return;
            }
        }

        risultato = new DatiModificati(nome, cognome, password.isEmpty() ? null : password, citta);
        chiudiFinestra();
    }

    @FXML
    void chiudi(ActionEvent event) {
        chiudiFinestra();
    }

    private void chiudiFinestra() {
        ((Stage) nomeField.getScene().getWindow()).close();
    }
}