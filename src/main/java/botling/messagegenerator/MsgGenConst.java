package botling.messagegenerator;

/**
 * Message constants used to generate Botling's replies.
 */
public enum MsgGenConst {
    // For greetings and farewells.
    MSG_FAREWELL("Bye. Hope to see you again soon!"),
    MSG_GREET("Hello! I'm Botling!\n"
            + "What can I do for you?"),

    // For list / find.
    MSG_ADD("Got it. I've added this task: \n"),
    MSG_CURRENT_SIZE_P1("\nNow you have "),
    MSG_CURRENT_SIZE_P2(" tasks in the list."),
    MSG_CURRENT_TASKS("Here are the tasks in your list:\n"),
    MSG_EMPTY_LIST("There are currently no tasks!"),
    MSG_FIND_TASKS("Here are the matching tasks in your list:\n"),
    MSG_NO_TASKS("There are currently no matching tasks!"),

    // For mark / unmark / delete.
    MSG_TASK_DELETE("Noted. I've removed this task: \n"),
    MSG_TASK_DONE("Nice! I've marked this task as done:\n"),
    MSG_TASK_UNDONE("Ok, I've marked this task as not done yet:\n"),

    // Regex for mark and unmark tasks
    REGEX_MARK(" \\d+\\. \\[[TDE]\\]\\[X\\](?s).*"),
    REGEX_UNMARK(" \\d+\\. \\[[TDE]\\]\\[ \\](?s).*"),

    // For unexpected inputs
    MSG_INVALID_CMD_P1("OOPS!!! The format of "),
    MSG_INVALID_CMD_P2(" should be "),
    MSG_INVALID_UNKNOWN("OOPS!!! This command does not exist (yet).\n "
            + "Type 'help' for a list of commandtypes and their syntax!"),

    // For corrupt files
    CORRUPT_FILE("Please enter 'y' for yes and 'n' for no.");

    private final String message;

    /**
     * Default constructor.
     */
    MsgGenConst(String message) {
        this.message = message;
    }

    /**
     * Returns message in String type.
     */
    public String getString() {
        return message;
    }

}
