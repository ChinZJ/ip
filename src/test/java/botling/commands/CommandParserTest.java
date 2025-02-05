package botling.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import botling.TaskList;
import botling.messagegenerator.MsgGen;
import botling.tasks.Deadlines;
import botling.tasks.Events;
import botling.tasks.ToDo;

public class CommandParserTest {

    @Test
    public void byeTest() {
        CommandParser cmdParse = new CommandParser();
        CommandColor cmdColor = new CommandColor();
        TaskList tasks = new TaskList();

        // Valid input.
        String result = "Bye. Hope to see you again soon!";
        assertEquals(result, cmdParse.parse("bye", tasks, cmdColor));

        // Invalid input.
        tasks.hasOpen();
        result = "OOPS!!! This command does not exist (yet).\n "
            + "Type 'help' for a list of commandtypes and their syntax!";
        assertEquals(result, cmdParse.parse("bye ", tasks, cmdColor));
    }

    @Test
    public void listTest() {
        // Tasks in list are not rigorously tested.
        // They should be tested in their respective classes.
        CommandParser cmdParse = new CommandParser();
        CommandColor cmdColor = new CommandColor();
        TaskList tasks = new TaskList();

        // Empty list.
        String result = "There are currently no tasks!";
        assertEquals(result, cmdParse.parse("list", tasks, cmdColor));

        // List with item.
        ToDo task = new ToDo("unmark", false, LocalDateTime.now());
        tasks.add(task);
        result = "Here are the tasks in your list:\n"
                + " 1. [T][ ] unmark";
        assertEquals(result, cmdParse.parse("list", tasks, cmdColor));

        // List with marked item.
        tasks.remove(0);
        task = new ToDo("mark", true, LocalDateTime.now());
        tasks.add(task);
        result = "Here are the tasks in your list:\n"
                + " 1. [T][X] mark";
        assertEquals(result, cmdParse.parse("list", tasks, cmdColor));

        // Invalid input.
        result = MsgGen.unknownCmd(cmdColor);
        assertEquals(result, cmdParse.parse("list ", tasks, cmdColor));
    }

    @Test
    public void findTest() {
        CommandParser cmdParse = new CommandParser();
        CommandColor cmdColor = new CommandColor();
        TaskList tasks = new TaskList();

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

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);

        // Spaces.
        String result = "Here are the matching tasks in your list:\n"
                + " 1. [T][ ]  \n"
                + " 2. [D][ ]  b (by: cc)\n"
                + " 3. [E][ ]  B (from: CC to: DD)";
        assertEquals(result, cmdParse.parse("find  ", tasks, cmdColor));

        // Upper and lower case.
        result = "Here are the matching tasks in your list:\n"
                + " 2. [D][ ]  b (by: cc)\n"
                + " 3. [E][ ]  B (from: CC to: DD)";
        assertEquals(result, cmdParse.parse("find  b", tasks, cmdColor));
        assertEquals(result, cmdParse.parse("find  B", tasks, cmdColor));

        // Character in other fields.
        result = "There are currently no matching tasks!";
        assertEquals(result, cmdParse.parse("find c", tasks, cmdColor));

        // Find special characters
        result = "Here are the matching tasks in your list:\n"
                + " 4. [DATE] [D][ ] )(*&^%$#@! (by: 02 Jan 2024 0000)\n"
                + " 5. [DATE] [E][ ] !@#$%^&*( (from: 02 Jan 2024 0000 to: 02 Jan 2024 0000)";
        assertEquals(result, cmdParse.parse("find !", tasks, cmdColor));

