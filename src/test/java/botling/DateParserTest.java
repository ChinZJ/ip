package botling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;


public class DateParserTest {
    @Test
    public void passFormatTest() {
        // yyyy-MM-dd HHmm format.
        String preferredFormat = "2025-12-09 2359";
        DateTimeFormatter preferredFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        Optional<LocalDateTime> result = Optional.of(
                LocalDateTime.parse(preferredFormat, preferredFormatter));
        assertEquals(result, DateParser.parseDateTime(preferredFormat));

        // dd MMM yyyy HHmm format.
        String savedFormat = "02 Jan 2024 1234";
        DateTimeFormatter savedFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");
        result = Optional.of(LocalDateTime.parse(savedFormat, savedFormatter));
        assertEquals(result, DateParser.parseDateTime(savedFormat));

        // yyyy-MM-dd format.
        String noTimeFormat = "2024-01-04";
        DateTimeFormatter noTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        result = Optional.of(LocalDate.parse(noTimeFormat, noTimeFormatter).atStartOfDay());
        assertEquals(result, DateParser.parseDateTime(noTimeFormat));

        // New day.
        String newDay = "02 Jan 2024 2400";
        DateTimeFormatter newDayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");
        result = Optional.of(LocalDateTime.parse(newDay, newDayFormatter));
        assertEquals(result, DateParser.parseDateTime(newDay));
    }

    @Test
    public void failFormatTest() {
        // Month does not exist
        String invalidMonth = "2023-99-11";
        assertEquals(Optional.empty(), DateParser.parseDateTime(invalidMonth));

        // Invalid time.
        String invalidTime = "2023-12-03 2500";
        assertEquals(Optional.empty(), DateParser.parseDateTime(invalidTime));
    }

}