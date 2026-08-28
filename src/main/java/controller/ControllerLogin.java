package controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;

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
    public void initialize(){
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

}
