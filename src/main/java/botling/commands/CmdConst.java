package botling.commands;

/**
 * Commands used by Botling.
 * Used in parsing commandtypes.
 */
public enum CmdConst {
    // Unique commandtypes.
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
    MSG_INVALID_CMD_DATE("Date: 'yy(yy)-MM-dd HHmm', 'dd/MM/yy(yy)' or 'dd MMM yy(yy)', "
            + "optionally with HHmm in 24hour format."),
    MSG_INVALID_CMD_DEADLINE(" <name> /by <deadline>.\n"),
    MSG_INVALID_CMD_EVENT(" <name> /from <start> /to <end>.\n"),
    MSG_INVALID_CMD_EVENT_DATE(" <start> should be before or equal to <end>"
            + " if dates are inputs.\n"),
    MSG_INVALID_CMD_MARK(" <X>, where X is a positive integer <= "),
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
