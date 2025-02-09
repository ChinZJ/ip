package botling.commands.commandtypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import botling.DateParser;
import botling.TaskList;
import botling.exceptions.InvalidInputException;

/**
 * Specialised interface for task commands that have to read from the history file as well.
 */
public interface TasksCmd extends Command {

    /**
     * Restores the <code>TaskList</code> object based on the existing history file.
     * Note that the BufferedReader is already assumed to be open.
     *
     * @param reader Reads inputs from history file.
     * @param tasks Contains list of tasks to be edited.
     */
    void restore(BufferedReader reader, TaskList tasks)
            throws InvalidInputException, IOException;

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
        Optional<LocalDateTime> createDate = new DateParser().parseDateTime(input);
        if (createDate.isEmpty()) {
            throw new InvalidInputException();
        }
        return createDate.get();
    }
}
