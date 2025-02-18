package botling.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class EventsTest {
    private final LocalDateTime testTime = LocalDateTime.parse("05 Feb 2025 1116",
            DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
    private final LocalDateTime startTime = LocalDateTime.parse("05 Feb 2024 1116",
            DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
    private final LocalDateTime endTime = LocalDateTime.parse("05 Feb 2026 1116",
            DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));

    /**
     * Test constructor.
     * Note that solving of dates is not the responsibility ot the Events class
     * and is thus not tested here.
     */
    @Test
    public void noDateUnmarkTest() {
        Events testEvents = new Events("yes", false, "startDate", "endDate",
                Optional.empty(), Optional.empty(), testTime);

        assertEquals("yes", testEvents.toString());
        assertEquals("[E][ ] yes (from: startDate to: endDate)",
                testEvents.getTaskStatus());
        assertEquals("event\nstartDate\nendDate\nyes\nfalse\n05 Feb 2025 1116",
                testEvents.getTaskData());
        assertFalse(testEvents.hasDate());
        assertEquals(testTime.toString(), testEvents.getCreateDate().toString());
        assertEquals(testTime.toString(), testEvents.getStartDate().toString());
        assertEquals(testTime.toString(), testEvents.getEndDate().toString());
        assertEquals(" [E][X] yes (from: startDate to: endDate)",
                testEvents.updateTask(true));
        assertEquals(" [E][X] yes (from: startDate to: endDate)",
                testEvents.updateTask(true));
        assertEquals(" [E][ ] yes (from: startDate to: endDate)",
                testEvents.updateTask(false));
    }

    @Test
    public void noDateMarkTest() {
        Events testEvents = new Events("yes", true, "startDate", "endDate",
                Optional.empty(), Optional.empty(), testTime);

        assertEquals("[E][X] yes (from: startDate to: endDate)",
                testEvents.getTaskStatus());
        assertEquals("event\nstartDate\nendDate\nyes\ntrue\n05 Feb 2025 1116",
                testEvents.getTaskData());
        assertFalse(testEvents.hasDate());
        assertEquals(testTime.toString(), testEvents.getCreateDate().toString());
        assertEquals(testTime.toString(), testEvents.getStartDate().toString());
        assertEquals(testTime.toString(), testEvents.getEndDate().toString());
    }

    @Test
    public void singleDateTest() {
        Events testEvents = new Events("yes", false, "startDate", "endDate",
                Optional.empty(), Optional.of(endTime), testTime);
        assertEquals("[DATE] [E][ ] yes (from: startDate to: 05 Feb 2026 1116)",
                testEvents.getTaskStatus());
        assertEquals("event\nstartDate\n05 Feb 2026 1116\nyes\nfalse\n05 Feb 2025 1116",
                testEvents.getTaskData());
        assertTrue(testEvents.hasDate());
        assertEquals(testTime.toString(), testEvents.getCreateDate().toString());
        assertEquals(testTime.toString(), testEvents.getStartDate().toString());
        assertEquals(endTime.toString(), testEvents.getEndDate().toString());
    }

    @Test
    public void doubleDateTest() {
        Events testEvents = new Events("yes", false, "startDate", "endDate",
                Optional.of(startTime), Optional.empty(), testTime);
        assertEquals("[DATE] [E][ ] yes (from: 05 Feb 2024 1116 to: endDate)",
                testEvents.getTaskStatus());
        assertEquals("event\n05 Feb 2024 1116\nendDate\nyes\nfalse\n05 Feb 2025 1116",
                testEvents.getTaskData());
        assertTrue(testEvents.hasDate());
        assertEquals(testTime.toString(), testEvents.getCreateDate().toString());
        assertEquals(startTime.toString(), testEvents.getStartDate().toString());
        assertEquals(testTime.toString(), testEvents.getEndDate().toString());

        testEvents = new Events("yes", false, "startDate", "endDate",
                Optional.of(startTime), Optional.of(endTime), testTime);
        assertEquals("[DATE] [E][ ] yes (from: 05 Feb 2024 1116 to: 05 Feb 2026 1116)",
                testEvents.getTaskStatus());
        assertEquals("event\n05 Feb 2024 1116\n05 Feb 2026 1116"
                + "\nyes\nfalse\n05 Feb 2025 1116", testEvents.getTaskData());
        assertTrue(testEvents.hasDate());
        assertEquals(testTime.toString(), testEvents.getCreateDate().toString());
        assertEquals(startTime.toString(), testEvents.getStartDate().toString());
        assertEquals(endTime.toString(), testEvents.getEndDate().toString());
    }
}
