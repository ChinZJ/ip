package botling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import botling.tasks.Deadlines;
import botling.tasks.Events;
import botling.tasks.Task;
import botling.tasks.ToDo;

public class TaskListTest {
    private final LocalDateTime createTime = LocalDateTime.parse("05 Feb 2025 1116",
            DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
    private final LocalDateTime laterCreateTime = LocalDateTime.parse("10 Feb 2025 1116",
            DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
    private final LocalDateTime startTime = LocalDateTime.parse("05 Feb 2024 1116",
            DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
    private final LocalDateTime lastTime = LocalDateTime.parse("05 Feb 2026 1116",
            DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));

    private final String expectedMessage1 = " 1. [DATE] [E][X] not supposed to exist hack"
            + " (from: 05 Feb 2024 1116 to: 05 Feb 2024 1116)\n"
            + " 2. [DATE] [E][X] earliestEvent "
            + "(from: 05 Feb 2024 1116 to: 05 Feb 2024 1116)\n"
            + " 3. [DATE] [E][X] middleEvent "
            + "(from: 05 Feb 2025 1116 to: 05 Feb 2025 1116)\n"
            + " 4. [DATE] [D][ ] earlyDeadline (by: 05 Feb 2026 1116)\n"
            + " 5. [DATE] [E][X] lastEvent (from: startTime to: 05 Feb 2026 1116)\n"
            + " 6. [DATE] [D][ ] lateDeadline (by: 05 Feb 2026 1116)\n"
            + " 7. [T][ ] todo\n"
            + " 8. [T][ ] zzzzzzzz";

    private final String expectedMessage2 = " 2. [DATE] [E][X] earliestEvent "
            + "(from: 05 Feb 2024 1116 to: 05 Feb 2024 1116)\n"
            + " 3. [DATE] [E][X] middleEvent (from: 05 Feb 2025 1116 to: 05 Feb 2025 1116)\n"
            + " 5. [DATE] [E][X] lastEvent (from: startTime to: 05 Feb 2026 1116)";

    private final String expectedMessage3 = " 1. [DATE] [E][X] not supposed to exist hack"
            + " (from: 05 Feb 2024 1116 to: 05 Feb 2024 1116)\n"
            + " 2. [DATE] [E][ ] earliestEvent "
            + "(from: 05 Feb 2024 1116 to: 05 Feb 2024 1116)\n"
            + " 3. [DATE] [E][ ] middleEvent "
            + "(from: 05 Feb 2025 1116 to: 05 Feb 2025 1116)\n"
            + " 4. [DATE] [D][ ] earlyDeadline (by: 05 Feb 2026 1116)\n"
            + " 5. [DATE] [E][X] lastEvent (from: startTime to: 05 Feb 2026 1116)\n"
            + " 6. [DATE] [D][ ] lateDeadline (by: 05 Feb 2026 1116)\n"
            + " 7. [T][ ] todo\n"
            + " 8. [T][ ] zzzzzzzz";

    private final Task testTodo = new ToDo("todo", false, createTime);
    private final Task testLastTodo = new ToDo("zzzzzzzz", false, createTime);
    private final Task testEarlyDeadline = new Deadlines("earlyDeadline", false, "dummy",
            Optional.of(lastTime), createTime);
    private final Task testLateDeadline = new Deadlines("lateDeadline", false, "dummy",
            Optional.of(lastTime), laterCreateTime);
    private final Task testEarliestEvent = new Events("earliestEvent", true, "dummy", "dummy",
            Optional.of(startTime), Optional.of(startTime), createTime);
    private final Task testMiddleEvent = new Events("middleEvent", true, "dummy", "dummy",
            Optional.of(createTime), Optional.of(createTime), createTime);
    private final Task notSupposedToExistEarliestTime = new Events("not supposed to exist hack",
            true, "dummy", "dummy",
            Optional.of(startTime), Optional.of(startTime), startTime);
    private final Task lastEvent = new Events("lastEvent", true, "startTime", "dummy",
            Optional.empty(), Optional.of(lastTime), createTime);

    private final String expectedMessage4 = " 1. [DATE] [E][X] not supposed to exist hack"
            + " (from: 05 Feb 2024 1116 to: 05 Feb 2024 1116)\n"
            + " 2. [DATE] [E][ ] earliestEvent "
            + "(from: 05 Feb 2024 1116 to: 05 Feb 2024 1116)\n"
            + " 3. [DATE] [D][ ] earlyDeadline (by: 05 Feb 2026 1116)\n"
            + " 4. [DATE] [E][X] lastEvent (from: startTime to: 05 Feb 2026 1116)\n"
            + " 5. [DATE] [D][ ] lateDeadline (by: 05 Feb 2026 1116)\n"
            + " 6. [T][ ] todo\n"
            + " 7. [T][ ] zzzzzzzz";

    private final String expectedMessage5 = "event\n05 Feb 2024 1116\n05 Feb 2024 1116\n"
            + "not supposed to exist hack\ntrue\n05 Feb 2024 1116\n"
            + "event\n05 Feb 2024 1116\n05 Feb 2024 1116\n"
            + "earliestEvent\nfalse\n05 Feb 2025 1116\n"
            + "deadline\n05 Feb 2026 1116\nearlyDeadline\nfalse\n05 Feb 2025 1116\n"
            + "event\nstartTime\n05 Feb 2026 1116\nlastEvent\ntrue\n05 Feb 2025 1116\n"
            + "deadline\n05 Feb 2026 1116\nlateDeadline\nfalse\n10 Feb 2025 1116\n"
            + "todo\ntodo\nfalse\n05 Feb 2025 1116\ntodo\nzzzzzzzz\nfalse\n05 Feb 2025 1116";

    @Test
    public void singleFunctionalityTest() {
        TaskList tasks = new TaskList();
        assertTrue(tasks.isOpen());
        tasks.hasClose();
        assertFalse(tasks.isOpen());
        tasks.hasOpen();
        assertTrue(tasks.isOpen());
    }

    @Test
    public void singleTaskTest() {
        TaskList tasks = new TaskList();
        assertEquals(0, tasks.size());

        tasks.add(testTodo);
        assertEquals(1, tasks.size());
        assertEquals(testTodo, tasks.get(0));
        tasks.remove(0);
        assertEquals(0, tasks.size());

        tasks.add(testTodo);
        tasks.clear();
        assertEquals(0, tasks.size());
    }

    @Test
    public void multiTaskTest() {
        TaskList tasks = new TaskList();
        tasks.add(testTodo);
        tasks.add(testLastTodo);
        tasks.add(testEarlyDeadline);
        tasks.add(testLateDeadline);
        tasks.add(testEarliestEvent);
        tasks.add(testMiddleEvent);
        tasks.add(notSupposedToExistEarliestTime);
        tasks.add(lastEvent);

        assertEquals(8, tasks.list().length);
        assertEquals(expectedMessage1, String.join("", tasks.list()));

        assertEquals(expectedMessage2, String.join("", tasks.find("ev")));

        tasks.unmark(1);
        tasks.mark(4);
        tasks.mark(2);
        tasks.unmark(2);
        assertEquals(expectedMessage3, String.join("", tasks.list()));

        tasks.remove(2);
        assertEquals(expectedMessage4, String.join("", tasks.list()));

        assertEquals(expectedMessage5, tasks.fileString());
    }
}
