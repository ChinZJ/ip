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
        String result = MsgGen.bye();
        assertEquals(result, cmdParse.parse("bye", tasks));

        // Invalid input.
        result = MsgGen.unknownCmd();
        assertEquals(result, cmdParse.parse("bye ", tasks));
    }

    @Test
    public void listTest() {
        // Tasks in list are not rigorously tested.
        // They should be tested in their respective classes.
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();

        // Empty list.
        String result = MsgGen.list("");
        assertEquals(result, cmdParse.parse("list", tasks));

        // List with item.
        ToDo task = new ToDo("unmark", false);
        tasks.add(task);
        result = MsgGen.list("\t 1. [T][ ] unmark\n");
        assertEquals(result, cmdParse.parse("list", tasks));

        // List with marked item.
        tasks.remove(0);
        task = new ToDo("mark", true);
        tasks.add(task);
        result = MsgGen.list("\t 1. [T][X] mark\n");
        assertEquals(result, cmdParse.parse("list", tasks));

        // Invalid input.
        result = MsgGen.unknownCmd();
        assertEquals(result, cmdParse.parse("list ", tasks));
    }

    @Test
    public void markTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();
        ToDo task = new ToDo("unmark", false);
        tasks.add(task);

        // Correct input.
        String result = MsgGen.mark(tasks.mark(0));
        tasks.unmark(0);
        assertEquals(result, cmdParse.parse("mark 1", tasks));

        // Marking the same object again.
        assertEquals(result, cmdParse.parse("mark 1", tasks));

        // Exceed size.
        result = MsgGen.unknownSyntax(CmdConst.CMD_MARK.getString(),
                CmdConst.MSG_INVALID_CMD_MARK.getString()
                        + String.valueOf(tasks.size()));
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
        String result = MsgGen.unmark(tasks.unmark(0));
        tasks.mark(0);
        assertEquals(result, cmdParse.parse("unmark 1", tasks));

        // Marking the same object again.
        assertEquals(result, cmdParse.parse("unmark 1", tasks));

        // Exceed size.
        result = MsgGen.unknownSyntax(CmdConst.CMD_UNMARK.getString(),
                CmdConst.MSG_INVALID_CMD_MARK.getString()
                        + String.valueOf(tasks.size()));
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
        String result = MsgGen.delete(tasks.remove(0), tasks.size());
        tasks.add(task);
        assertEquals(result, cmdParse.parse("delete 1", tasks));

        // Exceed size.
        tasks.add(task);
        result = MsgGen.unknownSyntax(CmdConst.CMD_DELETE.getString(),
                CmdConst.MSG_INVALID_CMD_MARK.getString()
                        + String.valueOf(tasks.size()));
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
        ToDo task = new ToDo(" ", false);

        // Valid input.
        String result = MsgGen.add(tasks.add(task), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("todo  ", tasks));

        // No descriptor.
        result = MsgGen.unknownSyntax(CmdConst.CMD_TODO.getString(),
                CmdConst.MSG_INVALID_CMD_TODO.getString());
        assertEquals(result, cmdParse.parse("todo", tasks));
    }

    @Test
    public void deadlineTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();
        Deadlines task = new Deadlines(" ", " ");

        // Standard input.
        String result = MsgGen.add(tasks.add(task), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("deadline   /by  ", tasks));

        // Multiple /by commands
        // Nested deadline commands.
        tasks.remove(0);
        task = new Deadlines("deadline", "deadline /by abc");
        result = MsgGen.add(tasks.add(task), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("deadline deadline /by deadline /by abc", tasks));

        // deadline with date.
        // deadline with alternate time formats are not tested
        // because that is the functionality of DateParser object.
        tasks.remove(0);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime time = LocalDateTime.parse("2024-01-02 0000", format);
        DeadlineDate taskDate = new DeadlineDate(" ", time);
        result = MsgGen.add(tasks.add(taskDate), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("deadline   /by 2024-01-02 0000", tasks));

        // Invalid input.
        result = MsgGen.unknownSyntax(CmdConst.CMD_DEADLINE.getString(),
                CmdConst.MSG_INVALID_CMD_DEADLINE.getString()
                        + CmdConst.MSG_INVALID_CMD_DATE.getString());
        assertEquals(result, cmdParse.parse("deadline", tasks));
    }

    @Test
    public void eventTest() {
        CommandParser cmdParse = new CommandParser();
        TaskList tasks = new TaskList();
        Events task = new Events(" ", " ", " ");

        // Standard input.
        String result = MsgGen.add(tasks.add(task), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("event   /from   /to  ", tasks));

        // Nested event commands.
        tasks.remove(0);
        task = new Events(" ", "event ", "/from asd /to asd ");
        result = MsgGen.add(tasks.add(task), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("event   /from event  /to /from asd /to asd ", tasks));

        // Multiple /from /to commands.
        tasks.remove(0);
        task = new Events("a", "b /from c", "d /to e");
        result = MsgGen.add(tasks.add(task), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("event a /from b /from c /to d /to e", tasks));

        tasks.remove(0);
        task = new Events("a", "b", "c /from d /to e");
        result = MsgGen.add(tasks.add(task), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("event a /from b /to c /from d /to e", tasks));

        tasks.remove(0);
        task = new Events("a", "b", "c /to d /from e");
        result = MsgGen.add(tasks.add(task), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("event a /from b /to c /to d /from e", tasks));

        tasks.remove(0);
        task = new Events("a /to b", "c", "d /from e");
        result = MsgGen.add(tasks.add(task), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("event a /to b /from c /to d /from e", tasks));

        tasks.remove(0);
        task = new Events("a /to b", "c /from d", "e");
        result = MsgGen.add(tasks.add(task), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("event a /to b /from c /from d /to e", tasks));

        // Doubles as invalid input.
        result = MsgGen.unknownSyntax(CmdConst.CMD_EVENT.getString(),
                CmdConst.MSG_INVALID_CMD_EVENT.getString()
                        + CmdConst.MSG_INVALID_CMD_EVENT_DATE.getString()
                        + CmdConst.MSG_INVALID_CMD_DATE.getString());
        assertEquals(result, cmdParse.parse("event a /to b /to c /from d /from e", tasks));

        // event with invalid start and end date
        assertEquals(result, cmdParse.parse("event a /from 02 Jan 2024 0000 /to 02 Jan 2022 0000", tasks));

        // event with date.
        // Doubles to check that same date works, but not before.
        tasks.remove(0);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime time = LocalDateTime.parse("2024-01-02 0000", format);
        EventDate taskDate = new EventDate(" ", time, time);
        result = MsgGen.add(tasks.add(taskDate), tasks.size());
        tasks.remove(0);
        assertEquals(result, cmdParse.parse("event   /from 2024-01-02 /to 2024-01-02", tasks));
    }

}
