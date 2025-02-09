package botling.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class DeadlinesTest {

    /**
     * Test constructor.
     * Note that solving of dates is not the responsibility ot the Events class
     * and is thus not tested here.
     */
    @Test
    public void defaultTest() {
        LocalDateTime testTime = LocalDateTime.parse("05 Feb 2025 1116",
                DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
        Deadlines testDeadlines = new Deadlines("yes", false, "startDate",
                Optional.empty(), testTime);

        assertEquals("yes", testDeadlines.toString());
        assertEquals("[D][ ] yes (by: startDate)",
                testDeadlines.getTaskStatus());
        assertEquals("deadline\nstartDate\nyes\nfalse\n05 Feb 2025 1116",
                testDeadlines.getTaskData());
        assertFalse(testDeadlines.hasDate());
        assertEquals(testTime.toString(), testDeadlines.getCreateDate().toString());
        assertEquals(testTime.toString(), testDeadlines.getStartDate().toString());
        assertEquals(testTime.toString(), testDeadlines.getEndDate().toString());
        assertEquals(" [D][X] yes (by: startDate)",
                testDeadlines.updateTask(true));
        assertEquals(" [D][X] yes (by: startDate)",
                testDeadlines.updateTask(true));
        assertEquals(" [D][ ] yes (by: startDate)",
                testDeadlines.updateTask(false));

        testDeadlines = new Deadlines("yes", true, "startDate",
                Optional.empty(), testTime);

        assertEquals("[D][X] yes (by: startDate)",
                testDeadlines.getTaskStatus());
        assertEquals("deadline\nstartDate\nyes\ntrue\n05 Feb 2025 1116",
                testDeadlines.getTaskData());
        assertFalse(testDeadlines.hasDate());
        assertEquals(testTime.toString(), testDeadlines.getCreateDate().toString());
        assertEquals(testTime.toString(), testDeadlines.getStartDate().toString());
        assertEquals(testTime.toString(), testDeadlines.getEndDate().toString());

        LocalDateTime endTime = LocalDateTime.parse("05 Feb 2026 1116",
                DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
        testDeadlines = new Deadlines("yes", false, "startDate",
                Optional.of(endTime), testTime);
        assertEquals("[DATE] [D][ ] yes (by: 05 Feb 2026 1116)",
                testDeadlines.getTaskStatus());
        assertEquals("deadline\n05 Feb 2026 1116\nyes\nfalse\n05 Feb 2025 1116",
                testDeadlines.getTaskData());
        assertTrue(testDeadlines.hasDate());
        assertEquals(testTime.toString(), testDeadlines.getCreateDate().toString());
        assertEquals(testTime.toString(), testDeadlines.getStartDate().toString());
        assertEquals(endTime.toString(), testDeadlines.getEndDate().toString());
    }
}
