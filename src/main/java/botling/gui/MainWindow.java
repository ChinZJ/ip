package botling.gui;

import botling.Botling;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controls the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextArea userInput;
    @FXML
    private Button sendButton;
    private Botling botling;
    private Image userImage = new Image(this.getClass()
            .getResourceAsStream("/images/MantaRayUser.png"));
    private Image botlingImage = new Image(this.getClass()
            .getResourceAsStream("/images/TurtleBot.png"));

    /**
     * Initializes the main window of the GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects Botling instance.
     */
    public void setBotling(Botling d) {
        botling = d;
    }

    /**
     * Displays the start message from Botling.
     */
    public void startUp(String message) {
        dialogContainer.getChildren().add(
            DialogBox.getBotlingDialog(botlingImage,
                    botling.getLines(), botling.getMessages()));
    }

    /**
     * Generates two dialog boxes
     * one echoing user input
     * and the other containing Botling's reply and then appends them to
     * the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        // Handles the \n that comes with the input
        input = input.substring(0, input.length() - 1);
        botling.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBotlingDialog(botlingImage,
                        botling.getLines(), botling.getMessages()));
        userInput.clear();

        if (!botling.isOpen()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }

    @FXML
    private void handleSendButton() {
        userInput.appendText("\n");
        handleUserInput();
    }

    /**
     * Keypress for TextArea object.
     */
    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
            event.consume();
            handleUserInput();
        }
    }
}
