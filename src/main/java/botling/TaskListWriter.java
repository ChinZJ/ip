package botling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Optional;

import botling.exceptions.InvalidInputException;
import botling.tasks.DeadlineDate;
import botling.tasks.Deadlines;
import botling.tasks.EventDate;
import botling.tasks.Events;
import botling.tasks.Task;
import botling.tasks.ToDo;

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
            String name;
            String arg1;
            String arg2;
            boolean mark;
            boolean isValid = true;

            Task task;
            while (isValid) {
                cmd = reader.readLine();
                if (cmd != null) {
                    if (!cmd.isEmpty()) {
                        try {
                            switch (cmd) {
                            case "todo":
                                name = reader.readLine();
                                mark = validateAndParseBool(reader.readLine());
                                task = new ToDo(name, mark);
                                tasks.add(task);
                                break;
                            case "deadline":
                                arg1 = reader.readLine();
                                name = reader.readLine();
                                mark = validateAndParseBool(reader.readLine());

                                // Check deadline is a valid LocalDateTime object.
                                Optional<LocalDateTime> byDateTime = DateParser.parseDateTime(arg1);
                                if (byDateTime.isPresent()) {
                                    task = new DeadlineDate(name, mark, byDateTime.get());
                                } else {
                                    task = new Deadlines(name, mark, arg1);
                                }

                                // Add task
                                tasks.add(task);
                                break;
                            case "event":
                                arg1 = reader.readLine();
                                arg2 = reader.readLine();
                                name = reader.readLine();
                                mark = validateAndParseBool(reader.readLine());

                                // Check if eventFrom and eventTo are valid LocalDateTime objects.
                                Optional<LocalDateTime> startDateTimeOpt = DateParser
                                        .parseDateTime(arg1);
                                Optional<LocalDateTime> endDateTimeOpt = DateParser
                                        .parseDateTime(arg2);
                                if (startDateTimeOpt.isPresent() && endDateTimeOpt.isPresent()) {
                                    LocalDateTime startDateTime = startDateTimeOpt.get();
                                    LocalDateTime endDateTime = endDateTimeOpt.get();
                                    if (startDateTime.isBefore(endDateTime)
                                            || startDateTime.isEqual(endDateTime)) {
                                        task = new EventDate(
                                                name, mark, startDateTime, endDateTime);
                                    } else {
                                        throw new InvalidInputException();
                                    }
                                } else {
                                    task = new Events(name, mark, arg1, arg2);
                                }
                                tasks.add(task);
                                break;
                            default:
                                throw new InvalidInputException();
                            }
                        } catch (InvalidInputException e) {
                            tasks.hasClose();
                        }
                    }
                } else {
                    isValid = false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading history file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Checks if the input aligns with a boolean.
     * Due to the fact that <code>Boolean.parseBoolean()</code> does not distinguish invalid inputs.
     *
     * @throws InvalidInputException An arbitrary exception thrown when there is no boolean String.
     */
    private static boolean validateAndParseBool(String input) throws InvalidInputException {
        if (input == null) {
            throw new InvalidInputException();
        } else if (!input.equalsIgnoreCase("true")
                && !input.equalsIgnoreCase("false")) {
            throw new InvalidInputException();
        }
        return Boolean.parseBoolean(input);
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
