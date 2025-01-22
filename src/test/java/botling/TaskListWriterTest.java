package botling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import botling.TaskList;
import botling.tasks.Deadlines;
import botling.tasks.EventDate;
import botling.tasks.ToDo;
import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskListWriterTest {
    private final InputStream systemIn = System.in;

    private ByteArrayInputStream testIn;

    /**
     * Used to simulate user input for (y/n) during unexpected events.
     */
    private void provideInput(String input) {
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }

    /**
     * Used to overwrite the history.txt file for each test where applicable.
     */
    @BeforeEach
    private void createTempHistoryFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("./data/history.txt"))) {
            File dataFolder = new File("data");
            File historyFile = new File("./data/history.txt");
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }
            if (!historyFile.exists()) {
                historyFile.createNewFile();
            }

            /*
             * Test everything in the following order
             * ToDo mark
             * Deadline by unmark
             * Event from to mark (date) mark
             * */
            String fileHistory = "todo\n \ntrue\n"
                    + "deadline\ntonight!\n \nfalse\n"
                    + "event\n23 Jan 2025 0000\n24 Jan 2025 2359\n \ntrue";
            writer.write(fileHistory);
        }
    }

    @AfterEach
    public void restoreSystemInput() {
        System.setIn(systemIn);
        System.gc();
    }

    @Test
    public void filePresentTest() {
        String expectedMsg = "\t Attempting to retrieve history...\n"
                + "\t Data folder found!\n"
                + "\t History file found! Restoring data...\n";

        ToDo first = new ToDo(" ", true);
        Deadlines second = new Deadlines(" ", "tonight!");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime startTime = LocalDateTime.parse("2025-01-23 0000", format);
        LocalDateTime endTime = LocalDateTime.parse("2025-01-24 2359", format);
        EventDate third = new EventDate(" ", true, startTime, endTime);

        TaskList expected = new TaskList();
        expected.add(first);
        expected.add(second);
        expected.add(third);

        TaskListWriter tester = new TaskListWriter();
        TaskList testList = new TaskList();
        String actual = tester.restore(testList);

        assertEquals(expectedMsg, actual);
        assertEquals(3, testList.size());
        assertEquals(expected.fileString(), testList.fileString());
        assertEquals(expected.list(), testList.list());
    }

    @Test
    public void folderPresentTest() {
        File historyFile = new File("./data/history.txt");
        historyFile.delete();

        String expectedMsg = "\t Attempting to retrieve history...\n"
                + "\t Data folder found!\n"
                + "\t No history file found! Creating history file...\n";

        TaskListWriter tester = new TaskListWriter();
        TaskList testList = new TaskList();
        String actual = tester.restore(testList);

        assertEquals(expectedMsg, actual);
        assertEquals(0, testList.size());
        assertEquals("", testList.fileString());
        assertEquals("", testList.list());
    }

    @Test
    public void nonePresentTest() {
        File dataFolder = new File("data");
        File historyFile = new File("./data/history.txt");
        historyFile.delete();
        dataFolder.delete();

        String expectedMsg = "\t Attempting to retrieve history...\n"
                + "\t No data folder found! Creating data folder...\n"
                + "\t No history file found! Creating history file...\n";

        TaskListWriter tester = new TaskListWriter();
        TaskList testList = new TaskList();
        String actual = tester.restore(testList);

        assertEquals(expectedMsg, actual);
        assertEquals(0, testList.size());
        assertEquals("", testList.fileString());
        assertEquals("", testList.list());
    }

    @Test
    public void failedReadYesTest() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("./data/history.txt"))) {
            String fileHistory = "corrupted\nfile\n!!!!!!";
            writer.write(fileHistory);
        }

        String expectedMsg = "\t Attempting to retrieve history...\n"
                + "\t Data folder found!\n"
                + "\t History file found! Restoring data...\n";

        String input = "y";
        provideInput(input);

        TaskListWriter tester = new TaskListWriter();
        TaskList testList = new TaskList();
        String actual = tester.restore(testList);

        assertEquals(expectedMsg, actual);
        assertEquals(0, testList.size());
        assertEquals("", testList.fileString());
        assertEquals("", testList.list());


    }

    @Test
    public void failedReadNoTest() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("./data/history.txt"))) {
            String fileHistory = "corrupted\nfile\n!!!!!!";
            writer.write(fileHistory);
        }

        TaskListWriter tester = new TaskListWriter();
        TaskList testList = new TaskList();
        tester.restore(testList);

        try (BufferedReader reader = new BufferedReader(
                new FileReader("./data/history.txt"))) {
            String actualContent = reader.readLine() + "\n"
                    + reader.readLine() + "\n"
                    + reader.readLine();

            assertEquals("corrupted\nfile\n!!!!!!", actualContent);
        }
    }
}
