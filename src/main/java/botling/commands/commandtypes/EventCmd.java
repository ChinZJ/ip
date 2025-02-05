package botling.commands.commandtypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import botling.DateParser;
import botling.TaskList;
import botling.TaskListWriter;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.commands.ValConstants;
import botling.exceptions.InvalidInputException;
import botling.messagegenerator.MsgGen;
import botling.tasks.Events;
import botling.tasks.Task;

/**
 * Parses event commands.
 */
public class EventCmd implements TasksCmd {
    private static final int IDX_NAME = 0;
    private static final int IDX_FROM = 1;
    private static final int IDX_TO = 2;

    /**
     * Creates a event task.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        if (input.matches(CmdConst.TASK_EVENT.getString())) {
            String[] splitCommands = splitCommand(input);
            String eventName = splitCommands[IDX_NAME];
            String eventFrom = splitCommands[IDX_FROM];
            String eventTo = splitCommands[IDX_TO];

            // Check if eventFrom and eventTo are of valid LocalDateTime objects.
            Optional<LocalDateTime> startDateTimeOpt = DateParser.parseDateTime(eventFrom);
            Optional<LocalDateTime> endDateTimeOpt = DateParser.parseDateTime(eventTo);
            // Use the negation instead so that it reads clearer.
            // If the start date and end date are valid,
            // such that the start date is after the end date,
            // it should be deemed as an invalid expression.
            if (!(startDateTimeOpt.isPresent()
                    && endDateTimeOpt.isPresent()
                    && startDateTimeOpt.get().isAfter(endDateTimeOpt.get()))) {
                Task newTask = new Events(eventName, eventFrom, eventTo,
                        startDateTimeOpt, endDateTimeOpt);

                // Add task.
                String message = tasks.add(newTask);
                TaskListWriter.write(tasks);
                return MsgGen.add(message, tasks.size(), cmdColor);
            }
        }
        return MsgGen.unknownSyntax(CmdConst.CMD_EVENT.getString(),
                CmdConst.MSG_INVALID_CMD_EVENT.getString()
                        + CmdConst.MSG_INVALID_CMD_EVENT_DATE.getString()
                        + CmdConst.MSG_INVALID_CMD_DATE.getString(), cmdColor);
    }

    /**
     * Helper method to split the command line to name, from and to.
     * Deadline has a /from and /to specification, greedily take the first in sequence.
     */
    private static String[] splitCommand(String input) {
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
        return new String[]{eventName, eventFrom, eventTo};
    }

    /**
     * Parses command from history file to generate Todo task.
     *
     * @param reader Reader that reads the history file.
     * @param tasks TaskList object containing tasks.
     */
    public void restore(BufferedReader reader, TaskList tasks)
            throws InvalidInputException, IOException {
        String from = validateString(reader.readLine());
        String to = validateString(reader.readLine());
        String name = validateString(reader.readLine());
        Boolean mark = validateAndParseBool(reader.readLine());
        LocalDateTime createDate = validateAndParseDate(reader.readLine());

        Optional<LocalDateTime> startDateTimeOpt = DateParser
                .parseDateTime(from);
        Optional<LocalDateTime> endDateTimeOpt = DateParser
                .parseDateTime(to);
        startBeforeEnd(startDateTimeOpt, endDateTimeOpt);

        Task task = new Events(name, mark, from, to,
                DateParser.parseDateTime(from), DateParser.parseDateTime(to), createDate);
        tasks.add(task);
    }

    private void startBeforeEnd(Optional<LocalDateTime> startDateTimeOpt,
                Optional<LocalDateTime> endDateTimeOpt) {
        // Check if eventFrom and eventTo are valid LocalDateTime objects.
        if (startDateTimeOpt.isPresent()
                && endDateTimeOpt.isPresent()
                && startDateTimeOpt.get().isAfter(endDateTimeOpt.get())) {
            // This was validated during instantiation of the task object.
            // If occurs, either someone has tinkered with the history file
            // or the program is at fault.
            throw new AssertionError("History file corrupt or "
                    + "program fault!");
        }
    }
}
