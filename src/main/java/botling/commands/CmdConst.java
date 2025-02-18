package botling.commands;

/**
 * Commands used by Botling.
 * Used in parsing command types.
 */
public enum CmdConst {
    // Unique command types.
    CMD_BY(" /by "),
    CMD_BYE("bye"),
    CMD_DEADLINE("deadline"),
    CMD_DELETE("delete"),
    CMD_EVENT("event"),
    CMD_FIND("find"),
    CMD_FROM(" /from "),
    CMD_HELP("help"),
    CMD_LIST("list"),
    CMD_MARK("mark"),
    CMD_TO(" /to "),
    CMD_TODO("todo"),
    CMD_UNMARK("unmark"),

    // Tasks regex.
    TASK_DEADLINE("deadline .+ /by .+"),
    TASK_DELETE("delete -?\\d+"),
    TASK_EVENT("event .+ /from .+ /to .+"),
    TASK_FIND("find .+"),
    TASK_MARK("mark -?\\d+"),
    TASK_TODO("todo .+"),
    TASK_UNMARK("unmark -?\\d+"),

    // Expected Syntax.
    MSG_INVALID_CMD_DATE("If using a date, the accepted format are as follows:\n"
            + "'yy(yy)-MM-dd ', 'dd/MM/yy(yy)' or 'dd MMM yy(yy)'"
            + "(month in short form e.g. Jan), "
            + "optionally with HHmm in 24hour format.\n"
            + "Else, normal text is accepted as well!"),
    MSG_INVALID_CMD_DEADLINE(" <name> /by <deadline (may be date)>.\n"),
    MSG_INVALID_CMD_EVENT(" <name> /from <start (may be date)> /to <end (may be date)>.\n"),
    MSG_INVALID_CMD_EVENT_DATE("<start> should be before or equal to <end>"
            + " if dates are inputs.\n"),
    MSG_INVALID_CMD_INDEX(" <X>, where X is a positive integer <= "),
    MSG_INVALID_CMD_INDEX_EMPTY_LIST(" <X>, though you have no tasks!"),
    MSG_INVALID_CMD_TODO(" <name>."),

    // For corrupted files.
    CORRUPT_DELETE("History data has been reset!"),
    CORRUPT_PAUSE("Program will now terminate. Please check the file.");

    private final String message;

    /**
     * Default constructor.
     */
    CmdConst(String message) {
        this.message = message;
    }

    /**
     * Returns message in String type.
     */
    public String getString() {
        return message;
    }

}
