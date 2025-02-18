package botling.commands.commandtypes;

import botling.TaskList;
import botling.TaskListWriter;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.commands.ValConstants;
import botling.messagegenerator.MsgGen;

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

        if (tasks.size() != 0) {
            return MsgGen.unknownSyntax(CmdConst.CMD_MARK.getString(),
                    CmdConst.MSG_INVALID_CMD_INDEX.getString()
                            + String.valueOf(tasks.size()), cmdColor);
        }
        return MsgGen.unknownSyntax(CmdConst.CMD_MARK.getString(),
                CmdConst.MSG_INVALID_CMD_INDEX_EMPTY_LIST.getString()
                        + "", cmdColor);
    }
}
