package botling.commands;

/**
 * Deconstructs messages created by the CommandParser.
 * Used by the GUI to color relevant text.
 */
public class CommandColor {
    private String[] messages;
    private Integer[] colorLines;

    /**
     * Additional constructor.
     * Used in tests.
     */
    public CommandColor() {
        this(new Integer[0]);
    }

    /**
     * Default constructor.
     * @param messages that are separated by lines.
     * @param colorLines that need to be colored
     */
    public CommandColor(Integer[] colorLines, String... messages) {
        this.messages = messages;
        this.colorLines = colorLines;
    }

    /**
     * Resets messages and colorLines.
     */
    public void reset() {
        messages = new String[0];
        colorLines = new Integer[0];
    }

    /**
     * Returns true if contains colorLines to be colored.
     */
    public boolean hasColorLines() {
        if (colorLines.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * Getter for messages.
     */
    public String[] getMessages() {
        return messages;
    }

    /**
     * Setter for messages.
     */
    public void setMessages(String[] messages) {
        this.messages = messages;
    }

    /**
     * Getter for colorLines.
     */
    public Integer[] getLines() {
        return colorLines;
    }

    /**
     * Setter for colorLines.
     */
    public void setLines(Integer[] colorLines) {
        this.colorLines = colorLines;
    }

    /**
     * Sets both attributes at once.
     * Wrapper for above setters.
     */
    public void setAll(String[] messages, Integer[] colorLines) {
        setMessages(messages);
        setLines(colorLines);
    }
}
