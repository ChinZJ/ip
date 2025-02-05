package botling.commands.commandtypes;

import botling.TaskList;
import botling.commands.CommandColor;
import botling.messagegenerator.MsgGen;

/**
 * When user inputs an unrecognizable string.
 */
public class UnknownCmd {

    /**
     * Returns an unknown command message.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        return MsgGen.unknownCmd(cmdColor);
    }
}
