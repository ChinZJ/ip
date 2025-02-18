package botling.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import botling.TaskList;
import botling.TaskListWriter;
import botling.tasks.Deadlines;
import botling.tasks.Events;
import botling.tasks.Task;
import botling.tasks.ToDo;

public class CommandParserTest {
    private static final String NEW_FILE_MESSAGE = "Attempting to retrieve history...\n"
            + "Data folder found!\n"
            + "History file found! Restoring data...\n\nHey! I'm Botling!\n"
            + "What can I do for you?";
    private static final String BYE_MESSAGE = "Shell-be seeing you!";
    private static final String UNKNOWN_MESSAGE = "Yikes!!! "
            + "This command is still up for discussion.\n"
            + "Type 'help' for a list of commands and their syntax!";
    private static final String NO_TASK_MESSAGE = "";
    private static final String DATE_FORMAT_MESSAGE = "If using a date, "
            + "the accepted format are as follows:\n"
            + "'yy(yy)-MM-dd ', 'dd/MM/yy(yy)' or 'dd MMM yy(yy)'"
            + "(month in short form e.g. Jan), optionally with HHmm in 24hour format.\n"
            + "Else, normal text is accepted as well!";

    private CommandParser cmdParse;
    private CommandColor cmdColor;
    private TaskList tasks;

    /**
     * Resets all private variables.
     */
    private void resetVariables() {
        cmdParse = new CommandParser();
        cmdColor = new CommandColor();
        tasks = new TaskList();
    }

    /**
     * Adds a varying number of <code>Task</code> objects to tasks.
     */
    private void addTasks(Task... toAddTasks) {
        for (Task task : toAddTasks) {
            tasks.add(task);
        }
    }

    /**
     * Generates the message when a <code>Task</code> is added to the <code>TaskList</code>.
     */
    private String addTaskMessage(String taskName, int tasksSize) {
        return "You threw this into the ocean:\n"
                + taskName + "Now you have " + tasksSize + " tasks polluting my waters!";
    }

    @BeforeAll
    public static void setUp() {
        TaskListWriter.createTemp();
    }

    @AfterAll
    public static void cleanUp() {
        TaskListWriter.restoreTemp();
    }

    @Test
    public void startTest() {
        resetVariables();
        TaskListWriter.recreateFile();
        String output = CommandParser.start(tasks, cmdColor);

        assertEquals(NEW_FILE_MESSAGE, output);
    }

    @Test
    public void byeTest() {
        resetVariables();

        // Valid input.
        assertEquals(BYE_MESSAGE, cmdParse.parse("bye", tasks, cmdColor));

        tasks.hasOpen();
        assertEquals(BYE_MESSAGE, cmdParse.parse("bye ", tasks, cmdColor));

        // Invalid input.
        tasks.hasOpen();
        assertEquals(UNKNOWN_MESSAGE, cmdParse.parse("bye/", tasks, cmdColor));
    }

    @Test
    public void listTest() {
        // Tasks in list are not rigorously tested.
        // They should be tested in their respective classes.
        resetVariables();

        // Empty list.
        String result = "Oceans clean, I'm free!";
        assertEquals(result, cmdParse.parse("list", tasks, cmdColor));

        // List with item.
        ToDo unmarkTodo = new ToDo("unmark", false, LocalDateTime.now());
        addTasks(unmarkTodo);
        result = "Here's whats sinking:\n"
                + " 1. [T][ ] unmark";
        assertEquals(result, cmdParse.parse("list", tasks, cmdColor));

        // List with marked item.
        tasks.remove(0);
        ToDo markTodo = new ToDo("mark", true, LocalDateTime.now());
        addTasks(markTodo);
        result = "Here's whats sinking:\n"
                + " 1. [T][X] mark";
        assertEquals(result, cmdParse.parse("list", tasks, cmdColor));

        // Invalid input.
        assertEquals(UNKNOWN_MESSAGE, cmdParse.parse("list/", tasks, cmdColor));
    }

