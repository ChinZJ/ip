package botling.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import botling.TaskList;
import botling.commands.commandtypes.ByeCmd;
import botling.commands.commandtypes.Command;
import botling.commands.commandtypes.CorruptCmd;
import botling.commands.commandtypes.DeadlineCmd;
import botling.commands.commandtypes.DeleteCmd;
import botling.commands.commandtypes.EventCmd;
import botling.commands.commandtypes.FindCmd;
import botling.commands.commandtypes.HelpCmd;
import botling.commands.commandtypes.ListCmd;
import botling.commands.commandtypes.MarkCmd;
import botling.commands.commandtypes.StartCmd;
import botling.commands.commandtypes.TodoCmd;
import botling.commands.commandtypes.UnknownCmd;
import botling.commands.commandtypes.UnmarkCmd;

/**
 * Parses user input and executes downstream commands.
 */
public class CommandParser {
    private static final String DUMMY_STRING = "";
    private static final Map<String, Command> COMMANDS;

    static {
        Map<String, Command> tempMap = new HashMap<>();
        tempMap.put(CmdConst.CMD_HELP.getString(), new HelpCmd());
        tempMap.put(CmdConst.CMD_BYE.getString(), new ByeCmd());
        tempMap.put(CmdConst.CMD_LIST.getString(), new ListCmd());
        tempMap.put(CmdConst.CMD_FIND.getString(), new FindCmd());
        tempMap.put(CmdConst.CMD_MARK.getString(), new MarkCmd());
        tempMap.put(CmdConst.CMD_UNMARK.getString(), new UnmarkCmd());
        tempMap.put(CmdConst.CMD_DELETE.getString(), new DeleteCmd());
        tempMap.put(CmdConst.CMD_TODO.getString(), new TodoCmd());
        tempMap.put(CmdConst.CMD_DEADLINE.getString(), new DeadlineCmd());
        tempMap.put(CmdConst.CMD_EVENT.getString(), new EventCmd());
        COMMANDS = Collections.unmodifiableMap(tempMap);
    }

    /**
     * Generates start up message and checks for any history.
     *
     * @param tasks TaskList to restore progress if any.
     * @return message for Botling, inclusive of startup amd if any history is recovered.
     */
    public static String start(TaskList tasks, CommandColor cmdColor) {
        return new StartCmd().parse(DUMMY_STRING, tasks, cmdColor);
    }

    /**
     * Main method for parsing user input.
     * Invalid inputs will throw an InvalidInputException.
     *
     * @param input User input. May be valid or invalid
     * @param tasks TaskList containing list of tasks.
     * @param cmdColor CommandColor for coloring text in GUI.
     * @return Message for Botling to pass messages to UI object to handle.
     */
    public static String parse(String input, TaskList tasks, CommandColor cmdColor) {
        cmdColor.reset(); // Refresh the instantiation

        if (tasks.isOpen()) {
            return COMMANDS.entrySet().stream()
                    .filter(entry -> input.startsWith(entry.getKey()))
                    .findFirst()
                    .map(entry -> entry.getValue().parse(input, tasks, cmdColor))
                    .orElseGet(() -> new UnknownCmd().parse(input, tasks, cmdColor));
        }
        return new CorruptCmd().parse(input, tasks, cmdColor);
    }
}
