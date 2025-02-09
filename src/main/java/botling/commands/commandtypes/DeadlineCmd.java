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
import botling.tasks.Deadlines;
import botling.tasks.Task;

/**
 * Parses deadline commands.
 */
public class DeadlineCmd implements TasksCmd {
    private static final int IDX_NAME = 0;
    private static final int IDX_BY = 1;

    /**
     * Creates a deadline task.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        if (input.matches(CmdConst.TASK_DEADLINE.getString())) {
            String[] splitInput = splitCommand(input);
            String deadlineName = splitInput[IDX_NAME];
            String by = splitInput[IDX_BY];

            DateParser dateParser = new DateParser();
            Optional<LocalDateTime> byDateTime = dateParser.parseDateTime(by);
            // If empty, check if syntax correct but invalid date time.
            if (byDateTime.isEmpty() && dateParser.isInvalid()) {
                return MsgGen.unknownDateTime(cmdColor);
            }
            Task newTask = new Deadlines(deadlineName, by, byDateTime);

            // Add task.
            String message = tasks.add(newTask);
            TaskListWriter.write(tasks);
            return MsgGen.add(message, tasks.size(), cmdColor);
        }
        return MsgGen.unknownSyntax(CmdConst.CMD_DEADLINE.getString(),
                CmdConst.MSG_INVALID_CMD_DEADLINE.getString()
                        + CmdConst.MSG_INVALID_CMD_DATE.getString(), cmdColor);
    }

    /**
     * Helper method to split the command line to name and deadline.
     * Deadline has a /by specification, greedily take the first in sequence.
     */
    private static String[] splitCommand(String input) {
        String withoutDeadline = input.substring(ValConstants.TASK_DEADLINE_IDX.getVal());
        int byIdx = withoutDeadline.indexOf(CmdConst.CMD_BY.getString());
        String deadlineName = withoutDeadline.substring(0, byIdx);
        String by = withoutDeadline.substring(byIdx
                + ValConstants.TASK_BY_IDX.getVal());
        return new String[]{deadlineName, by};

    }

    /**
     * Parses command from history file to generate Deadline task.
     *
     * @param reader Reader that reads the history file.
     * @param tasks TaskList object containing tasks.
     */
    public void restore(BufferedReader reader, TaskList tasks)
            throws InvalidInputException, IOException {
        String by = validateString(reader.readLine());
        String name = validateString(reader.readLine());
        boolean mark = validateAndParseBool(reader.readLine());
        LocalDateTime createDate = validateAndParseDate(reader.readLine());

        Task task = new Deadlines(name, mark, by,
                new DateParser().parseDateTime(by), createDate);
        tasks.add(task);
    }
}
