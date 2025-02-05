package botling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import botling.commands.commandtypes.DeadlineCmd;
import botling.commands.commandtypes.EventCmd;
import botling.commands.commandtypes.TodoCmd;
import botling.exceptions.InvalidInputException;
import botling.tasks.Task;

/**
 * Performs I/O actions for <code>TaskList</code> objects to hard disk where appropriate.
 */
public class TaskListWriter {
    private static final String HISTORY_DATA_FOLDER = "./data";
    private static final String HISTORY_DATA_PATH = "./data/history.txt";

    /**
     * Checks if the TaskList has been instantiated before.
     * If so, load the previous history of the TaskList.
     * Else generate a new history of the TaskList.
     *
     * @return Any relevant logs of trying to generate / retrieve the history of the task list.
     */
    public static String restore(TaskList tasks) {
        String message = "Attempting to retrieve history...\n";
        File dataDir = new File(TaskListWriter.HISTORY_DATA_FOLDER);
        if (!dataDir.exists()) {
            message += "No data folder found! Creating data folder...\n";
            dataDir.mkdir();
        } else {
            message += "Data folder found!\n";
        }

        File historyFile = new File(TaskListWriter.HISTORY_DATA_PATH);
        if (!historyFile.exists()) {
            message += "No history file found! Creating history file...\n";
            try {
                historyFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating history file: " + e.getMessage());
            }
        } else {
            message += "History file found! Restoring data...\n";
            tasks = TaskListWriter.read(tasks);
            // If tasks is closed, then the history file is corrupt.
            // Push the error message back
            if (!tasks.isOpen()) {
                message += "An error occurred while trying to read"
                        + " the history.txt file.\n"
                        + "Do you wish to delete it and start again? (y/n)";
            }
        }
        return message;
    }

    /**
     * Reads the history file and generates a <code>TaskList</code> object off of it.
     */
    private static TaskList read(TaskList tasks) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(TaskListWriter.HISTORY_DATA_PATH))) {
            String cmd;
            boolean isValid = true;

            Task task;
            while (isValid) {
                cmd = reader.readLine();
                if (cmd != null) {
                    if (!cmd.isEmpty()) {
                        try {
                            // CHECKSTYLE.OFF: Indentation
                            switch (cmd) {
                                case "todo" -> new TodoCmd().restore(reader, tasks);
                                case "deadline" -> new DeadlineCmd().restore(reader, tasks);
                                case "event" -> new EventCmd().restore(reader, tasks);
                                default -> throw new InvalidInputException();
                            }
                            // CHECKSTYLE.ON: Indentation
                        } catch (InvalidInputException e) {
                            tasks.hasClose();
                        }
                    }
                } else {
                    isValid = false;
                }
                // Ignore the case of null for an empty file.
            }
        } catch (IOException e) {
            System.out.println("Error reading history file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Writes to file to save tasks.
     */
    public static void write(TaskList tasks) {
        try (PrintWriter writer = new PrintWriter(
                new FileWriter(TaskListWriter.HISTORY_DATA_PATH))) {
            writer.print(tasks.fileString());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Deletes and creates a new history.txt file.
     */
    public static void recreateFile() {
        try {
            File file = new File(TaskListWriter.HISTORY_DATA_PATH);
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
