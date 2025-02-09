package botling.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandColorTest {

    @Test
    public void allTest() {
        CommandColor cmdColor = new CommandColor();
        assertEquals(0, cmdColor.getMessages().length);
        assertEquals(0, cmdColor.getLines().length);

        cmdColor.setMessages(new String[]{"setMessage"});
        cmdColor.setLines(new Integer[]{1});
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("setMessage", cmdColor.getMessages()[0]);
        assertEquals(1, cmdColor.getLines().length);
        assertEquals(1, cmdColor.getLines()[0]);

        cmdColor.setAll(new String[]{"setAll"}, new Integer[]{25});
        assertEquals(1, cmdColor.getMessages().length);
        assertEquals("setAll", cmdColor.getMessages()[0]);
        assertEquals(1, cmdColor.getLines().length);
        assertEquals(25, cmdColor.getLines()[0]);

        cmdColor.reset();
        assertEquals(0, cmdColor.getMessages().length);
        assertEquals(0, cmdColor.getLines().length);
    }
}