        // Find invalid input
        result = "OOPS!!! This command does not exist (yet).\n "
                + "Type 'help' for a list of commandtypes and their syntax!";
        assertEquals(result, cmdParse.parse("find", tasks, cmdColor));
    }

    @Test
    public void markTest() {
        CommandParser cmdParse = new CommandParser();
        CommandColor cmdColor = new CommandColor();
        TaskList tasks = new TaskList();
        ToDo task = new ToDo("unmark", false, LocalDateTime.now());
        tasks.add(task);

        // Correct input.
        String result = "Nice! I've marked this task as done:\n"
                + " [T][X] unmark";
        tasks.unmark(0);
        assertEquals(result, cmdParse.parse("mark 1", tasks, cmdColor));
        // Marking the same object again.
        assertEquals(result, cmdParse.parse("mark 1", tasks, cmdColor));

        // Exceed size.
        result = "OOPS!!! The format of mark should be mark <X>"
                + ", where X is a positive integer <= 1";
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
        CommandParser cmdParse = new CommandParser();
        CommandColor cmdColor = new CommandColor();
        TaskList tasks = new TaskList();
        ToDo task = new ToDo("unmark", true, LocalDateTime.now());
        tasks.add(task);

        // Correct input.
        String result = "Ok, I've marked this task as not done yet:\n"
                + " [T][ ] unmark";
        tasks.mark(0);
        assertEquals(result, cmdParse.parse("unmark 1", tasks, cmdColor));

        // Marking the same object again.
        assertEquals(result, cmdParse.parse("unmark 1", tasks, cmdColor));

        // Exceed size.
        result = "OOPS!!! The format of unmark should be unmark <X>"
                + ", where X is a positive integer <= 1";
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
        CommandParser cmdParse = new CommandParser();
        CommandColor cmdColor = new CommandColor();
        TaskList tasks = new TaskList();
        ToDo task = new ToDo("delete", false, LocalDateTime.now());
        tasks.add(task);

        // Correct input.
        String result = "Noted. I've removed this task: \n"
                + " [T][ ] delete\n"
                + "Now you have 0 tasks in the list.";
        assertEquals(result, cmdParse.parse("delete 1", tasks, cmdColor));

        // Exceed size.
        tasks.add(task);
        result = "OOPS!!! The format of delete should be delete <X>"
                + ", where X is a positive integer <= 1";
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
        CommandParser cmdParse = new CommandParser();
        CommandColor cmdColor = new CommandColor();
        TaskList tasks = new TaskList();
        // ToDo task = new ToDo(" ", false);

        // Valid input.
        String result = "Got it. I've added this task: \n"
                + " [T][ ]  \n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("todo  ", tasks, cmdColor));

        // No descriptor.
        result = "OOPS!!! The format of todo should be todo <name>.";
        assertEquals(result, cmdParse.parse("todo", tasks, cmdColor));
    }

    @Test
    public void deadlineTest() {
        CommandParser cmdParse = new CommandParser();
        CommandColor cmdColor = new CommandColor();
        TaskList tasks = new TaskList();
        // Deadlines task = new Deadlines(" ", " ");

        // Standard input.
        String result = "Got it. I've added this task: \n"
                + " [D][ ]   (by:  )\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("deadline   /by  ", tasks, cmdColor));

        // Multiple /by commandtypes
        // Nested deadline commandtypes.
        tasks.remove(0);
        // task = new Deadlines("deadline", "deadline /by abc");
        result = "Got it. I've added this task: \n"
                + " [D][ ] deadline (by: deadline /by abc)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("deadline deadline /by deadline /by abc",
                tasks, cmdColor));

        // deadline with date.
        // deadline with alternate time formats are not tested
        // because that is the functionality of DateParser object.
        tasks.remove(0);
        // DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        // LocalDateTime time = LocalDateTime.parse("2024-01-02 0000", format);
        // DeadlineDate taskDate = new DeadlineDate(" ", time);
        result = "Got it. I've added this task: \n"
                + " [DATE] [D][ ]   (by: 02 Jan 2024 0000)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("deadline   /by 2024-01-02 0000", tasks, cmdColor));

        // Invalid input.
        result = "OOPS!!! The format of deadline should be deadline <name> /by <deadline>.\n"
                + "Date: 'yy(yy)-MM-dd HHmm', 'dd/MM/yy(yy)' or 'dd MMM yy(yy)', "
                + "optionally with HHmm in 24hour format.";
        assertEquals(result, cmdParse.parse("deadline", tasks, cmdColor));
    }

    @Test
    public void eventTest() {
        CommandParser cmdParse = new CommandParser();
        CommandColor cmdColor = new CommandColor();
        TaskList tasks = new TaskList();
        // Events task = new Events(" ", " ", " ");

        // Standard input.
        String result = "Got it. I've added this task: \n"
                + " [E][ ]   (from:   to:  )\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("event   /from   /to  ", tasks, cmdColor));

        // Nested event commandtypes.
        tasks.remove(0);
        // task = new Events(" ", "event ", "/from asd /to asd ");
        result = "Got it. I've added this task: \n"
                + " [E][ ]   (from: event  to: /from asd /to asd )\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("event   /from event  /to /from asd /to asd ",
                tasks, cmdColor));

        // Multiple /from /to commandtypes.
        tasks.remove(0);
        // task = new Events("a", "b /from c", "d /to e");
        result = "Got it. I've added this task: \n"
                + " [E][ ] a (from: b /from c to: d /to e)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("event a /from b /from c /to d /to e",
                tasks, cmdColor));

        tasks.remove(0);
        // task = new Events("a", "b", "c /from d /to e");
        result = "Got it. I've added this task: \n"
                + " [E][ ] a (from: b to: c /from d /to e)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("event a /from b /to c /from d /to e",
                tasks, cmdColor));

        tasks.remove(0);
        // task = new Events("a", "b", "c /to d /from e");
        result = "Got it. I've added this task: \n"
                + " [E][ ] a (from: b to: c /to d /from e)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("event a /from b /to c /to d /from e",
                tasks, cmdColor));

        tasks.remove(0);
        // task = new Events("a /to b", "c", "d /from e");
        result = "Got it. I've added this task: \n"
                + " [E][ ] a /to b (from: c to: d /from e)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("event a /to b /from c /to d /from e",
                tasks, cmdColor));

        tasks.remove(0);
        // task = new Events("a /to b", "c /from d", "e");
        result = "Got it. I've added this task: \n"
                + " [E][ ] a /to b (from: c /from d to: e)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("event a /to b /from c /from d /to e",
                tasks, cmdColor));

        // Doubles as invalid input.
        result = "OOPS!!! The format of event should be event <name> /from <start> /to <end>.\n"
                + " <start> should be before or equal to <end> if dates are inputs.\n"
                + "Date: 'yy(yy)-MM-dd HHmm', 'dd/MM/yy(yy)' or 'dd MMM yy(yy)', "
                + "optionally with HHmm in 24hour format.";
        assertEquals(result, cmdParse.parse("event a /to b /to c /from d /from e",
                tasks, cmdColor));

        // event with invalid start and end date
        assertEquals(result, cmdParse.parse(
                "event a /from 02 Jan 2024 0000 /to 02 Jan 2022 0000",
                tasks, cmdColor));

        // event with date.
        // Doubles to check that same date works, but not before.
        tasks.remove(0);
        // DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        // LocalDateTime time = LocalDateTime.parse("2024-01-02 0000", format);
        // EventDate taskDate = new EventDate(" ", time, time);
        result = "Got it. I've added this task: \n"
                + " [DATE] [E][ ]   (from: 02 Jan 2024 0000 to: 02 Jan 2024 0000)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(result, cmdParse.parse("event   /from 2024-01-02 /to 2024-01-02",
                tasks, cmdColor));
    }

}
