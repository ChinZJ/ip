package botling;

import botling.commands.CommandColor;
import botling.commands.CommandParser;

/**
 * Main class where application starts.
 */
public class Botling {
    private TaskList tasks ;
    private CommandColor cmdColor;

    /**
     * Default constructor.
     */
    public Botling() {
        tasks = new TaskList();
        cmdColor = new CommandColor(new Integer[0]);
    }

    /**
     * Generates the start message.
     * Includes loading of history with user.
     */
    public String startUp() {
        return CommandParser.start(tasks);
    }

    /**
     * Retrieves command type.
     */
    public String getCommandType(String input) {
        return CommandParser.parseCommand(input);
    }

    /**
     * Generates a response for the user's chat message
     * Adapted from main method.
     */
    public String getResponse(String input) {
        return CommandParser.parse(input, tasks, cmdColor);
    }

    /**
     * Getter for messages in CommandColor.
     * Carrying forward to MainWindow.java
     */
    public String[] getMessages() {
        return cmdColor.getMessages();
    }

    /**
     * Getter for lines in CommandColor.
     * Carrying forward to MainWindow.java
     */
    public Integer[] getLines() {
        return cmdColor.getLines();
    }
}
