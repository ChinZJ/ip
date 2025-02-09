package botling.commands.commandtypes;

import botling.TaskList;
import botling.commands.CommandColor;

/**
 * Command interface for all commands to implement.
 */
public interface Command {
    /**
     * Parses the command from the user input.
     * @param input User input provided by the user.
     * @param tasks The <code>TaskList</code> object used to perform operations on tasks.
     * @param cmdColor Text meant for GUI.
     * @return Message string used in JUnit testing.
     */
    String parse(String input, TaskList tasks, CommandColor cmdColor);
}