    @Test
    public void findTest() {
        resetVariables();

        // Find upper and lower case
        // Simultaneously test for special characters and spaces
        ToDo task1 = new ToDo(" ");
        Deadlines task2 = new Deadlines(" b", "cc",
                Optional.empty());
        Events task3 = new Events(" B", "CC", "DD",
                Optional.empty(), Optional.empty());

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime time = LocalDateTime.parse("2024-01-02 0000", format);
        Deadlines task4 = new Deadlines(")(*&^%$#@!", "2024-01-02 0000",
                Optional.of(time));
        Events task5 = new Events("!@#$%^&*(", "2024-01-02 0000", "2024-01-02 0000",
                Optional.of(time), Optional.of(time));

        addTasks(task1, task2, task3, task4, task5);

        // Spaces.
        String result = "I chewed on some, but here's the remnants:\n"
                + " 3. [T][ ]  \n"
                + " 4. [E][ ]  B (from: CC to: DD)\n"
                + " 5. [D][ ]  b (by: cc)";
        assertEquals(result, cmdParse.parse("find  ", tasks, cmdColor));

        // Upper and lower case.
        result = "I chewed on some, but here's the remnants:\n"
                + " 4. [E][ ]  B (from: CC to: DD)\n"
                + " 5. [D][ ]  b (by: cc)";
        assertEquals(result, cmdParse.parse("find  b", tasks, cmdColor));
        assertEquals(result, cmdParse.parse("find  B", tasks, cmdColor));

        // Character in other fields.
        result = "Don't see any, check the landfill!";
        assertEquals(result, cmdParse.parse("find c", tasks, cmdColor));

        // Find special characters
        result = "I chewed on some, but here's the remnants:\n"
                + " 1. [DATE] [E][ ] !@#$%^&*( (from: 02 Jan 2024 0000 to: 02 Jan 2024 0000)\n"
                + " 2. [DATE] [D][ ] )(*&^%$#@! (by: 02 Jan 2024 0000)";
        assertEquals(result, cmdParse.parse("find !", tasks, cmdColor));

        // Invalid input
        assertEquals(UNKNOWN_MESSAGE, cmdParse.parse("find", tasks, cmdColor));
    }

    @Test
    public void markTest() {
        resetVariables();

        // Testing empty list
        assertEquals("Yikes!!! The format of mark should be mark <X>, "
                + "though you have no tasks!",
                cmdParse.parse("mark 1", tasks, cmdColor));

        ToDo task = new ToDo("unmark", false, LocalDateTime.now());
        addTasks(task);

        // Correct input.
        String result = "Nice! I've swallowed this task:\n"
                + " [T][X] unmark";
        tasks.unmark(0);
        assertEquals(result, cmdParse.parse("mark 1", tasks, cmdColor));
        // Marking the same object again.
        assertEquals(result, cmdParse.parse("mark 1", tasks, cmdColor));

        // Exceed size.
        result = "Yikes!!! The format of mark should be mark <X>, "
                + "where X is a positive integer <= 1";
        assertEquals(result, cmdParse.parse("mark 9", tasks, cmdColor));

        // Float.
        assertEquals(result, cmdParse.parse("mark 92.6", tasks, cmdColor));

        // Long.
        assertEquals(result, cmdParse.parse("mark 5147483647", tasks, cmdColor));

        // Negative integer.
        assertEquals(result, cmdParse.parse("mark -900", tasks, cmdColor));
    }

    @Test
    public void unmarkTest() {
        resetVariables();

        // Testing empty list
        assertEquals("Yikes!!! The format of unmark should be unmark <X>, "
                + "though you have no tasks!", cmdParse.parse("unmark 1", tasks, cmdColor));

        ToDo task = new ToDo("unmark", true, LocalDateTime.now());
        addTasks(task);

        // Correct input.
        String result = "Yuck, I've spat out this task:\n"
                + " [T][ ] unmark";
        tasks.mark(0);
        assertEquals(result, cmdParse.parse("unmark 1", tasks, cmdColor));

        // Marking the same object again.
        assertEquals(result, cmdParse.parse("unmark 1", tasks, cmdColor));

        // Exceed size.
        result = "Yikes!!! The format of unmark should be unmark <X>, "
                + "where X is a positive integer <= 1";
        assertEquals(result, cmdParse.parse("unmark 9", tasks, cmdColor));

        // Float.
        assertEquals(result, cmdParse.parse("unmark 92.6", tasks, cmdColor));

        // Long.
        assertEquals(result, cmdParse.parse("unmark 5147483647", tasks, cmdColor));

        // Negative integer.
        assertEquals(result, cmdParse.parse("unmark -900", tasks, cmdColor));
    }

    @Test
    public void deleteTest() {
        resetVariables();

        // Testing empty list
        assertEquals("Yikes!!! The format of unmark should be unmark <X>, "
                + "though you have no tasks!", cmdParse.parse("unmark 1", tasks, cmdColor));

        ToDo task = new ToDo("delete", false, LocalDateTime.now());
        tasks.add(task);

        // Correct input.
        String result = "This task has degraded into nothingness:\n"
                + " [T][ ] delete\n"
                + "Now you have 0 tasks polluting my waters!";
        assertEquals(result, cmdParse.parse("delete 1", tasks, cmdColor));

        // Exceed size.
        tasks.add(task);
        result = "Yikes!!! The format of delete should be delete <X>,"
                + " where X is a positive integer <= 1";
        assertEquals(result, cmdParse.parse("delete 9", tasks, cmdColor));

        // Float.
        assertEquals(result, cmdParse.parse("delete 92.6", tasks, cmdColor));

        // Long.
        assertEquals(result, cmdParse.parse("delete 5147483647", tasks, cmdColor));

        // Negative integer.
        assertEquals(result, cmdParse.parse("delete -900", tasks, cmdColor));
    }

