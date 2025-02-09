package botling.commands.commandtypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

import botling.TaskList;
import botling.TaskListWriter;
import botling.commands.CmdConst;
import botling.commands.CommandColor;
import botling.commands.ValConstants;
import botling.exceptions.InvalidInputException;
import botling.messagegenerator.MsgGen;
import botling.tasks.Task;
import botling.tasks.ToDo;

/**
 * Parses todo commands.
 */
public class TodoCmd implements TasksCmd {

    /**
     * Creates a ToDo task.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor) {
        if (input.matches(CmdConst.TASK_TODO.getString())) {
            Task newTask = new ToDo(input.substring(ValConstants.TASK_TODO_IDX.getVal()));
            String message = tasks.add(newTask);
            TaskListWriter.write(tasks);
            return MsgGen.add(message, tasks.size(), cmdColor);
        } else {
            return MsgGen.unknownSyntax(CmdConst.CMD_TODO.getString(),
                    CmdConst.MSG_INVALID_CMD_TODO.getString(), cmdColor);
        }
    }

    /**
     * Parses command from history file to generate Todo task.
     *
     * @param reader Reader that reads the history file.
     * @param tasks TaskList object containing tasks.
     */
    public void restore(BufferedReader reader, TaskList tasks)
            throws InvalidInputException, IOException {
        String name = validateString(reader.readLine());
        boolean mark = validateAndParseBool(reader.readLine());
        LocalDateTime createDate = validateAndParseDate(reader.readLine());

        Task task = new ToDo(name, mark, createDate);
        tasks.add(task);
    }
}
