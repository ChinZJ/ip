package botling.commands.commandtypes;

import botling.DateParser;
import botling.TaskList;
import botling.commands.CommandColor;
import botling.exceptions.InvalidInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Command interface for all commands to implement.
 */
public interface Command {
    /**
     * Parses the command from the user input
     * @param input User input provided by the user.
     * @param tasks The <code>TaskList</code> object used to perform operations on tasks.
     * @param cmdColor Text meant for GUI.
     * @return Message string used in JUnit testing.
     */
    public String parse(String input, TaskList tasks, CommandColor cmdColor);

    /**
     * Restores the <code>TaskList</code> object based on the existing history file.
     * Note that the BufferedReader is already assumed to be open.
     *
     * @param reader
     * @param tasks
     */
    public void restore(BufferedReader reader, TaskList tasks)
            throws InvalidInputException, IOException ;

    /**
     * Checks if input entry is a string.
     *
     * @param input User input.
     * @return Parse boolean.
     * @throws InvalidInputException When input is null.
     */
    default String validateString(String input) throws InvalidInputException {
        if (input == null) {
            throw new InvalidInputException();
        }
        return input;
    }

    /**
     * Checks if input entry is a boolean.
     *
     * @param input User input.
     * @return Parse boolean.
     * @throws InvalidInputException When input is not a boolean.
     */
    default boolean validateAndParseBool(String input) throws InvalidInputException {
        if (input == null) {
            throw new InvalidInputException();
        } else if (!input.equalsIgnoreCase("true")
                && !input.equalsIgnoreCase("false")) {
            throw new InvalidInputException();
        }
        return Boolean.parseBoolean(input);
    }

    /**
     * Wrapper to validate LocalDateTime object.
     *
     * @param input User input.
     * @return LocalDateTime object.
     * @throws InvalidInputException When input is not a LocalDateTime.
     */
    default LocalDateTime validateAndParseDate(String input) throws InvalidInputException {
        if (input == null) {
            throw new InvalidInputException();
        }
        Optional<LocalDateTime> createDate = DateParser.parseDateTime(input);
        if (createDate.isEmpty()) {
            throw new InvalidInputException();
        }
        return createDate.get();
    }
}
