package botling.messagegenerator;

public enum MsgGenConst {

    // For greetings and farewells.
    MSG_FAREWELL("Bye. Hope to see you again soon!\n"),
    MSG_GREET("Hello! I'm Botling\n"
            + "What can I do for you?\n"),

    // For list / find.
    MSG_ADD("Got it. I've added this task: \n"),
    MSG_CURRENT_SIZE_P1("Now you have "),
    MSG_CURRENT_SIZE_P2(" tasks in the list.\n"),
    MSG_CURRENT_TASKS("Here are the tasks in your list:\n"),
    MSG_EMPTY_LIST("There are currently no tasks!\n"),
    MSG_FIND_TASKS("Here are the matching tasks in your list:\n"),
    MSG_NO_TASKS("There are currently no matching tasks!\n"),

    // For mark / unmark / delete.
    MSG_TASK_DELETE("Noted. I've removed this task: \n"),
    MSG_TASK_DONE("Nice! I've marked this task as done:\n"),
    MSG_TASK_UNDONE("Ok, I've marked this task as not done yet:\n"),

    // For unexpected inputs
    MSG_INVALID_CMD_P1("OOPS!!! The format of "),
    MSG_INVALID_CMD_P2(" should be "),
    MSG_INVALID_UNKNOWN("OOPS!!! This command does not exist(yet).\n");

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
