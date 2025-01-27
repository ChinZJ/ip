package botling.gui;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {

    @FXML
    private TextArea dialog;
    @FXML
    private ImageView displayPicture;
    private Text textNode;

    private Group selectionGroup;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        dialog.setText(text);
        // Issue with TextArea is that it has scroll bars,
        // and its size does not seem to be dynamic as well.
        dialog.skinProperty().addListener(it -> {
            if (dialog.getSkin() != null) {
                textAreaResize();
            }
        });
        displayPicture.setImage(img);
    }

    /**
     * Reconfigure the TextArea in the GUI.
     */
    private void textAreaResize() {
        // First is to disable scrollbars.
        // TextArea has both horizontal and vertical.
        // Adapted from:
        // https://github.com/dlsc-software-consulting-gmbh/GemsFX/tree/master
        // Unsure why the scroll does not work though.
        // Look into it in the future.
        ScrollPane scrollPane = (ScrollPane) dialog.lookup(".scroll-pane");
        if (scrollPane != null) {
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            // Checks if text has been added.
            // Else tries to find the Text Node.
            // Once found, resizes TextArea height.
            scrollPane.skinProperty().addListener(it -> {
                if (scrollPane.getSkin() != null) {
                    if (textNode == null) {
                        textNode = findTextNode();
                        if (textNode != null) {
                            dialog.prefHeightProperty().bind(Bindings
                                    .createDoubleBinding(() -> computeHeight(textNode),
                                            textNode.layoutBoundsProperty()));
                        }
                    }
                }
            });
        }
    }

    /**
     * Adapted from:
     * https://github.com/dlsc-software-consulting-gmbh/GemsFX/tree/master
     * Helper method to textAreaResize().
     * Finds the Text Node for TextArea object to resize to.
     * "We need to find the node of type Text that is owned by a Group because
     * there might be two Text instances if a prompt text has been specified."
     */
    private Text findTextNode() {
        final Set<Node> nodes = lookupAll(".text");
        for (Node node : nodes) {
            if (node.getParent() instanceof Group) {
                return (Text) node;
            }
        }
        return null;
    }

    /**
     * Adapted from:
     * https://github.com/dlsc-software-consulting-gmbh/GemsFX/tree/master
     * Helper method to textAreaResize().
     * Calculates the height required for resizing.
     */
    private double computeHeight(Text textNode) {
        double offsetTop = dialog.getInsets().getTop();
        double offsetBottom = dialog.getInsets().getBottom();

        ScrollPane scrollPane = (ScrollPane) dialog.lookup(".scroll-pane");
        if (scrollPane != null) {
            Region viewport = (Region) scrollPane.lookup(".viewport");
            Region content = (Region) scrollPane.lookup(".content");

            offsetTop += viewport.getInsets().getTop();
            offsetTop += content.getInsets().getTop();

            offsetBottom += viewport.getInsets().getBottom();
            offsetBottom += content.getInsets().getBottom();
        }

        Bounds layoutBounds = localToScreen(textNode.getLayoutBounds());
        if (layoutBounds != null) {
            double minY = layoutBounds.getMinY();
            double maxY = layoutBounds.getMaxY();

            return maxY - minY + offsetTop + offsetBottom;
        }

        return 0;
    }

    /**
     * Part of processing for Botling's DialogBox.
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.BOTTOM_LEFT);
        dialog.getStyleClass().add("reply-text-area");

        // Get current margins to flip as well
        Insets currentMargins = HBox.getMargin(dialog);
        HBox.setMargin(dialog, new Insets(
                currentMargins.getTop(),
                currentMargins.getLeft(),  // Swap left and right margins
                currentMargins.getBottom(),
                currentMargins.getRight()  // Swap left and right margins
        ));
    }

    /**
     * Generates a DialogBox object for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    private void findSelectionGroup() {
        Region content = (Region) dialog.lookup(".content");
        if (content != null) {
            // Find the selection group in TextArea
            content.getChildrenUnmodifiable().stream()
                    .filter(node -> node instanceof Group)
                    .map(node -> (Group) node)
                    .filter(grp -> {
                        boolean notSelectionGroup = grp.getChildren().stream()
                                .anyMatch(node -> !(node instanceof Path));
                        return !notSelectionGroup;
                    })
                    .findFirst()
                    .ifPresent(n -> selectionGroup = n);
        }
    }

    /**
     * Generates DialogBox object for Botling.
     */
    public static DialogBox getBotlingDialog(String text, Image img,
                                             String commandType, String[] messages, Integer[] lines) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }

}