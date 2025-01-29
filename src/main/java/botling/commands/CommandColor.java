package botling.commands;

/**
 * Deconstructs messages created by the CommandParser.
 * Used by the GUI to color relevant text.
 */
public class CommandColor {
    private String[] messages;
    private Integer[] lines;

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
     * @param lines that need to be colored
     */
    public CommandColor(Integer[] lines, String... messages) {
        this.messages = messages;
        this.lines = lines;
    }

    /**
     * Resets messages and lines.
      */
    public void reset() {
        messages = new String[0];
        lines = new Integer[0];
    }

    /**
     * Returns true if contains lines to be colored.
     */
    public boolean hasColorLines() {
        if (lines.length > 0) {
            return true;
        }
        return false;
    }

    // Getter for messages
    public String[] getMessages() {
        return messages;
    }

    // Setter for messages
    public void setMessages(String[] messages) {
        this.messages = messages;
    }

    // Getter for lines
    public Integer[] getLines() {
        return lines;
    }

    // Setter for lines
    public void setLines(Integer[] lines) {
        this.lines = lines;
    }

    // Setter for all. Wrapper for above setters.
    public void setAll(String[] messages, Integer[] lines) {
        setMessages(messages);
        setLines(lines);
    }
}
