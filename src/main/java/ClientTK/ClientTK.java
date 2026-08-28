package ClientTK;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientTK extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            Registry registry = LocateRegistry.getRegistry("localhost", 8888);
            TheKnifeInterface obj = (TheKnifeInterface) registry.lookup("TheKnife");
            //salvo l'oggetto remoto in una classe
            ConnessioneRMI.setServer(obj);
            //Avvio la grafica del client
            Parent root = FXMLLoader.load(getClass().getResource("/Benvenuto.fxml"));
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.show();
        }
}
