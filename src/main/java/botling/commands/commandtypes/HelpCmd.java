package botling.commands.commandtypes;

import botling.TaskList;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.messagegenerator.MsgGen;

/**
 * Parses help commands.
 */
public class HelpCmd implements Command {

    /**
     * Returns a list of all available commands.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        if (input.equals(CmdConst.CMD_HELP.getString())) {
            String message = CmdConst.CMD_BYE.getString() + "\n\n"
                    + CmdConst.CMD_LIST.getString() + "\n\n"
                    + CmdConst.CMD_FIND.getString() + CmdConst.MSG_INVALID_CMD_TODO.getString()
                    + "\n\n" + CmdConst.CMD_MARK.getString() + " / "
                    + CmdConst.CMD_UNMARK.getString()
                    + " / " + CmdConst.CMD_DELETE.getString()
                    + CmdConst.MSG_INVALID_CMD_MARK.getString() + tasks.size() + "\n\n"
                    + CmdConst.CMD_TODO.getString() + CmdConst.MSG_INVALID_CMD_TODO.getString()
                    + "\n\n" + CmdConst.CMD_DEADLINE.getString()
                    + CmdConst.MSG_INVALID_CMD_DEADLINE.getString() + "\n\n"
                    + CmdConst.CMD_EVENT.getString() + CmdConst.MSG_INVALID_CMD_EVENT.getString();
            return MsgGen.wrap(message, cmdColor);
        }
        return MsgGen.unknownCmd(cmdColor);
    }
}
