package botling.commands.commandtypes;

import botling.TaskList;
import botling.TaskListWriter;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.commands.ValConstants;
import botling.messagegenerator.MsgGen;

import java.io.BufferedReader;

/**
 * Parses delete commands.
 */
public class DeleteCmd implements Command {

    /**
     * Deletes task.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        try {
            if (input.matches(CmdConst.TASK_DELETE.getString())) {
                // Recall to convert to 1 based indexing
                // NumberFormatException may be thrown here during int parsing
                int index = Integer.parseInt(
                        input.substring(ValConstants.TASK_DELETE_IDX.getVal()))
                        - ValConstants.TASK_FIX_IDX.getVal();

                // Ensure integer is valid
                if ((index >= 0) && (index < tasks.size())) {
                    String message = tasks.remove(index);
                    TaskListWriter.write(tasks);
                    return MsgGen.delete(message, tasks.size(), cmdColor);
                }
            }
        } catch (NumberFormatException e) {
            // Do nothing. Falls through to unknown syntax.
            // Exception arises from attempting to parse the string as an integer.
        }
        return MsgGen.unknownSyntax(CmdConst.CMD_DELETE.getString(),
                CmdConst.MSG_INVALID_CMD_MARK.getString()
                        + String.valueOf(tasks.size()), cmdColor);
    }

    /**
     * Dummy method.
     */
    public void restore(BufferedReader reader, TaskList tasks) {
        // Do nothing.
    }
}
