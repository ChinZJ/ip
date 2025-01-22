package botling.Commands;

public enum CmdConst {
    // Unique Commands.
    CMD_BYE("bye"),
    CMD_LIST("list"),
    CMD_MARK("mark"),
    CMD_UNMARK("unmark"),
    CMD_DELETE("delete"),
    CMD_TODO("todo"),
    CMD_DEADLINE("deadline"),
    CMD_EVENT("event"),
    CMD_BY(" /by "),
    CMD_FROM(" /from "),
    CMD_TO(" /to "),

    // Tasks regex.
    TASK_MARK("mark -?\\d+"),
    TASK_UNMARK("unmark -?\\d+"),
    TASK_DELETE("delete -?\\d+"),
    TASK_TODO("todo .+"),
    TASK_DEADLINE("deadline .+ /by .+"),
    TASK_EVENT("event .+ /from .+ /to .+"),

    // Expected Syntax
    MSG_INVALID_CMD_MARK(" <X>, where X is a positive integer <= "),
    MSG_INVALID_CMD_TODO(" <name>."),
    MSG_INVALID_CMD_DEADLINE(" <name> /by <deadline>.\n"),
    MSG_INVALID_CMD_EVENT(" <name> /from <start> /to <end>.\n"),
    MSG_INVALID_CMD_EVENT_DATE("\t <start> should be before or equal to <end> if dates are inputs.\n"),
    MSG_INVALID_CMD_DATE("\t Date: 'yyyy-MM-dd HHmm', 'yyyy-MM-dd' or 'dd MMM yyyy HHmm'");

    private final String message;

    /**
     * Default constructor
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
