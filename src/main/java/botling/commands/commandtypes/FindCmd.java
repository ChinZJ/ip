package botling.commands.commandtypes;

import botling.TaskList;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.commands.ValConstants;
import botling.messagegenerator.MsgGen;

/**
 * Parses find commands.
 */
public class FindCmd implements Command {

    /**
     * Returns a list of all tasks that matches the input string.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        if (input.matches(CmdConst.TASK_FIND.getString())) {
            return MsgGen.find(tasks.find(input.substring(ValConstants.TASK_FIND.getVal())),
                    cmdColor);
        }
        return MsgGen.unknownCmd(cmdColor);
    }
}
