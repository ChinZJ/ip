package botling.commands.commandtypes;

import botling.TaskList;
import botling.TaskListWriter;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.commands.ValConstants;
import botling.messagegenerator.MsgGen;

/**
 * Parses unmark commands.
 */
public class UnmarkCmd implements Command {

    /**
     * Unmarks task.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        try {
            if (input.matches(CmdConst.TASK_UNMARK.getString())) {
                int index = Integer.parseInt(input.substring(ValConstants.TASK_UNMARK_IDX.getVal()))
                        - ValConstants.TASK_FIX_IDX.getVal();
                if ((index >= 0) && (index < tasks.size())) {
                    String message = tasks.unmark(index);
                    TaskListWriter.write(tasks);
                    return MsgGen.unmark(message, cmdColor);
                }
            }
        } catch (NumberFormatException e) {
            // Do nothing. Falls through to unknown syntax.
            // Exception arises from attempting to parse the string as an integer.
        }

        if (tasks.size() != 0) {
            return MsgGen.unknownSyntax(CmdConst.CMD_UNMARK.getString(),
                    CmdConst.MSG_INVALID_CMD_MARK.getString()
                            + String.valueOf(tasks.size()), cmdColor);
        }

        return MsgGen.emptyList(cmdColor);
    }
}
