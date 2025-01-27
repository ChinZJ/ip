package botling;

import botling.commands.CommandParser;

/**
 * Main class where application starts.
 */
public class Botling {
    private TaskList tasks = new TaskList();

    /**
     * Generates the start message.
     * Includes loading of history with user.
     */
    public String startUp() {
        return CommandParser.start(tasks);
    }

    /**
     * Generates a response for the user's chat message
     * Adapted from main method.
     */
    public String getResponse(String input) {
        return CommandParser.parse(input, tasks);
    }
}
