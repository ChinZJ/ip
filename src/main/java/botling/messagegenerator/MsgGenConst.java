package botling.messagegenerator;

public enum MsgGenConst {
    // For wrapping messages.
    MSG_LINE("\t____________________________________________________________\n"),

    // For greetings and farewells.
    MSG_FAREWELL("\t Bye. Hope to see you again soon!\n"),
    MSG_GREET("\t Hello! I'm Botling\n"
            + "\t What can I do for you?\n"),

    // For list / find.
    MSG_ADD("\t Got it. I've added this task: \n"),
    MSG_CURRENT_SIZE_P1("\t Now you have "),
    MSG_CURRENT_SIZE_P2(" tasks in the list.\n"),
    MSG_CURRENT_TASKS("\t Here are the tasks in your list:\n"),
    MSG_EMPTY_LIST("\t There are currently no tasks!\n"),
    MSG_FIND_TASKS("\t Here are the matching tasks in your list:\n"),
    MSG_NO_TASKS("\t There are currently no matching tasks!\n"),

    // For mark / unmark / delete.
    MSG_TASK_DELETE("\t Noted. I've removed this task: \n"),
    MSG_TASK_DONE("\t Nice! I've marked this task as done:\n"),
    MSG_TASK_UNDONE("\t Ok, I've marked this task as not done yet:\n"),

    // For unexpected inputs
    MSG_INVALID_CMD_P1("\t OOPS!!! The format of "),
    MSG_INVALID_CMD_P2(" should be "),
    MSG_INVALID_UNKNOWN("\t OOPS!!! This command does not exist(yet).\n");

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
