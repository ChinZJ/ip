package botling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import botling.tasks.Deadlines;
import botling.tasks.Events;
import botling.tasks.ToDo;

public class TaskListWriterTest {
    /**
     * Used to overwrite the history.txt file for each test where applicable.
     */
    @BeforeEach
    public void createTempHistoryFile() throws IOException {
        File dataFolder = new File("data");
        File historyFile = new File("./data/history.txt");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        if (!historyFile.exists()) {
            historyFile.createNewFile();
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("./data/history.txt"))) {
            /*
             * Test everything in the following order
             * ToDo mark
             * Deadline by unmark
             * Event from to (date) mark
             * */
            String fileHistory = "todo\n \ntrue\n05 Feb 2025 1116\n"
                    + "deadline\ntonight!\n \nfalse\n05 Feb 2025 1116\n"
                    + "event\n23 Jan 2025 0000\n24 Jan 2025 2359\n \ntrue\n05 Feb 2025 1116";
            writer.write(fileHistory);
        }
    }

    @Test
    public void filePresentTest() {
        String expectedMsg = "Attempting to retrieve history...\n"
                + "Data folder found!\n"
                + "History file found! Restoring data...\n";
        String expectedFileString = "todo\n \ntrue\n05 Feb 2025 1116\n"
                + "deadline\ntonight!\n \nfalse\n05 Feb 2025 1116\n"
                + "event\n23 Jan 2025 0000\n24 Jan 2025 2359\n \ntrue\n05 Feb 2025 1116";
        String expectedListString = " 1. [T][X]  \n"
                + " 2. [D][ ]   (by: tonight!)\n"
                + " 3. [DATE] [E][X]   (from: 23 Jan 2025 0000 to: 24 Jan 2025 2359)";

        LocalDateTime setTime = LocalDateTime.parse("05 Feb 2025 1116",
                DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));

        ToDo first = new ToDo(" ", true, LocalDateTime.parse("05 Feb 2025 1116",
                DateTimeFormatter.ofPattern("dd MMM yyyy HHmm")));
        Deadlines second = new Deadlines(" ", false, "tonight!",
                Optional.empty(), setTime);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime startTime = LocalDateTime.parse("2025-01-23 0000", format);
        LocalDateTime endTime = LocalDateTime.parse("2025-01-24 2359", format);
        Events third = new Events(" ", true, "2025-01-23 0000", "2025-01-24 2359",
                Optional.of(startTime), Optional.of(endTime), setTime);

        TaskList expected = new TaskList();
        expected.add(first);
        expected.add(second);
        expected.add(third);

        TaskListWriter tester = new TaskListWriter();
        TaskList testList = new TaskList();
        String actual = tester.restore(testList);

        assertEquals(expectedMsg, actual);
        assertEquals(3, testList.size());
        assertEquals(expectedFileString, testList.fileString());
        assertEquals(expected.fileString(), testList.fileString());
        assertEquals(expectedListString, String.join("", testList.list()));
    }

    @Test
    public void folderPresentTest() {
        File historyFile = new File("./data/history.txt");
        historyFile.delete();

        String expectedMsg = "Attempting to retrieve history...\n"
                + "Data folder found!\n"
                + "No history file found! Creating history file...\n";

        TaskListWriter tester = new TaskListWriter();
        TaskList testList = new TaskList();
        String actual = tester.restore(testList);

        assertEquals(expectedMsg, actual);
        assertEquals(0, testList.size());
        assertEquals("", testList.fileString());
        assertEquals(0, testList.list().length);
    }

    @Test
    public void nonePresentTest() {
        File dataFolder = new File("data");
        File historyFile = new File("./data/history.txt");
        historyFile.delete();
        dataFolder.delete();

        String expectedMsg = "Attempting to retrieve history...\n"
                + "No data folder found! Creating data folder...\n"
                + "No history file found! Creating history file...\n";

        TaskListWriter tester = new TaskListWriter();
        TaskList testList = new TaskList();
        String actual = tester.restore(testList);

        assertEquals(expectedMsg, actual);
        assertEquals(0, testList.size());
        assertEquals("", testList.fileString());
        assertEquals(0, testList.list().length);
    }

    @Test
    public void failedReadYesTest() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("./data/history.txt"))) {
            String fileHistory = "corrupted\nfile\n!!!!!!";
            writer.write(fileHistory);
        }

        String expectedMsg = "Attempting to retrieve history...\n"
                + "Data folder found!\n"
                + "History file found! Restoring data...\n"
                + "An error occurred while trying to read"
                + " the history.txt file.\n"
                + "Do you wish to delete it and start again? (y/n)";;

        TaskListWriter tester = new TaskListWriter();
        TaskList testList = new TaskList();
        String actual = tester.restore(testList);

        assertEquals(expectedMsg, actual);

        tester.recreateFile();
        assertEquals(0, testList.size());
        assertEquals("", testList.fileString());
        assertEquals(0, testList.list().length);
        assertEquals(false, testList.isOpen());

        File file = new File("./data/history.txt");
        assertEquals(0, file.length());
    }

    @Test
    public void failedReadNoTest() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("./data/history.txt"))) {
            String fileHistory = "corrupted\nfile\n!!!!!!";
            writer.write(fileHistory);
        }

        String expectedMsg = "Attempting to retrieve history...\n"
                + "Data folder found!\n"
                + "History file found! Restoring data...\n"
                + "An error occurred while trying to read"
                + " the history.txt file.\n"
                + "Do you wish to delete it and start again? (y/n)";;

        TaskListWriter tester = new TaskListWriter();
        TaskList testList = new TaskList();
        String actual = tester.restore(testList);

        assertEquals(expectedMsg, actual);
    }
}
