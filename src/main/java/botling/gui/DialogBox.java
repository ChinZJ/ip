package botling.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a SelectableTextFlow containing selectable and colorable text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private SelectableTextFlow textFlow;
    @FXML
    private ImageView displayPicture;

    /**
     * Default constructor.
     * Two methods of creating a DialogBox (hopefully for slight efficiency).
     *
     * @param text When no color is required, constructs the entire SelectableFlowText.
     * @param img Image to be included in the ImageView.
     * @param colorCodes List of colors for individual text where applicable.
     * @param coloredText List of text which matches the size of colorCodes.
     */
    private DialogBox(String text, Image img, Integer[] colorCodes, String[] coloredText) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Establish TextFlow
        if (colorCodes.length != 0 && colorCodes.length == coloredText.length) {
            try {
                List<Text> styledTexts = new ArrayList<>();
                for (int i = 0; i < coloredText.length; i++) {
                    Text styledText = new Text(coloredText[i]);
                    if (colorCodes[i] == ColorNames.COLOR_STRIKETHROUGH.getIndex()) {
                        styledText.setStrikethrough(true);
                    } else {
                        styledText.setFill(ColorNames.getColor(colorCodes[i]));
                    }
                    styledTexts.add(styledText);
                }
                textFlow.getChildren().addAll(styledTexts);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Check code!" + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Check code!" + e.getMessage());
            }

        } else {
            Text styledText = new Text(text);
            textFlow.getChildren().add(styledText);
        }

        displayPicture.setImage(img);
    }

    /**
     * lips the DialogBox such that the ImageView is on the left and text on the right.
     * Ensures that margins are flipped as well to maintain consistency.
     * Part of Botling's DialogBox generation.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.BOTTOM_LEFT);
        textFlow.getStyleClass().add("reply-text-area");

        // Flip margins for both TextArea and TextFlow
        Insets originalMargins = HBox.getMargin(textFlow);
        Insets flippedMargins = new Insets(
                originalMargins.getTop(),
                originalMargins.getLeft(), // Swap left and right
                originalMargins.getBottom(),
                originalMargins.getRight());
        HBox.setMargin(textFlow, flippedMargins);
    }

    /**
     * Generates a DialogBox object for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        Integer[] dummyInt = new Integer[0];
        String[] dummyString = new String[0];
        return new DialogBox(text, img, dummyInt, dummyString);
    }


    /**
     * Generates DialogBox object for Botling.
     */
    public static DialogBox getBotlingDialog(String text, Image img,
                                             Integer[] lines, String[] messages) {
        var db = new DialogBox(text, img, lines, messages);
        db.flip();
        return db;
    }
}
