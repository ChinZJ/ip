package botling.messagegenerator;

/**
 * Responsible for generating all messages where appropriate.
 */
public class MsgGen {

    /**
     * Generates greeting message.
     */
    public static String greet() {
        return MsgGenConst.MSG_GREET.getString();
    }

    /**
     * Generates farewell message.
     */
    public static String bye() {
        return MsgGenConst.MSG_FAREWELL.getString();
    }

    /**
     * Provides a wrapper for TaskList list() message.
     */
    public static String list(String message) {
        if (message.isEmpty()) {
            return MsgGenConst.MSG_EMPTY_LIST.getString();
        } else {
            return MsgGenConst.MSG_CURRENT_TASKS.getString() + message;
        }
    }

    /**
     * Provides a wrapper for TaskList find() message.
     */
    public static String find(String message) {
        if (message.isEmpty()) {
            return MsgGenConst.MSG_NO_TASKS.getString();
        } else {
            return MsgGenConst.MSG_FIND_TASKS.getString() + message;
        }
    }

    /**
     * Provides a wrapper for TaskList mark() message.
     */
    public static String mark(String message) {
        return MsgGenConst.MSG_TASK_DONE.getString() + message;
    }

    /**
     * Provides a wrapper for TaskList unmark() message.
     */
    public static String unmark(String message) {
        return MsgGenConst.MSG_TASK_UNDONE.getString() + message;
    }

    /**
     * Provides aa wrapper for TaskList add() message.
     *
     * @param message Message generated from TaskList.
     * @param size Size of TaskList.
     */
    public static String add(String message, int size) {
        return MsgGenConst.MSG_ADD.getString()
                + message
                + MsgGenConst.MSG_CURRENT_SIZE_P1.getString() + size
                + MsgGenConst.MSG_CURRENT_SIZE_P2.getString();
    }

    /**
     * Provides aa wrapper for TaskList remove() message.
     *
     * @param message Message generated from TaskList.
     * @param size Size of TaskList.
     */
    public static String delete(String message, int size) {
        return MsgGenConst.MSG_TASK_DELETE.getString()
                + message
                + MsgGenConst.MSG_CURRENT_SIZE_P1.getString() + size
                + MsgGenConst.MSG_CURRENT_SIZE_P2.getString();
    }

    /**
     * Message when command is unknown.
     */
    public static String unknownCmd() {
        return MsgGenConst.MSG_INVALID_UNKNOWN.getString();
    }

    /**
     * Message when command syntax is not fulfilled.
     */
    public static String unknownSyntax(String cmd, String syntax) {
        return MsgGenConst.MSG_INVALID_CMD_P1.getString()
                + cmd
                + MsgGenConst.MSG_INVALID_CMD_P2.getString()
                + cmd + syntax + "\n";
    }

    /**
     * Message when looking for 'y' or 'n' inputs.
     * Used when history file is corrupted and checking to delete or exit the program.
     */
    public static String unknownCorrupt() {
        return MsgGenConst.CORRUPT_FILE.getString();
    }

}
