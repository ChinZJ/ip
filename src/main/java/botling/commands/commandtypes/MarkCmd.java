package botling.commands.commandtypes;

import botling.TaskList;
import botling.TaskListWriter;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.commands.ValConstants;
import botling.messagegenerator.MsgGen;

import java.io.BufferedReader;

/**
 * Parses mark commands.
 */
public class MarkCmd implements Command {

    /**
     * Marks task as complete.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        if (input.matches(CmdConst.TASK_MARK.getString())) {
            try {
                int index = Integer.parseInt(input.substring(ValConstants.TASK_MARK_IDX.getVal()))
                        - ValConstants.TASK_FIX_IDX.getVal();
                if ((index >= 0) && (index < tasks.size())) {
                    String message = tasks.mark(index);
                    TaskListWriter.write(tasks);
                    return MsgGen.mark(message, cmdColor);
                }
            } catch (NumberFormatException e) {
                // Do nothing. Falls through to unknown syntax.
                // Exception arises from attempting to parse the string as an integer.
            }
        }
        return MsgGen.unknownSyntax(CmdConst.CMD_MARK.getString(),
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
