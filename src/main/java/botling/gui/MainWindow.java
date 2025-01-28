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
 * Controller for the main GUI.
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
            DialogBox.getBotlingDialog(message, botlingImage,
                    "", botling.getLines(), botling.getMessages()));
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
        // Handles the \n that comes with the input
        input = input.substring(0, input.length() - 1);
        System.out.println(input);
        String commandType = botling.getCommandType(input);

        String response = botling.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBotlingDialog(response, botlingImage,
                        commandType, botling.getLines(), botling.getMessages()));
        userInput.clear();

        if (input.equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }

    /**
     * Keypress for TextArea
     */
    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
            event.consume();
            handleUserInput();
        }
    }
}
