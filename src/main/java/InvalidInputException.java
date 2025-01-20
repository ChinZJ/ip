import java.lang.Exception;

public class InvalidInputException extends Exception {
    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public void unknownCmd() {
        System.out.println(MsgConstants.MSG_LINE.getString()
                + MsgConstants.MSG_INVALID_UNKNOWN.getString()
                + MsgConstants.MSG_LINE.getString());
    }

    public void unknownList() {
        System.out.println(MsgConstants.MSG_LINE.getString()
                + MsgConstants.MSG_INVALID_UNKNOWN.getString()
                + MsgConstants.MSG_LINE.getString());
    }

    public void unknownMark(String name, int size) {
        System.out.println(MsgConstants.MSG_LINE.getString()
                + MsgConstants.MSG_INVALID_CMD_P1.getString()
                + name
                + MsgConstants.MSG_INVALID_CMD_P2.getString()
                + name + MsgConstants.MSG_INVALID_CMD_MARK.getString() + size + "\n"
                + MsgConstants.MSG_LINE.getString());
    }

    public void unknownTodo() {
        System.out.println(MsgConstants.MSG_LINE.getString()
                + MsgConstants.MSG_INVALID_CMD_P1.getString()
                + MsgConstants.CMD_TODO.getString()
                + MsgConstants.MSG_INVALID_CMD_P2.getString()
                + MsgConstants.CMD_TODO.getString() + MsgConstants.MSG_INVALID_CMD_TODO.getString()
                + MsgConstants.MSG_LINE.getString());
    }

    public void unknownDeadline() {
        System.out.println(MsgConstants.MSG_LINE.getString()
                + MsgConstants.MSG_INVALID_CMD_P1.getString()
                + MsgConstants.CMD_DEADLINE.getString()
                + MsgConstants.MSG_INVALID_CMD_P2.getString()
                + MsgConstants.CMD_DEADLINE.getString() + MsgConstants.MSG_INVALID_CMD_DEADLINE.getString()
                + MsgConstants.MSG_LINE.getString());
    }

    public void unknownEvent() {
        System.out.println(MsgConstants.MSG_LINE.getString()
                + MsgConstants.MSG_INVALID_CMD_P1.getString()
                + MsgConstants.CMD_EVENT.getString()
                + MsgConstants.MSG_INVALID_CMD_P2.getString()
                + MsgConstants.CMD_EVENT.getString() + MsgConstants.MSG_INVALID_CMD_EVENT.getString()
                + MsgConstants.MSG_LINE.getString());
    }
}
