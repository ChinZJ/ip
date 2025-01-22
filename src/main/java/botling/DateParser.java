package botling;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Check if user input is valid as a date object.
 */
public class DateParser {
    private static final DateTimeFormatter PREFERRED_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter SAVED_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");
    private static final DateTimeFormatter NO_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /**
     * Checks if given input matches date time syntax required.
     * If successfully parsed, returns an Optional with a LocalDate object inside it.
     */
    public static Optional<LocalDateTime> parseDateTime(String input) {
        try {
            LocalDateTime parsed = LocalDateTime.parse(input, PREFERRED_FORMAT);
            return Optional.of(parsed);
        } catch (DateTimeParseException e) {
            // Do nothing
        }

        try {
            LocalDateTime parsed = LocalDateTime.parse(input, SAVED_FORMAT);
            return Optional.of(parsed);
        } catch (DateTimeParseException e) {
            // Do nothing
        }

        try {
            LocalDate parsed = LocalDate.parse(input, NO_TIME_FORMAT);
            return Optional.of(parsed.atStartOfDay());
        } catch (DateTimeParseException e) {
                // Do nothing.
        }

        return Optional.empty();
    }

}
