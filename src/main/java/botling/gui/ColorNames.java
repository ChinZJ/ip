package botling.gui;

import javafx.scene.paint.Color;

/**
 * Constants for colors for TextFlow object.
 * Since manipulation is required, a custom class is created instead.
 */
public enum ColorNames {

    COLOR_BLACK(0),
    COLOR_STRIKETHROUGH(-1),
    COLOR_GREEN(1),
    COLOR_RED(2);

    private static final Color[] COLORS = new Color[]{Color.BLACK,
            Color.rgb(0, 100, 0), // Pastel green
            Color.rgb(139, 34, 34)}; // Pastel red
    private final int index;

    /**
     * Default constructor.
     */
    ColorNames(int index) {
        this.index = index;
    }

    /**
     * Returns color in String type.
     */
    public static Color getColor(int index) {
        return ColorNames.COLORS[index];
    }

    public int getIndex() {
        return index;
    }
}
