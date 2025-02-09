package botling.messagegenerator;

import java.util.Arrays;
import java.util.stream.Stream;

import botling.commands.CommandColor;
import botling.gui.ColorNames;

/**
 * Generates all of Botling's messages where appropriate.
 */
public class MsgGen {

    /**
     * Wraps single messages with black colored text.
     */
    public static String wrap(String message, CommandColor cmdColor) {
        String[] messages = new String[]{message};
        Integer[] lines = new Integer[]{ColorNames.COLOR_BLACK.getIndex()};
        cmdColor.setAll(messages, lines);
        return message;
    }

    /**
     * Generates greeting message.
     */
    public static String greet(String message, CommandColor cmdColor) {
        return wrap((message + "\n" + MsgGenConst.MSG_GREET.getString()),
                cmdColor);
    }

    /**
     * Generates farewell message.
     */
    public static String bye(CommandColor cmdColor) {
        return wrap(MsgGenConst.MSG_FAREWELL.getString(), cmdColor);
    }

    /**
     * Provides a wrapper for TaskList list() message.
     */
    public static String list(String[] message, CommandColor cmdColor) {
        if (message.length == 0) {
            return wrap(MsgGenConst.MSG_EMPTY_LIST.getString(), cmdColor);
        } else {
            /*
             Adapted from
             https://stackoverflow.com/questions/23079003/how-to-convert-a-java-8-stream-to-an-array
             */
            String[] start = new String[]{MsgGenConst.MSG_CURRENT_TASKS.getString()};
            String[] merge = Stream.concat(Arrays.stream(start), Arrays.stream(message))
                    .toArray(String[]::new);
            Integer[] lines = new Integer[merge.length];
            // Not necessary, but for clarity.
            Arrays.fill(lines, ColorNames.COLOR_BLACK.getIndex());
            // Skip first element.
            for (int i = 1; i < merge.length; i++) {
                if (merge[i].matches(MsgGenConst.REGEX_MARK.getString())) {
                    lines[i] = ColorNames.COLOR_GREEN.getIndex();
                } else {
                    lines[i] = ColorNames.COLOR_RED.getIndex();
                }
            }
            cmdColor.setAll(merge, lines);
            return MsgGenConst.MSG_CURRENT_TASKS.getString() + String.join("", message);
        }
    }

    /**
     * Provides a wrapper for TaskList find() message.
     */
    public static String find(String[] message, CommandColor cmdColor) {
        if (Arrays.stream(message)
                .filter(s -> !s.isEmpty())
                .count() == 0) {
            return wrap(MsgGenConst.MSG_NO_TASKS.getString(), cmdColor);
        } else {
            String[] start = new String[]{MsgGenConst.MSG_FIND_TASKS.getString()};
            String[] merge = Stream.concat(Arrays.stream(start), Arrays.stream(message))
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);
            Integer[] lines = new Integer[merge.length];
            Arrays.fill(lines, ColorNames.COLOR_BLACK.getIndex());
            for (int i = 1; i < merge.length; i++) {
                if (merge[i].matches(MsgGenConst.REGEX_MARK.getString())) {
                    lines[i] = ColorNames.COLOR_GREEN.getIndex();
                } else {
                    lines[i] = ColorNames.COLOR_RED.getIndex();
                }
            }
            cmdColor.setAll(merge, lines);
            return MsgGenConst.MSG_FIND_TASKS.getString() + String.join("", message);
        }
    }

    /**
     * Provides a wrapper for TaskList mark() message.
     */
    public static String mark(String message, CommandColor cmdColor) {
        String[] messages = new String[]{MsgGenConst.MSG_TASK_DONE.getString(), message};
        Integer[] lines = new Integer[]{ColorNames.COLOR_BLACK.getIndex(),
                ColorNames.COLOR_GREEN.getIndex()};
        cmdColor.setAll(messages, lines);
        return MsgGenConst.MSG_TASK_DONE.getString() + message;
    }

    /**
     * Provides a wrapper for TaskList unmark() message.
     */
    public static String unmark(String message, CommandColor cmdColor) {
        String[] messages = new String[]{MsgGenConst.MSG_TASK_UNDONE.getString(), message};
        Integer[] lines = new Integer[]{ColorNames.COLOR_BLACK.getIndex(),
                ColorNames.COLOR_RED.getIndex()};
        cmdColor.setAll(messages, lines);
        return MsgGenConst.MSG_TASK_UNDONE.getString() + message;
    }

    /**
     * Provides aa wrapper for TaskList add() message.
     *
     * @param message Message generated from TaskList.
     * @param size Size of TaskList.
     */
    public static String add(String message, int size, CommandColor cmdColor) {
        String[] messages = new String[]{MsgGenConst.MSG_ADD.getString(), message,
                MsgGenConst.MSG_CURRENT_SIZE_P1.getString() + size,
                MsgGenConst.MSG_CURRENT_SIZE_P2.getString()};
        Integer[] lines = new Integer[]{ColorNames.COLOR_BLACK.getIndex(),
                ColorNames.COLOR_RED.getIndex(),
                ColorNames.COLOR_BLACK.getIndex(),
                ColorNames.COLOR_BLACK.getIndex()};
        cmdColor.setAll(messages, lines);
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
    public static String delete(String message, int size, CommandColor cmdColor) {
        String[] messages = new String[]{MsgGenConst.MSG_TASK_DELETE.getString(), message,
                MsgGenConst.MSG_CURRENT_SIZE_P1.getString() + size,
                MsgGenConst.MSG_CURRENT_SIZE_P2.getString()};
        Integer[] lines = new Integer[]{ColorNames.COLOR_BLACK.getIndex(),
                ColorNames.COLOR_STRIKETHROUGH.getIndex(),
                ColorNames.COLOR_BLACK.getIndex(),
                ColorNames.COLOR_BLACK.getIndex()};
        cmdColor.setAll(messages, lines);
        return MsgGenConst.MSG_TASK_DELETE.getString()
                + message + MsgGenConst.MSG_CURRENT_SIZE_P1.getString() + size
                + MsgGenConst.MSG_CURRENT_SIZE_P2.getString();
    }

    /**
     * Message when command is unknown.
     */
    public static String unknownCmd(CommandColor cmdColor) {
        return wrap(MsgGenConst.MSG_INVALID_UNKNOWN.getString(), cmdColor);
    }

    /**
     * Message when command syntax is not fulfilled.
     */
    public static String unknownSyntax(String cmd, String syntax, CommandColor cmdColor) {
        String message = MsgGenConst.MSG_INVALID_CMD_P1.getString()
                + cmd
                + MsgGenConst.MSG_INVALID_CMD_P2.getString()
                + cmd + syntax;
        return wrap(message, cmdColor);
    }

    /**
     * Message when looking for 'y' or 'n' inputs.
     * Used when history file is corrupted and checking to delete or exit the program.
     */
    public static String unknownCorrupt(CommandColor cmdColor) {
        return wrap(MsgGenConst.CORRUPT_FILE.getString(), cmdColor);
    }

    public static String unknownDateTime(CommandColor cmdColor) {
        return wrap(MsgGenConst.MSG_INVALID_DATETIME.getString(), cmdColor);
    }

}
