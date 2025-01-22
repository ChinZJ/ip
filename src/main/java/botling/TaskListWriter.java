package botling;

import botling.exceptions.InvalidInputException;

import botling.tasks.DeadlineDate;
import botling.tasks.Deadlines;
import botling.tasks.EventDate;
import botling.tasks.Events;
import botling.tasks.Task;
import botling.tasks.ToDo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;




/**
 * Used to perform I/O actions for <code>TaskList</code> objects to hard disk where appropriate.
 */
public class TaskListWriter {
    private static final String HISTORY_DATA_FOLDER = "./data";
    private static final String HISTORY_DATA_PATH = "./data/history.txt";

    private String logs;

    /**
     * Checks if the TaskList has been instantiated before.
     * If so, load the previous history of the TaskList.
     * Else generate a new history of the TaskList
     *
     * @return Any relevant logs of trying to generate / retrieve the history of the task list.
     */
    public String restore(TaskList tasks) {
        String message = "\t Attempting to retrieve history...\n";
        File dataDir = new File(TaskListWriter.HISTORY_DATA_FOLDER);
        if (!dataDir.exists()) {
            message += "\t No data folder found! Creating data folder...\n";
            dataDir.mkdir();
        } else {
            message += "\t Data folder found!\n";
        }

        File historyFile = new File(TaskListWriter.HISTORY_DATA_PATH);
        if (!historyFile.exists()) {
            message += "\t No history file found! Creating history file...\n";
            try {
                historyFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating history file: " + e.getMessage());
            }
        } else {
            message += "\t History file found! Restoring data...\n";
            tasks = this.read(tasks);
        }
        return message;
    }

    /**
     * Reads the history file and generates a <code>TaskList</code> object off of it.
     */
    private TaskList read(TaskList tasks) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(TaskListWriter.HISTORY_DATA_PATH));
            String cmd, name, arg1, arg2;
            Optional<LocalDateTime> argTime1, argTime2;
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

                                    // Check if eventFrom and eventTo are of valid LocalDateTime objects.
                                    Optional<LocalDateTime> startDateTimeOpt = DateParser.parseDateTime(arg1);
                                    Optional<LocalDateTime> endDateTimeOpt = DateParser.parseDateTime(arg2);
                                    if (startDateTimeOpt.isPresent() && endDateTimeOpt.isPresent()) {
                                        LocalDateTime startDateTime = startDateTimeOpt.get();
                                        LocalDateTime endDateTime = endDateTimeOpt.get();
                                        if (startDateTime.isBefore(endDateTime) || startDateTime.isEqual(endDateTime)) {
                                            task = new EventDate(name, mark, startDateTime, endDateTime);
                                        } else {
                                            throw new InvalidInputException();
                                        }
                                    } else {
                                        task = new Events(name, mark, arg1, arg2);
                                    }

                                    tasks.add(task);
                                    break;
                            }
                        } catch (InvalidInputException e) {
                            System.out.println("An error occurred while trying to read"
                                    + "the history.txt file."
                                    + "Do you wish to delete it and start again? (y/n)");
                            Scanner scanner = new Scanner(System.in);
                            String response = scanner.nextLine().trim().toLowerCase();

                            while (!response.equals("y") && !response.equals("n")) {
                                System.out.println("Please enter 'y' for yes and 'n' for no.");
                                response = scanner.nextLine().trim().toLowerCase();
                            }
                            if (response.equals("y")) {
                                tasks = new TaskList();
                                File file = new File(TaskListWriter.HISTORY_DATA_PATH);
                                file.delete();
                                file.createNewFile();
                            } else {
                                System.out.println("Program will now terminate. Please check the file.");
                                System.exit(0);
                                return null;
                            }
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
    private boolean validateAndParseBool(String input) throws InvalidInputException {
        if (input == null) {
            throw new InvalidInputException();
        } else if (!input.equalsIgnoreCase("true")
                && !input.equalsIgnoreCase("false")) {
            throw new InvalidInputException();
        }
        return Boolean.parseBoolean(input);
    }


    public void write(TaskList tasks) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(TaskListWriter.HISTORY_DATA_PATH));
            writer.print(tasks.fileString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

}
