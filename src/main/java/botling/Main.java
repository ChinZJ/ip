package botling;

import java.io.IOException;

import botling.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
            stage.setTitle("Botling");
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setBotling(botling); // Inject Botling instance
            mainWindow.startUp(botling.startUp()); // Start with Botling start message

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
