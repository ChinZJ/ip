package botling;

import botling.commands.CommandParser;
import java.util.Scanner;

/**
 * Main class where application starts.
 */
public class Botling {
    private TaskList tasks = new TaskList();
    private CommandParser cmdParse = new CommandParser();

    /**
     * Generates the start message.
     * Includes loading of history with user.
     */
    public String startUp() {
        return cmdParse.start(tasks);
    }

    /** Generates a response for the user's chat message
     * Adapted from main method.
     */
    public String getResponse(String input) {
        return cmdParse.parse(input, tasks);
    }

    /**
     * Generates objects required for program to function.
     * Handles the main loop of the program.
     */
//    @Deprecated
//    public static void main(String[] args) {
//        Scanner reader = new Scanner(System.in);
//        TaskList tasks = new TaskList();
//        CommandParser cmdParse = new CommandParser();
//
//        // Generates the startup.
//        System.out.println(cmdParse.start(tasks));
//
//        // User input loop.
//        while (tasks.isOpen()) {
//            // Leveraging on object pass by reference
//            System.out.println(cmdParse.parse(reader.nextLine(), tasks));
//        }
//        reader.close();
//    }
}
