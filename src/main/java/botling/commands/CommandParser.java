package botling.commands;

import java.time.LocalDateTime;
import java.util.Optional;

import botling.DateParser;
import botling.TaskList;
import botling.TaskListWriter;
import botling.exceptions.InvalidInputException;
import botling.messagegenerator.MsgGen;
import botling.tasks.DeadlineDate;
import botling.tasks.Deadlines;
import botling.tasks.EventDate;
import botling.tasks.Events;
import botling.tasks.Task;
import botling.tasks.ToDo;

/**
 * Parses user input and uses <code>MsgGen</code> to generate messages appropriately.
 */
public class CommandParser {

    /**
     * Generates start up message and checks for any history.
     *
     * @param tasks TaskList to restore progress if any.
     *
     * @return message for Botling, inclusive of startup amd if any history is recovered.
     */
    public static String start(TaskList tasks) {
        String message = TaskListWriter.restore(tasks);
        if (tasks.isOpen()) {
            return (message + "\n" + MsgGen.greet());
        }
        return message;
    }

    /**
     * Main method for parsing user input.
     * Invalid inputs will throw a InvalidInputException.
     *
     * @param input User input. May be valid or invalid
     * @param tasks TaskList containing list of tasks.
     * @return message for Botling to pass messages to UI object to handle.
     */
    public static String parse(String input, TaskList tasks) {
        String message; // Dummy initialization
        if (tasks.isOpen()) {
            // TaskList is open means that there are no issues.
            try {
                if (input.startsWith(CmdConst.CMD_BYE.getString())) {
                    // "bye" command.
                    try {
                        message = CommandParser.bye(input, tasks);
                    } catch (InvalidInputException e) {
                        message = MsgGen.unknownCmd();
                    }
                } else if (input.startsWith(CmdConst.CMD_LIST.getString())) {
                    // "list" command.
                    try {
                        if (input.equals(CmdConst.CMD_LIST.getString())) {
                            message = MsgGen.list(tasks.list());
                        } else {
                            throw new InvalidInputException();
                        }
                    } catch (InvalidInputException e) {
                        message = MsgGen.unknownCmd();
                    }
                } else if (input.startsWith(CmdConst.CMD_FIND.getString())) {
                    // "find" command.
                    try {
                        message = MsgGen.find(find(input, tasks));
                    } catch (InvalidInputException e) {
                        message = MsgGen.unknownCmd();
                    }
                } else if (input.startsWith(CmdConst.CMD_MARK.getString())) {
                    // "mark" command.
                    try {
                        message = MsgGen.mark(CommandParser.mark(input, tasks));
                    } catch (NumberFormatException | InvalidInputException e) {
                        message = MsgGen.unknownSyntax(CmdConst.CMD_MARK.getString(),
                                CmdConst.MSG_INVALID_CMD_MARK.getString()
                                        + String.valueOf(tasks.size()));
                    }
                } else if (input.startsWith(CmdConst.CMD_UNMARK.getString())) {
                    // "unmark" command.
                    try {
                        message = MsgGen.unmark(CommandParser.unmark(input, tasks));
                    } catch (NumberFormatException | InvalidInputException e) {
                        message = MsgGen.unknownSyntax(CmdConst.CMD_UNMARK.getString(),
                                CmdConst.MSG_INVALID_CMD_MARK.getString()
                                        + String.valueOf(tasks.size()));
                    }
                } else if (input.startsWith(CmdConst.CMD_DELETE.getString())) {
                    // "delete" command.
                    try {
                        message = CommandParser.delete(input, tasks);
                        message = MsgGen.delete(message, tasks.size());
                    } catch (NumberFormatException | InvalidInputException e) {
                        message = MsgGen.unknownSyntax(CmdConst.CMD_DELETE.getString(),
                                CmdConst.MSG_INVALID_CMD_MARK.getString()
                                        + String.valueOf(tasks.size()));
                    }
                } else if (input.startsWith(CmdConst.CMD_TODO.getString())) {
                    // "todo" command.
                    try {
                        message = CommandParser.todo(input, tasks);
                        message = MsgGen.add(message, tasks.size());
                    } catch (InvalidInputException e) {
                        message = MsgGen.unknownSyntax(CmdConst.CMD_TODO.getString(),
                                CmdConst.MSG_INVALID_CMD_TODO.getString());
                    }
                } else if (input.startsWith(CmdConst.CMD_DEADLINE.getString())) {
                    // "deadline" command.
                    try {
                        message = CommandParser.deadline(input, tasks);
                        message = MsgGen.add(message, tasks.size());
                    } catch (InvalidInputException e) {
                        message = MsgGen.unknownSyntax(CmdConst.CMD_DEADLINE.getString(),
                                CmdConst.MSG_INVALID_CMD_DEADLINE.getString()
                                        + CmdConst.MSG_INVALID_CMD_DATE.getString());
                    }
                } else if (input.startsWith(CmdConst.CMD_EVENT.getString())) {
                    try {
                        message = CommandParser.event(input, tasks);
                        message = MsgGen.add(message, tasks.size());
                    } catch (InvalidInputException e) {
                        message = MsgGen.unknownSyntax(CmdConst.CMD_EVENT.getString(),
                                CmdConst.MSG_INVALID_CMD_EVENT.getString()
                                        + CmdConst.MSG_INVALID_CMD_EVENT_DATE.getString()
                                        + CmdConst.MSG_INVALID_CMD_DATE.getString());
                    }
                } else {
                    throw new InvalidInputException();
                }
            } catch (InvalidInputException e) {
                message = MsgGen.unknownCmd();
            }
        } else {
            // TaskList is not open means that there are issues.
            // Accept only 'y' or 'n'
            if (input.equals("y")) {
                tasks.clear();
                TaskListWriter.recreateFile();
                tasks.hasOpen();
                message = CmdConst.CORRUPT_DELETE.getString() + "\n" + MsgGen.greet();
            } else if (input.equals("n")) {
                message = CmdConst.CORRUPT_PAUSE.getString();
            } else {
                message = MsgGen.unknownCorrupt();
            }
        }
        return message;
    }

