package botling.commands.commandtypes;

import botling.TaskList;
import botling.TaskListWriter;
import botling.commands.CommandColor;
import botling.messagegenerator.MsgGen;

/**
 * Run on program startup.
 */
public class StartCmd implements Command {

    /**
     * Loads the history file where applicable.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        String message = TaskListWriter.restore(tasks);
        if (tasks.isOpen()) {
            return (MsgGen.greet(message, cmdColor));
        }
        return MsgGen.wrap(message, cmdColor);
    }
}
