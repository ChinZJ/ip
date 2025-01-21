/**
 * Responsible for generating all messages where appropriate.
 */
public class MsgGen {
    private static final String NO_MSG = "EMPTY";

    /**
     * Reads the message, wraps it in line.
     */
    public static String read(String message) {
        return MsgGenConst.MSG_LINE.getString()
                + message
                + MsgGenConst.MSG_LINE.getString();
    }

    /**
     * Generates greeting message.
     */
    public static String greet() {
        return MsgGen.read(MsgGenConst.MSG_GREET.getString());
    }

    /**
     * Generates farewell message.
     */
    public static String bye() {
        return MsgGen.read(MsgGenConst.MSG_FAREWELL.getString());
    }

    /**
     * Provides a wrapper for TaskList list() message.
     */
    public static String list(String message) {
        if (message.isEmpty()) {
            return MsgGen.read(MsgGenConst.MSG_EMPTY_LIST.getString());
        } else {
            return MsgGen.read(MsgGenConst.MSG_CURRENT_TASKS.getString() + message);
        }
    }

    /**
     * Provides a wrapper for TaskList mark() message.
     */
    public static String mark(String message) {
        return MsgGen.read(MsgGenConst.MSG_TASK_DONE.getString() + message);
    }

    /**
     * Provides a wrapper for TaskList unmark() message.
     */
    public static String unmark(String message) {
        return MsgGen.read(MsgGenConst.MSG_TASK_UNDONE.getString() + message);
    }

    /**
     * Provides aa wrapper for Tasklist add() message.
     *
     * @param message Message generated from TaskList.
     * @param size Size of TaskList.
     */
    public static String add(String message, int size) {
        return MsgGen.read(MsgGenConst.MSG_ADD.getString()
                + message
                + MsgGenConst.MSG_CURRENT_SIZE_P1.getString() + size
                + MsgGenConst.MSG_CURRENT_SIZE_P2.getString());
    }

    /**
     * Provides aa wrapper for Tasklist remove() message.
     *
     * @param message Message generated from TaskList.
     * @param size Size of TaskList.
     */
    public static String delete(String message, int size) {
        return MsgGen.read(MsgGenConst.MSG_TASK_DELETE.getString()
                + message
                + MsgGenConst.MSG_CURRENT_SIZE_P1.getString() + size
                + MsgGenConst.MSG_CURRENT_SIZE_P2.getString());
    }

    /**
     * Message when command is unknown.
     */
    public static String unknownCmd() {
        return MsgGen.read(MsgGenConst.MSG_INVALID_UNKNOWN.getString());
    }

    /**
     * Message when list syntax is not fulfilled.
     *
     */
    public static String unknownList() {
        return MsgGen.read(MsgGenConst.MSG_INVALID_UNKNOWN.getString());
    }

    /**
     * Message when command syntax is not fulfilled.
     */
    public static String unknownSyntax(String cmd, String syntax) {
        return MsgGen.read(MsgGenConst.MSG_INVALID_CMD_P1.getString()
                + cmd
                + MsgGenConst.MSG_INVALID_CMD_P2.getString()
                + cmd + syntax + "\n");
    }

}