    /**
     * Method for parsing bye inputs.
     *
     * @throws InvalidInputException if syntax is not recognized.
     */
    private static String bye(String input, TaskList tasks) throws InvalidInputException {
        if (input.equals(CmdConst.CMD_BYE.getString())) {
            tasks.hasClose();
            return MsgGen.bye();
        } else {
            throw new InvalidInputException();
        }
    }

    /**
     * Method for parsing find inputs.
     *
     * @throws InvalidInputException if syntax is not recognized.
     */
    private static String find(String input, TaskList tasks) throws InvalidInputException {
        if (input.matches(CmdConst.TASK_FIND.getString())) {
            return tasks.find(input.substring(ValConstants.TASK_FIND.getVal()));
        } else {
            throw new InvalidInputException();
        }
    }

    /**
     * Method for parsing mark inputs.
     * Despite their similarities, mark() and unmark() will not have a common method base
     * due to the fact that the final method call to TaskList is different.
     * This results in unnecessary if else block statements.
     *
     * @throws InvalidInputException if syntax is not recognized.
     */
    private static String mark(String input, TaskList tasks)
            throws NumberFormatException, InvalidInputException {
        if (input.matches(CmdConst.TASK_MARK.getString())) {
            int index = Integer.parseInt(input.substring(ValConstants.TASK_MARK_IDX.getVal()))
                    - ValConstants.TASK_FIX_IDX.getVal();
            if ((index >= 0) && (index < tasks.size())) {
                String message = tasks.mark(index);
                TaskListWriter.write(tasks);
                return message;
            } else {
                throw new InvalidInputException();
            }
        } else {
            throw new InvalidInputException();
        }
    }

    /**
    * Method for parsing unmark inputs.
     * Despite their similarities, mark() and unmark() will not have a common method base
     * due to the fact that the final method call to TaskList is different.
     * This results in unnecessary if else block statements.
     *
     * @throws NumberFormatException if syntax is correct but input is not an integer.
     * @throws InvalidInputException if syntax is not recognized.
    */
    private static String unmark(String input, TaskList tasks)
            throws NumberFormatException, InvalidInputException {
        if (input.matches(CmdConst.TASK_UNMARK.getString())) {
            int index = Integer.parseInt(input.substring(ValConstants.TASK_UNMARK_IDX.getVal()))
                    - ValConstants.TASK_FIX_IDX.getVal();
            if ((index >= 0) && (index < tasks.size())) {
                String message = tasks.unmark(index);
                TaskListWriter.write(tasks);
                return message;
            } else {
                throw new InvalidInputException();
            }
        } else {
            throw new InvalidInputException();
        }
    }

