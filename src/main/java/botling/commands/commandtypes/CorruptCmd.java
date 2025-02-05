package botling.commands.commandtypes;

import botling.TaskList;
import botling.TaskListWriter;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.messagegenerator.MsgGen;

import java.io.BufferedReader;

/**
 * Called when history file is corrupt.
 */
public class CorruptCmd implements Command {
    private static final String INPUT_YES = "y";
    private static final String INPUT_NO = "n";

    /**
     * Checks if input is valid and performs respective actions.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        if (input.equals(INPUT_YES)) {
            tasks.clear();
            TaskListWriter.recreateFile();
            tasks.hasOpen();
            return MsgGen.greet(CmdConst.CORRUPT_DELETE.getString(), cmdColor);
        } else if (input.equals(INPUT_NO)) {
            return MsgGen.wrap(CmdConst.CORRUPT_PAUSE.getString(), cmdColor);
        }
        return MsgGen.unknownCorrupt(cmdColor);
    }

    /**
     * Dummy method.
     */
    public void restore(BufferedReader reader, TaskList tasks) {
        // Do nothing.
    }
}
