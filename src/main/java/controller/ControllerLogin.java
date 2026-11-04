package controller;
import ClientTK.ConnessioneRMI;
import ClientTK.Sessione;
import ClientTK.TheKnifeInterface;
import ServerTK.Modelli.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLogin {
    @FXML
    private PasswordField campoSegreto;
    @FXML
    private TextField campoInChiaro;
    @FXML
    private ToggleButton btnOcchio;
    @FXML
    private ImageView iconaOcchio;
    @FXML
    private Button btnIndietro;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField campoUsername;
    @FXML
    private Text erroreUsername;
    @FXML
    private Text errorePassword;

    @FXML
    public void initialize(){
        //rendo invisibili i testi di errore
        erroreUsername.setVisible(false);
        errorePassword.setVisible(false);
        //i campi testo avranno sempre stesso valore
        campoInChiaro.textProperty().bindBidirectional(campoSegreto.textProperty());
        //apertura app mostro il testo segreto
        campoInChiaro.setVisible(false);
        campoSegreto.setVisible(true);

        //immagini che si scambieranno tra loro
        Image occhioAperto = new Image(getClass().getResourceAsStream("/immagini/occhioAperto.png"));
        Image occhioChiuso = new Image(getClass().getResourceAsStream("/immagini/occhioChiuso.png"));
        iconaOcchio.setImage(occhioAperto);
        btnOcchio.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        btnOcchio.setOnAction((event) -> {
            if(btnOcchio.isSelected()){
                campoInChiaro.setVisible(true);
                campoSegreto.setVisible(false);
                iconaOcchio.setImage(occhioChiuso);
            }else{
                campoInChiaro.setVisible(false);
                campoSegreto.setVisible(true);
                iconaOcchio.setImage(occhioAperto);
            }
        });
    }

    public void actionIndietro(ActionEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/graficaFXML/autenticazione/Benvenuto.fxml"));
        Scene loginScene = new Scene(loginRoot);

        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        currentStage.setScene(loginScene);
        currentStage.show();
    }

    public void actionLogin(ActionEvent event) throws IOException {
        String username = campoUsername.getText();
        String password = campoInChiaro.getText();
        if(username.isEmpty() || password.isEmpty()){
            erroreUsername.setVisible(true);
            errorePassword.setVisible(true);
        }else{
            TheKnifeInterface theKnife= ConnessioneRMI.getServer();
            Utente loggato= theKnife.login(username,password);
            if(loggato==null){
                erroreUsername.setVisible(true);
                errorePassword.setVisible(true);
            }else{
                Sessione.getInstance().setUtenteLoggato(loggato);
                if(loggato.getRuolo()== Utente.Ruolo.CLIENTE){
                   Parent loginRoot = FXMLLoader.load(getClass().getResource("/graficaFXML/ricerca/Ricerca.fxml"));
                   Scene loginScene = new Scene(loginRoot);
                   Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                   currentStage.setScene(loginScene);
                   currentStage.show();
               }else{
                   Parent loginRoot = FXMLLoader.load(getClass().getResource("/graficaFXML/ristoratore/AreaRistoratore.fxml"));
                   Scene loginScene = new Scene(loginRoot);
                   Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                   currentStage.setScene(loginScene);
                   currentStage.show();
               }
            }
        }
    }

}