    /**
     * Method for parsing delete inputs.
     *
     * @throws NumberFormatException if syntax is correct but input is not an integer.
     * @throws InvalidInputException if syntax is not recognized.
     */
    private static String delete(String input, TaskList tasks)
            throws NumberFormatException, InvalidInputException {
        if (input.matches(CmdConst.TASK_DELETE.getString())) {
            // Recall to convert to 1 based indexing
            // NumberFormatException may be thrown here during int parsing
            int index = Integer.parseInt(
                    input.substring(ValConstants.TASK_DELETE_IDX.getVal()))
                    - ValConstants.TASK_FIX_IDX.getVal();

            // Ensure integer is valid
            if ((index >= 0) && (index < tasks.size())) {
                String message = tasks.remove(index);
                TaskListWriter.write(tasks);
                return message;
            } else {
                throw new InvalidInputException();
            }
        } else {
            throw new InvalidInputException();
        }
    }

    /**
     * Method for parsing todo inputs.
     *
     * @throws InvalidInputException if syntax is not recognized.
     */
    private static String todo(String input, TaskList tasks) throws InvalidInputException {
        if (input.matches(CmdConst.TASK_TODO.getString())) {
            Task newTask = new ToDo(input.substring(ValConstants.TASK_TODO_IDX.getVal()));
            String message = tasks.add(newTask);
            TaskListWriter.write(tasks);
            return message;
        } else {
            throw new InvalidInputException();
        }
    }

    /**
     * Method for parsing deadline inputs.
     *
     * @throws InvalidInputException if syntax is not recognized.
     */
    private static String deadline(String input, TaskList tasks) throws InvalidInputException {
        if (input.matches(CmdConst.TASK_DEADLINE.getString())) {
            // Deadline has a /by specification, greedily take the first in sequence.
            String withoutDeadline = input.substring(ValConstants.TASK_DEADLINE_IDX.getVal());
            int byIdx = withoutDeadline.indexOf(CmdConst.CMD_BY.getString());
            String deadlineName = withoutDeadline.substring(0, byIdx);
            String by = withoutDeadline.substring(byIdx
                    + ValConstants.TASK_BY_IDX.getVal());
            Task newTask;

            // Check deadline is a valid LocalDateTime object.
            Optional<LocalDateTime> byDateTime = DateParser.parseDateTime(by);
            if (byDateTime.isPresent()) {
                newTask = new DeadlineDate(deadlineName, byDateTime.get());
            } else {
                newTask = new Deadlines(deadlineName, by);
            }

            // Add task.
            String message = tasks.add(newTask);
            TaskListWriter.write(tasks);
            return message;
        } else {
            throw new InvalidInputException();
        }
    }

    /**
     * Method for parsing event inputs.
     *
     * @throws InvalidInputException if syntax is not recognized.
     */
    private static String event(String input, TaskList tasks) throws InvalidInputException {
        if (input.matches(CmdConst.TASK_EVENT.getString())) {
            // Event has /from and /to specification.
            // Greedily take the first in sequential order.
            String withoutEvent = input.substring(ValConstants.TASK_EVENT_IDX.getVal());
            int fromIdx = withoutEvent.indexOf(CmdConst.CMD_FROM.getString());
            String eventName = withoutEvent.substring(0, fromIdx);
            String remainingSplit = withoutEvent.substring(fromIdx
                    + ValConstants.TASK_FROM_IDX.getVal());
            int toIdx = remainingSplit.indexOf(CmdConst.CMD_TO.getString());
            String eventFrom = remainingSplit.substring(0, toIdx);
            String eventTo = remainingSplit.substring(toIdx
                    + ValConstants.TASK_TO_IDX.getVal());
            Task newTask;

            // Check if eventFrom and eventTo are of valid LocalDateTime objects.
            Optional<LocalDateTime> startDateTimeOpt = DateParser.parseDateTime(eventFrom);
            Optional<LocalDateTime> endDateTimeOpt = DateParser.parseDateTime(eventTo);
            if (startDateTimeOpt.isPresent() && endDateTimeOpt.isPresent()) {
                LocalDateTime startDateTime = startDateTimeOpt.get();
                LocalDateTime endDateTime = endDateTimeOpt.get();
                if (startDateTime.isBefore(endDateTime) || startDateTime.isEqual(endDateTime)) {
                    newTask = new EventDate(eventName, startDateTime, endDateTime);
                } else {
                    throw new InvalidInputException();
                }
            } else {
                newTask = new Events(eventName, eventFrom, eventTo);
            }
            // Add task.
            String message = tasks.add(newTask);
            TaskListWriter.write(tasks);
            return message;
        } else {
            throw new InvalidInputException();
        }
    }

}