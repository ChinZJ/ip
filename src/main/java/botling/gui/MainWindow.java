package botling.gui;

import botling.Botling;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Botling botling;

    private Image userImage = new Image(this.getClass()
            .getResourceAsStream("/images/MantaRayUser.png"));
    private Image botlingImage = new Image(this.getClass()
            .getResourceAsStream("/images/TurtleBot.png"));

    /**
     * Used to initialize the main window of the GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Botling instance. */
    public void setBotling(Botling d) {
        botling = d;
    }

    /**
     * Displays the starting message from Botling.
     */
    public void startUp(String message) {
        dialogContainer.getChildren().add(
            DialogBox.getBotlingDialog(message, botlingImage));
    }

    /**
     * Creates two dialog boxes
     * one echoing user input
     * and the other containing Botling's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = botling.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBotlingDialog(response, botlingImage)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