    @Test
    public void todoTest() {
        resetVariables();

        // Valid input.
        String result = "You threw this into the ocean:\n"
                + " [T][ ]  \n"
                + "Now you have 1 tasks polluting my waters!";
        assertEquals(result, cmdParse.parse("todo  ", tasks, cmdColor));

        // No descriptor.
        result = "Yikes!!! The format of todo should be todo <name>.";
        assertEquals(result, cmdParse.parse("todo", tasks, cmdColor));
    }

    @Test
    public void deadlineTest() {
        resetVariables();

        // Standard input.
        String result = addTaskMessage(" [D][ ]   (by:  )\n", 1);
        assertEquals(result, cmdParse.parse("deadline   /by  ", tasks, cmdColor));

        // Multiple /by commandtypes
        // Nested deadline commandtypes.
        result = addTaskMessage(" [D][ ] deadline (by: deadline /by abc)\n", 2);
        assertEquals(result, cmdParse.parse("deadline deadline /by deadline /by abc",
                tasks, cmdColor));

        // deadline with date.
        // deadline with alternate time formats are not tested
        // because that is the functionality of DateParser object.
        result = addTaskMessage(" [DATE] [D][ ]   (by: 02 Jan 2024 0000)\n", 3);
        assertEquals(result, cmdParse
                .parse("deadline   /by 2024-01-02 0000", tasks, cmdColor));

        // Invalid input.
        result = "Yikes!!! The format of deadline should be deadline"
                + " <name> /by <deadline (may be date)>.\n"
                + DATE_FORMAT_MESSAGE;
        assertEquals(result, cmdParse.parse("deadline", tasks, cmdColor));
    }

    @Test
    public void eventTest() {
        resetVariables();

        // Standard input.
        String result = addTaskMessage(" [E][ ]   (from:   to:  )\n", 1);
        assertEquals(result, cmdParse.parse("event   /from   /to  ", tasks, cmdColor));

        // Nested event commandtypes.
        result = addTaskMessage(" [E][ ]   (from: event  to: /from asd /to asd )\n",
                2);
        assertEquals(result, cmdParse.parse("event   /from event  /to /from asd /to asd ",
                tasks, cmdColor));

        // Multiple /from /to commandtypes.
        result = addTaskMessage(" [E][ ] a (from: b /from c to: d /to e)\n", 3);
        assertEquals(result, cmdParse.parse("event a /from b /from c /to d /to e",
                tasks, cmdColor));

        result = addTaskMessage(" [E][ ] a (from: b to: c /from d /to e)\n", 4);
        assertEquals(result, cmdParse.parse("event a /from b /to c /from d /to e",
                tasks, cmdColor));

        result = addTaskMessage(" [E][ ] a (from: b to: c /to d /from e)\n", 5);
        assertEquals(result, cmdParse.parse("event a /from b /to c /to d /from e",
                tasks, cmdColor));

        result = addTaskMessage(" [E][ ] a /to b (from: c to: d /from e)\n", 6);
        assertEquals(result, cmdParse.parse("event a /to b /from c /to d /from e",
                tasks, cmdColor));

        result = addTaskMessage(" [E][ ] a /to b (from: c /from d to: e)\n", 7);
        assertEquals(result, cmdParse.parse("event a /to b /from c /from d /to e",
                tasks, cmdColor));

        // Doubles as invalid input.
        result = "Yikes!!! The format of event should be event <name> /from <start "
                + "(may be date)> /to <end (may be date)>.\n"
                + "<start> should be before or equal to <end> if dates are inputs.\n"
                + DATE_FORMAT_MESSAGE;
        assertEquals(result, cmdParse.parse("event a /to b /to c /from d /from e",
                tasks, cmdColor));

        // event with invalid start and end date
        assertEquals(result, cmdParse.parse(
                "event a /from 02 Jan 2024 0000 /to 02 Jan 2022 0000",
                tasks, cmdColor));

        // event with date.
        // Doubles to check that same date works, but not before.
        result = addTaskMessage(" [DATE] [E][ ]   "
                + "(from: 02 Jan 2024 0000 to: 02 Jan 2024 0000)\n", 8);
        assertEquals(result, cmdParse.parse("event   /from 2024-01-02 /to 2024-01-02",
                tasks, cmdColor));
    }
}
