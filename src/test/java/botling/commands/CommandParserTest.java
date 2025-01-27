package botling.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import botling.messagegenerator.MsgGen;
import botling.TaskList;
import botling.tasks.DeadlineDate;
import botling.tasks.Deadlines;
import botling.tasks.EventDate;
import botling.tasks.Events;
import botling.tasks.ToDo;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class CommandParserTest {

    @Test
    public void byeTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();

        // Valid input.
        String result = "Bye. Hope to see you again soon!\n";
        assertEquals(result, cmdParse.parse("bye", tasks));

        // Invalid input.
        tasks.hasOpen();
        result = "OOPS!!! This command does not exist(yet).\n";
        assertEquals(result, cmdParse.parse("bye ", tasks));
    }

    @Test
    public void listTest() {
        // Tasks in list are not rigorously tested.
        // They should be tested in their respective classes.
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();

        // Empty list.
        String result = "There are currently no tasks!\n";
        assertEquals(result, cmdParse.parse("list", tasks));

        // List with item.
        ToDo task = new ToDo("unmark", false);
        tasks.add(task);
        result = "Here are the tasks in your list:\n"
                + " 1. [T][ ] unmark\n";
        assertEquals(result, cmdParse.parse("list", tasks));

        // List with marked item.
        tasks.remove(0);
        task = new ToDo("mark", true);
        tasks.add(task);
        result = "Here are the tasks in your list:\n"
                + " 1. [T][X] mark\n";
        assertEquals(result, cmdParse.parse("list", tasks));

        // Invalid input.
        result = MsgGen.unknownCmd();
        assertEquals(result, cmdParse.parse("list ", tasks));
    }

    @Test
    public void findTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();

        // Find upper and lower case
        // Simultaneously test for special characters and spaces
        ToDo task1 = new ToDo(" ");
        Deadlines task2 = new Deadlines(" b", "cc");
        Events task3 = new Events(" B", "CC", "DD");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime time = LocalDateTime.parse("2024-01-02 0000", format);
        DeadlineDate task4 = new DeadlineDate(")(*&^%$#@!", time);
        EventDate task5 = new EventDate("!@#$%^&*(", time, time);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);

        // Spaces.
        String result = "Here are the matching tasks in your list:\n"
                + " 1. [T][ ]  \n"
                + " 2. [D][ ]  b (by: cc)\n"
                + " 3. [E][ ]  B (from: CC to: DD)\n";
        assertEquals(result, cmdParse.parse("find  ", tasks));

        // Upper and lower case.
        result = "Here are the matching tasks in your list:\n"
                + " 2. [D][ ]  b (by: cc)\n"
                + " 3. [E][ ]  B (from: CC to: DD)\n";
        assertEquals(result, cmdParse.parse("find  b", tasks));
        assertEquals(result, cmdParse.parse("find  B", tasks));

        // Character in other fields.
        result = "There are currently no matching tasks!\n";
        assertEquals(result, cmdParse.parse("find c", tasks));

        // Find special characters
        result = "Here are the matching tasks in your list:\n"
                + " 4. [D][ ] )(*&^%$#@! (by: 02 Jan 2024 0000) (date)\n"
                + " 5. [E][ ] !@#$%^&*( (from: 02 Jan 2024 0000 to: 02 Jan 2024 0000) (date)\n";
        assertEquals(result, cmdParse.parse("find !", tasks));

        // Find invalid input
        result = "OOPS!!! This command does not exist(yet).\n";
        assertEquals(result, cmdParse.parse("find", tasks));
    }

    @Test
    public void markTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();
        ToDo task = new ToDo("unmark", false);
        tasks.add(task);

        // Correct input.
        String result = "Nice! I've marked this task as done:\n"
                + " [T][X] unmark\n";
        tasks.unmark(0);
        assertEquals(result, cmdParse.parse("mark 1", tasks));

        // Marking the same object again.
        assertEquals(result, cmdParse.parse("mark 1", tasks));

        // Exceed size.
        result = "OOPS!!! The format of mark should be mark <X>, where X is a positive integer <= 1\n";
        assertEquals(result, cmdParse.parse("mark 9", tasks));

        // Float.
        assertEquals(result, cmdParse.parse("mark 92.6", tasks));

        // Long.
        assertEquals(result, cmdParse.parse("mark 5147483647", tasks));

        // Negative integer.
        assertEquals(result, cmdParse.parse("mark -900", tasks));
    }

    @Test
    public void unmarkTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();
        ToDo task = new ToDo("unmark", true);
        tasks.add(task);

        // Correct input.
        String result = "Ok, I've marked this task as not done yet:\n"
                + " [T][ ] unmark\n";
        tasks.mark(0);
        assertEquals(result, cmdParse.parse("unmark 1", tasks));

        // Marking the same object again.
        assertEquals(result, cmdParse.parse("unmark 1", tasks));

        // Exceed size.
        result = "OOPS!!! The format of unmark should be unmark <X>, where X is a positive integer <= 1\n";
        assertEquals(result, cmdParse.parse("unmark 9", tasks));

        // Float.
        assertEquals(result, cmdParse.parse("unmark 92.6", tasks));

        // Long.
        assertEquals(result, cmdParse.parse("unmark 5147483647", tasks));

        // Negative integer.
        assertEquals(result, cmdParse.parse("unmark -900", tasks));
    }

    @Test
    public void deleteTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();
        ToDo task = new ToDo("delete", false);
        tasks.add(task);

        // Correct input.
        String result = "Noted. I've removed this task: \n"
                + " [T][ ] delete\n"
                + "Now you have 0 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("delete 1", tasks));

        // Exceed size.
        tasks.add(task);
        result = "OOPS!!! The format of delete should be delete <X>, where X is a positive integer <= 1\n";
        assertEquals(result, cmdParse.parse("delete 9", tasks));

        // Float.
        assertEquals(result, cmdParse.parse("delete 92.6", tasks));

        // Long.
        assertEquals(result, cmdParse.parse("delete 5147483647", tasks));

        // Negative integer.
        assertEquals(result, cmdParse.parse("delete -900", tasks));
    }

    @Test
    public void todoTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();
        // ToDo task = new ToDo(" ", false);

        // Valid input.
        String result = "Got it. I've added this task: \n"
                + " [T][ ]  \n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("todo  ", tasks));

        // No descriptor.
        result = "OOPS!!! The format of todo should be todo <name>.\n";
        assertEquals(result, cmdParse.parse("todo", tasks));
    }

    @Test
    public void deadlineTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();
        // Deadlines task = new Deadlines(" ", " ");

        // Standard input.
        String result = "Got it. I've added this task: \n"
                + " [D][ ]   (by:  )\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("deadline   /by  ", tasks));

        // Multiple /by commands
        // Nested deadline commands.
        tasks.remove(0);
        // task = new Deadlines("deadline", "deadline /by abc");
        result = "Got it. I've added this task: \n"
                + " [D][ ] deadline (by: deadline /by abc)\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("deadline deadline /by deadline /by abc", tasks));

        // deadline with date.
        // deadline with alternate time formats are not tested
        // because that is the functionality of DateParser object.
        tasks.remove(0);
        // DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        // LocalDateTime time = LocalDateTime.parse("2024-01-02 0000", format);
        // DeadlineDate taskDate = new DeadlineDate(" ", time);
        result = "Got it. I've added this task: \n"
                + " [D][ ]   (by: 02 Jan 2024 0000) (date)\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("deadline   /by 2024-01-02 0000", tasks));

        // Invalid input.
        result = "OOPS!!! The format of deadline should be deadline <name> /by <deadline>.\n"
                + "Date: 'yyyy-MM-dd HHmm', 'yyyy-MM-dd' or 'dd MMM yyyy HHmm'\n";
        assertEquals(result, cmdParse.parse("deadline", tasks));
    }

    @Test
    public void eventTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();
        // Events task = new Events(" ", " ", " ");

        // Standard input.
        String result = "Got it. I've added this task: \n"
                + " [E][ ]   (from:   to:  )\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("event   /from   /to  ", tasks));

        // Nested event commands.
        tasks.remove(0);
        // task = new Events(" ", "event ", "/from asd /to asd ");
        result = "Got it. I've added this task: \n"
                + " [E][ ]   (from: event  to: /from asd /to asd )\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("event   /from event  /to /from asd /to asd ", tasks));

        // Multiple /from /to commands.
        tasks.remove(0);
        // task = new Events("a", "b /from c", "d /to e");
        result = "Got it. I've added this task: \n"
                + " [E][ ] a (from: b /from c to: d /to e)\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("event a /from b /from c /to d /to e", tasks));

        tasks.remove(0);
        // task = new Events("a", "b", "c /from d /to e");
        result = "Got it. I've added this task: \n"
                + " [E][ ] a (from: b to: c /from d /to e)\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("event a /from b /to c /from d /to e", tasks));

        tasks.remove(0);
        // task = new Events("a", "b", "c /to d /from e");
        result = "Got it. I've added this task: \n"
                + " [E][ ] a (from: b to: c /to d /from e)\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("event a /from b /to c /to d /from e", tasks));

        tasks.remove(0);
        // task = new Events("a /to b", "c", "d /from e");
        result = "Got it. I've added this task: \n"
                + " [E][ ] a /to b (from: c to: d /from e)\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("event a /to b /from c /to d /from e", tasks));

        tasks.remove(0);
        // task = new Events("a /to b", "c /from d", "e");
        result = "Got it. I've added this task: \n"
                + " [E][ ] a /to b (from: c /from d to: e)\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("event a /to b /from c /from d /to e", tasks));

        // Doubles as invalid input.
        result = "OOPS!!! The format of event should be event <name> /from <start> /to <end>.\n"
                + " <start> should be before or equal to <end> if dates are inputs.\n"
                + "Date: 'yyyy-MM-dd HHmm', 'yyyy-MM-dd' or 'dd MMM yyyy HHmm'\n";
        assertEquals(result, cmdParse.parse("event a /to b /to c /from d /from e", tasks));

        // event with invalid start and end date
        assertEquals(result, cmdParse.parse("event a /from 02 Jan 2024 0000 /to 02 Jan 2022 0000", tasks));

        // event with date.
        // Doubles to check that same date works, but not before.
        tasks.remove(0);
        // DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        // LocalDateTime time = LocalDateTime.parse("2024-01-02 0000", format);
        // EventDate taskDate = new EventDate(" ", time, time);
        result = "Got it. I've added this task: \n"
                + " [E][ ]   (from: 02 Jan 2024 0000 to: 02 Jan 2024 0000) (date)\n"
                + "Now you have 1 tasks in the list.\n";
        assertEquals(result, cmdParse.parse("event   /from 2024-01-02 /to 2024-01-02", tasks));
    }

}
