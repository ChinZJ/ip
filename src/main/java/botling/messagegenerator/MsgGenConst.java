package botling.messagegenerator;

/**
 * Message constants used to generate Botling's replies.
 */
public enum MsgGenConst {
    // For greetings and farewells.
    MSG_FAREWELL("Shell-be seeing you!"),
    MSG_GREET("Hey! I'm Botling!\n"
            + "What can I do for you?"),

    // For list / find.
    MSG_ADD("You threw this into the ocean:\n"),
    MSG_CURRENT_SIZE_P1("\nNow you have "),
    MSG_CURRENT_SIZE_P2(" tasks polluting my waters!"),
    MSG_CURRENT_TASKS("Here's whats sinking:\n"),
    MSG_EMPTY_LIST("Oceans clean, I'm free!"),
    MSG_USER_EMPTY_LIST("You have no tasks!"),
    MSG_FIND_TASKS("I chewed on some, but here's the remnants:\n"),
    MSG_NO_TASKS("Don't see any, check the landfill!"),

    // For mark / unmark / delete.
    MSG_TASK_DELETE("This task has degraded into nothingness:\n"),
    MSG_TASK_DONE("Nice! I've swallowed this task:\n"),
    MSG_TASK_UNDONE("Yuck, I've spat out this task:\n"),

    // Regex for mark and unmark tasks
    REGEX_MARK(" \\d+\\. (\\[DATE\\] )?\\[[TDE]\\]\\[X\\](?s).*"),
    REGEX_UNMARK(" \\d+\\. \\[[TDE]\\]\\[ \\](?s).*"),

    // For unexpected inputs
    MSG_INVALID_CMD_P1("Yikes!!! The format of "),
    MSG_INVALID_CMD_P2(" should be "),
    MSG_INVALID_UNKNOWN("Yikes!!! This command is still up for discussion.\n"
            + "Type 'help' for a list of commands and their syntax!"),
    MSG_INVALID_DATETIME("That date time looks awkwardly wrong...\n"
            + "I think this is suitable for peer help!"),

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
