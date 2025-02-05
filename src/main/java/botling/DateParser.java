package botling;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Check if user input is valid as a date object.
 */
public class DateParser {
    private static final Map<String, DateTimeFormatter> DATE_MAPPER;

    static {
        Map<String, DateTimeFormatter> tempMap = new HashMap<>();
        tempMap.put("yy-MM-dd", DateTimeFormatter.ofPattern("yy-MM-dd"));
        tempMap.put("yy-MM-dd HHmm", DateTimeFormatter.ofPattern("yy-MM-dd HHmm"));
        tempMap.put("yyyy-MM-dd", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        tempMap.put("yyyy-MM-dd HHmm", DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        tempMap.put("dd/MM/yyyy", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        tempMap.put("dd/MM/yyyy HHmm", DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
        tempMap.put("dd/MM/yy", DateTimeFormatter.ofPattern("dd/MM/yy"));
        tempMap.put("dd/MM/yy HHmm", DateTimeFormatter.ofPattern("dd/MM/yy HHmm"));
        tempMap.put("dd MMM yyyy", DateTimeFormatter.ofPattern("dd MMM yyyy"));
        tempMap.put("dd MMM yyyy HHmm", DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
        tempMap.put("dd MMM yy", DateTimeFormatter.ofPattern("dd MMM yy"));
        tempMap.put("dd MMM yy HHmm", DateTimeFormatter.ofPattern("dd MMM yy HHmm"));
        DATE_MAPPER = Collections.unmodifiableMap(tempMap);
    }

    /**
     * Checks if given input matches date time syntax required.
     * If successfully parsed, returns an Optional with a LocalDate object inside it.
     */
    public static Optional<LocalDateTime> parseDateTime(String input) {
        return DATE_MAPPER.entrySet()
                .stream()
                .map(entry ->
                        tryParse(input, entry))
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(Optional.empty());
    }

    private static Optional<LocalDateTime> tryParse(String input,
            Map.Entry<String, DateTimeFormatter> entry) {
        try {
            if (entry.getKey().contains("HHmm")) {
                return Optional.of(LocalDateTime.parse(input, entry.getValue()));
            } else {
                return Optional.of(LocalDate.parse(input, entry.getValue()).atStartOfDay());
            }
        } catch (DateTimeParseException e) {
            // Do nothing, will return Optional empty if invalid date
        }
        return Optional.empty();
    }

}
