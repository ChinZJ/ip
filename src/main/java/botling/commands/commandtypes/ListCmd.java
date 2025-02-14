package botling.commands.commandtypes;

import botling.TaskList;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.messagegenerator.MsgGen;

/**
 * Parses list commands.
 */
public class ListCmd implements Command {

    /**
     * Returns a list of all tasks.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        if (input.equals(CmdConst.CMD_LIST.getString()) ||
                input.strip().equals(CmdConst.CMD_LIST.getString())) {
            return MsgGen.list(tasks.list(), cmdColor);
        }
        return MsgGen.unknownCmd(cmdColor);
    }
}
