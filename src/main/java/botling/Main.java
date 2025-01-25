package botling;

import botling.gui.MainWindow;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A GUI for Botling using FXML.
 */
public class Main extends Application {

    private Botling botling = new Botling();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            // Load up Botling start message
            // fxmlLoader.<MainWindow>getController().setBotling(botling);  // inject the Botling instance - from demo
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setBotling(botling);
            mainWindow.startUp(botling.startUp());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
