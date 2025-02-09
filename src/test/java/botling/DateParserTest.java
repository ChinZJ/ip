package botling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;


public class DateParserTest {
    @Test
    public void yyMmDdFormatTest() {
        // yy-MM-dd HHmm format
        String testFormat = "25-12-09 2359";
        DateTimeFormatter testFormatter = DateTimeFormatter.ofPattern("yy-MM-dd HHmm");
        Optional<LocalDateTime> result = Optional.of(
                LocalDateTime.parse(testFormat, testFormatter));
        assertEquals(result, new DateParser().parseDateTime(testFormat));

        // yy-MM-dd format
        testFormat = "25-12-09";
        testFormatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        result = Optional.of(
                LocalDate.parse(testFormat, testFormatter).atStartOfDay());
        assertEquals(result, new DateParser().parseDateTime(testFormat));

        // yyyy-MM-dd HHmm format.
        testFormat = "2025-12-09 2359";
        testFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        result = Optional.of(
                LocalDateTime.parse(testFormat, testFormatter));
        assertEquals(result, new DateParser().parseDateTime(testFormat));

        // yyyy-MM-dd format.
        testFormat = "2024-01-04";
        testFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        result = Optional.of(LocalDate.parse(testFormat, testFormatter).atStartOfDay());
        assertEquals(result, new DateParser().parseDateTime(testFormat));
    }

    @Test
    public void ddMmYyFormatTest() {
        // dd/MM/yyyy HHmm format.
        String testFormat = "02/12/2024 1234";
        DateTimeFormatter testFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        Optional<LocalDateTime> result = Optional.of(
                LocalDateTime.parse(testFormat, testFormatter));
        assertEquals(result, new DateParser().parseDateTime(testFormat));

        // dd/MM/yyyy format.
        testFormat = "02/12/2024";
        testFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        result = Optional.of(LocalDate.parse(testFormat, testFormatter).atStartOfDay());
        assertEquals(result, new DateParser().parseDateTime(testFormat));

        // dd/MM/yy HHmm format.
        testFormat = "02/12/24 1234";
        testFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HHmm");
        result = Optional.of(LocalDateTime.parse(testFormat, testFormatter));
        assertEquals(result, new DateParser().parseDateTime(testFormat));

        // dd/MM/yy format.
        testFormat = "02/12/24";
        testFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        result = Optional.of(LocalDate.parse(testFormat, testFormatter).atStartOfDay());
        assertEquals(result, new DateParser().parseDateTime(testFormat));
    }

    @Test
    public void ddMmmYyFormatTest() {
        // dd MMM yyyy HHmm format.
        String testFormat = "02 Jan 2024 1234";
        DateTimeFormatter testFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");
        Optional<LocalDateTime> result = Optional.of(
                LocalDateTime.parse(testFormat, testFormatter));
        assertEquals(result, new DateParser().parseDateTime(testFormat));

        // dd MMM yyyy format.
        testFormat = "02 Jan 2024";
        testFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        result = Optional.of(LocalDate.parse(testFormat, testFormatter).atStartOfDay());
        assertEquals(result, new DateParser().parseDateTime(testFormat));

        // dd MMM yy HHmm format.
        testFormat = "02 Jan 24 1234";
        testFormatter = DateTimeFormatter.ofPattern("dd MMM yy HHmm");
        result = Optional.of(LocalDateTime.parse(testFormat, testFormatter));
        assertEquals(result, new DateParser().parseDateTime(testFormat));

        // dd MMM yyyy format.
        testFormat = "02 Jan 24";
        testFormatter = DateTimeFormatter.ofPattern("dd MMM yy");
        result = Optional.of(LocalDate.parse(testFormat, testFormatter).atStartOfDay());
        assertEquals(result, new DateParser().parseDateTime(testFormat));
    }

    @Test
    public void newDayFormatTest() {
        // New day.
        String testFormat = "02 Jan 2024 2400";
        DateTimeFormatter testFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HHmm");
        Optional<LocalDateTime> result = Optional.of(
                LocalDateTime.parse(testFormat, testFormatter));
        assertEquals(result, new DateParser().parseDateTime(testFormat));
    }

    @Test
    public void failFormatTest() {
        // Month does not exist
        String invalidMonth = "2023-99-11";
        DateParser dateParser = new DateParser();
        assertFalse(dateParser.isInvalid());

        assertEquals(Optional.empty(), dateParser.parseDateTime(invalidMonth));
        assertTrue(dateParser.isInvalid());


        // Invalid time.
        dateParser = new DateParser();
        String invalidTime = "2023-12-03 2500";
        assertEquals(Optional.empty(), dateParser.parseDateTime(invalidTime));
        assertTrue(dateParser.isInvalid());
    }

}
