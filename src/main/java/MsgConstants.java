public enum MsgConstants {
    // For outputs by Botling.
    MSG_LINE("\t____________________________________________________________\n"),
    MSG_GREET("\t Hello! I'm Botling\n"
            + "\t What can I do for you?\n"),
    MSG_FAREWELL("\t Bye. Hope to see you again soon!\n"),

    MSG_EMPTY_LIST("\t There are currently no tasks!\n"),
    MSG_CURRENT_TASKS("\t Here are the tasks in your list:\n"),
    MSG_ADD("\t Got it. I've added this task: \n"),
    MSG_CURRENT_SIZE_P1("\t Now you have "),
    MSG_CURRENT_SIZE_P2(" tasks in the list.\n"),

    MSG_TASK_DONE("\t Nice! I've marked this task as done:\n"),
    MSG_TASK_UNDONE("\t Ok, I've marked this task as not done yet:\n"),
    MSG_TASK_DELETE("\t Noted. I've removed this task: \n"),

    MSG_EMPTY("EMPTY"),

    // For unexpected inputs
    MSG_INVALID_CMD_P1("\t OOPS!!! The format of "),
    MSG_INVALID_CMD_P2(" should be "),
    MSG_INVALID_CMD_MARK(" <X>, where X is a positive integer <= "),
    MSG_INVALID_CMD_TODO(" <name>.\n"),
    MSG_INVALID_CMD_DEADLINE(" <name> /by <deadline>.\n"),
    MSG_INVALID_CMD_EVENT(" <name> /from <start> /to <end>.\n"),
    MSG_INVALID_UNKNOWN("\t OOPS!!! This command does not exist(yet).\n"),

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
    TASK_EVENT("event .+ /from .+ /to .+");

    private final String message;

    MsgConstants(String message) {
        this.message = message;
    }

    public String getString() {
        return message;
    }
}
