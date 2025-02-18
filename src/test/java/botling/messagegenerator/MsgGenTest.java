package botling.messagegenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import botling.TaskList;
import botling.commands.CommandColor;
import botling.gui.ColorNames;
import botling.tasks.Task;
import botling.tasks.ToDo;

public class MsgGenTest {
    private CommandColor cmdColor;
    private TaskList tasks;

    /**
     * Resets all private variables.
     */
    private void resetVariables() {
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

    private void setUpTasks() {
        ToDo markTodo = new ToDo("markTodo");
        markTodo.updateTask(true);
        ToDo unmarkTodo = new ToDo("unmarkTodo");
        addTasks(markTodo, unmarkTodo);
    }

    @Test
    public void wrapTest() {
        resetVariables();
        String output = MsgGen.wrap("wrapTest", cmdColor);

        assertEquals("wrapTest", output);
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("wrapTest", cmdColor.getMessages()[0]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
    }

    @Test
    public void greetTest() {
        resetVariables();
        String output = MsgGen.greet("greetTest", cmdColor);

        assertEquals("greetTest\nHey! I'm Botling!\nWhat can I do for you?", output);
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("greetTest\nHey! I'm Botling!\nWhat can I do for you?",
                cmdColor.getMessages()[0]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
    }

    @Test
    public void byeTest() {
        resetVariables();
        String output = MsgGen.bye(cmdColor);

        assertEquals("Shell-be seeing you!", output);
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("Shell-be seeing you!",
                cmdColor.getMessages()[0]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
    }

    @Test
    public void listTestEmpty() {
        resetVariables();

        String[] message = tasks.list();
        String output = MsgGen.list(message, cmdColor);
        assertEquals("Oceans clean, I'm free!", output);
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("Oceans clean, I'm free!",
                cmdColor.getMessages()[0]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
    }

    @Test
    public void findTestAddTask() {
        resetVariables();

        setUpTasks();

        String[] message = tasks.list();
        String output = MsgGen.list(message, cmdColor);
        assertEquals("Here's whats sinking:\n 1. [T][X] markTodo\n 2. [T][ ] unmarkTodo",
                output);
        assertEquals(3, cmdColor.getMessages().length);
        assertEquals("Here's whats sinking:\n", cmdColor.getMessages()[0]);
        assertEquals(" 1. [T][X] markTodo\n", cmdColor.getMessages()[1]);
        assertEquals(" 2. [T][ ] unmarkTodo", cmdColor.getMessages()[2]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
        assertEquals(ColorNames.COLOR_GREEN.getIndex(), cmdColor.getLines()[1]);
        assertEquals(ColorNames.COLOR_RED.getIndex(), cmdColor.getLines()[2]);
    }

    @Test
    public void findTestWithTaskSuccess() {
        resetVariables();

        setUpTasks();

        String[] message = tasks.find("to");
        String output = MsgGen.find(message, cmdColor);
        assertEquals("I chewed on some, but here's the remnants:\n"
                        + " 1. [T][X] markTodo\n 2. [T][ ] unmarkTodo",
                output);
        assertEquals(3, cmdColor.getMessages().length);
        assertEquals("I chewed on some, but here's the remnants:\n", cmdColor.getMessages()[0]);
        assertEquals(" 1. [T][X] markTodo\n", cmdColor.getMessages()[1]);
        assertEquals(" 2. [T][ ] unmarkTodo", cmdColor.getMessages()[2]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
        assertEquals(ColorNames.COLOR_GREEN.getIndex(), cmdColor.getLines()[1]);
        assertEquals(ColorNames.COLOR_RED.getIndex(), cmdColor.getLines()[2]);

        cmdColor.reset();
        message = tasks.find("un");
        output = MsgGen.find(message, cmdColor);
        assertEquals("I chewed on some, but here's the remnants:\n 2. [T][ ] unmarkTodo",
                output);
        assertEquals(2, cmdColor.getMessages().length);
        assertEquals("I chewed on some, but here's the remnants:\n", cmdColor.getMessages()[0]);
        assertEquals(" 2. [T][ ] unmarkTodo", cmdColor.getMessages()[1]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
        assertEquals(ColorNames.COLOR_RED.getIndex(), cmdColor.getLines()[1]);
    }

    @Test
    public void findTestWithTaskFail() {
        resetVariables();

        setUpTasks();

        String[] message = tasks.find("sss");
        String output = MsgGen.find(message, cmdColor);
        assertEquals("Don't see any, check the landfill!",
                output);
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("Don't see any, check the landfill!", cmdColor.getMessages()[0]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
    }

    @Test
    public void markTest() {
        resetVariables();
        String output = MsgGen.mark("markTest", cmdColor);

        assertEquals("Nice! I've swallowed this task:\nmarkTest", output);
        assertEquals(2, cmdColor.getMessages().length);
        assertEquals("Nice! I've swallowed this task:\n", cmdColor.getMessages()[0]);
        assertEquals("markTest", cmdColor.getMessages()[1]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
        assertEquals(ColorNames.COLOR_GREEN.getIndex(), cmdColor.getLines()[1]);
    }

    @Test
    public void unmarkTest() {
        resetVariables();
        String output = MsgGen.unmark("unmarkTest", cmdColor);

        assertEquals("Yuck, I've spat out this task:\nunmarkTest", output);
        assertEquals(2, cmdColor.getMessages().length);
        assertEquals("Yuck, I've spat out this task:\n", cmdColor.getMessages()[0]);
        assertEquals("unmarkTest", cmdColor.getMessages()[1]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
        assertEquals(ColorNames.COLOR_RED.getIndex(), cmdColor.getLines()[1]);
    }

    @Test
    public void addTest() {
        resetVariables();
        String output = MsgGen.add("addTest", 500, cmdColor);

        assertEquals("You threw this into the ocean:\naddTest\n"
                + "Now you have 500 tasks polluting my waters!", output);
        assertEquals(4, cmdColor.getMessages().length);
        assertEquals("You threw this into the ocean:\n", cmdColor.getMessages()[0]);
        assertEquals("addTest", cmdColor.getMessages()[1]);
        assertEquals("\nNow you have 500", cmdColor.getMessages()[2]);
        assertEquals(" tasks polluting my waters!", cmdColor.getMessages()[3]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
        assertEquals(ColorNames.COLOR_RED.getIndex(), cmdColor.getLines()[1]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[2]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[3]);
    }

    @Test
    public void deleteTest() {
        resetVariables();
        String output = MsgGen.delete("deleteTest", 500, cmdColor);

        assertEquals("This task has degraded into nothingness:\ndeleteTest\n"
                + "Now you have 500 tasks polluting my waters!", output);
        assertEquals(4, cmdColor.getMessages().length);
        assertEquals("This task has degraded into nothingness:\n", cmdColor.getMessages()[0]);
        assertEquals("deleteTest", cmdColor.getMessages()[1]);
        assertEquals("\nNow you have 500", cmdColor.getMessages()[2]);
        assertEquals(" tasks polluting my waters!", cmdColor.getMessages()[3]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
        assertEquals(ColorNames.COLOR_STRIKETHROUGH.getIndex(), cmdColor.getLines()[1]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[2]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[3]);
    }

    @Test
    public void unknownTest() {
        resetVariables();
        String output = MsgGen.unknownCmd(cmdColor);

        assertEquals("Yikes!!! This command is still up for discussion.\n"
                + "Type 'help' for a list of commands and their syntax!", output);
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("Yikes!!! This command is still up for discussion.\n"
                + "Type 'help' for a list of commands and their syntax!",
                cmdColor.getMessages()[0]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);

        cmdColor.reset();
        output = MsgGen.unknownSyntax("cmd", "syntax", cmdColor);

        assertEquals("Yikes!!! The format of cmd should be cmdsyntax", output);
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("Yikes!!! The format of cmd should be cmdsyntax",
                cmdColor.getMessages()[0]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);

        cmdColor.reset();
        output = MsgGen.unknownCorrupt(cmdColor);

        assertEquals("Please enter 'y' for yes and 'n' for no.", output);
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("Please enter 'y' for yes and 'n' for no.", cmdColor.getMessages()[0]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);

        cmdColor.reset();
        output = MsgGen.unknownDateTime(cmdColor);

        assertEquals("That date time looks awkwardly wrong...\n"
                + "I think this is suitable for peer help!", output);
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("That date time looks awkwardly wrong...\n"
                + "I think this is suitable for peer help!", cmdColor.getMessages()[0]);
        assertEquals(ColorNames.COLOR_BLACK.getIndex(), cmdColor.getLines()[0]);
    }
}
