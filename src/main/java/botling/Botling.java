package botling;

import botling.commands.CommandParser;
import java.util.Scanner;

/**
 * Main class where application starts.
 */
public class Botling {
    /**
     * Generates objects required for program to function.
     * Handles the main loop of the program
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        TaskList tasks = new TaskList();
        CommandParser cmdParse = new CommandParser();

        // Generates the startup.
        System.out.println(cmdParse.start(tasks));

        // User input loop.
        while (tasks.isOpen()) {
            // Leveraging on object pass by reference
            System.out.println(cmdParse.parse(reader.nextLine(), tasks));
        }
    }
}
