package botling.commands.commandtypes;

import botling.TaskList;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.messagegenerator.MsgGen;

import java.io.BufferedReader;

/**
 * Parses bye commands.
 */
public class ByeCmd implements Command {

    /**
     * Closes the <code>TaskList</code> and returns a goodbye message.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        if (input.equals(CmdConst.CMD_BYE.getString())) {
            tasks.hasClose();
            return MsgGen.bye(cmdColor);
        }
        return MsgGen.unknownCmd(cmdColor);
    }

    /**
     * Dummy method.
     */
    public void restore(BufferedReader reader, TaskList tasks) {
        // Do nothing.
    }
}
