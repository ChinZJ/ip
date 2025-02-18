package botling.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class ToDoTest {
    private final LocalDateTime testTime = LocalDateTime.parse("05 Feb 2025 1116",
            DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));

    /**
     * Test constructor.
     */
    @Test
    public void unmarkTest() {
        ToDo testTodo = new ToDo("yes", false, testTime);

        assertEquals("yes", testTodo.toString());
        assertEquals("[T][ ] yes", testTodo.getTaskStatus());
        assertEquals("todo\nyes\nfalse\n05 Feb 2025 1116", testTodo.getTaskData());
        assertFalse(testTodo.hasDate());
        assertEquals(testTime.toString(), testTodo.getCreateDate().toString());
        assertEquals(testTime.toString(), testTodo.getStartDate().toString());
        assertEquals(testTime.toString(), testTodo.getEndDate().toString());
        assertEquals(" [T][X] yes", testTodo.updateTask(true));
        assertEquals(" [T][X] yes", testTodo.updateTask(true));
        assertEquals(" [T][ ] yes", testTodo.updateTask(false));
    }

    @Test
    public void markTest() {
        ToDo testTodo = new ToDo("yes", true, testTime);

        assertEquals("[T][X] yes", testTodo.getTaskStatus());
        assertEquals("todo\nyes\ntrue\n05 Feb 2025 1116", testTodo.getTaskData());
        assertFalse(testTodo.hasDate());
        assertEquals(testTime.toString(), testTodo.getCreateDate().toString());
        assertEquals(testTime.toString(), testTodo.getStartDate().toString());
        assertEquals(testTime.toString(), testTodo.getEndDate().toString());
    }
}
