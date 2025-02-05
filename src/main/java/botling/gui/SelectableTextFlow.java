package botling.gui;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Custom class to enable selectable and colorable text.
 * Adapted from:
 * https://stackoverflow.com/questions/79395124/
 * javafx-overlaying-textflow-with-textarea?noredirect=1#comment140023326_79395124
 */
public class SelectableTextFlow extends TextFlow {
    private TextArea textArea;

    /**
     * Default constructor.
     * Programmatically creates textArea.
     * A .fxml file is not created due to the hardcoding required (see layoutChildren()).
     */
    public SelectableTextFlow() {
        getStyleClass().add("selectable-text-flow");
        getStylesheets().add(getClass()
                .getResource("/CSS/selectable-text-flow.css").toExternalForm());
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setManaged(false);
        textArea.setEditable(false);

        getChildren().add(textArea);
    }

    /**
     * Makes text selectable.
     * Copies all text and overlays the TextFlow object.
     */
    @Override
    protected void layoutChildren() {
        StringBuilder txt = new StringBuilder();
        List<Node> managed = getManagedChildren();
        for (Node node : managed) {
            if (node instanceof Text) {
                Text text = (Text) node;
                txt.append(text.getText());
            }
        }
        textArea.setText(txt.toString());
        textArea.toFront();
        super.layoutChildren();

        textArea.setLayoutX(6); // hardcoded
        textArea.setLayoutY(6); // hardcoded
        textArea.resize(getWidth() - 11.8, getHeight()); // hardcoded
        ScrollBar scrollBarv = (ScrollBar)textArea.lookup(".scroll-bar:vertical");
        scrollBarv.setDisable(true);
    }
}
