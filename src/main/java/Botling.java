import java.util.Scanner;
import java.util.ArrayList;

public class Botling {
    public static void main(String[] args) {
        final String MESSAGE_LINE =
                "\t____________________________________________________________\n";
        final String MESSAGE_GREET = "\t Hello! I'm Botling\n"
                + "\t What can I do for you?\n";
        final String MESSAGE_FAREWELL = "\t Bye. Hope to see you again soon!\n";
        final String MESSAGE_EMPTY_LIST = "\t There are currently no tasks!\n";
        final String MESSAGE_ADD = "\t added: ";

        Scanner reader = new Scanner(System.in);
        boolean isActive = true;
        ArrayList<String> tasks = new ArrayList<>();

        System.out.println(MESSAGE_LINE + MESSAGE_GREET + MESSAGE_LINE);


        while (isActive) {
            String input = reader.nextLine();
            if (input.equals("bye")) {
                isActive = false;
                System.out.println(MESSAGE_LINE + MESSAGE_FAREWELL + MESSAGE_LINE);
            } else if (input.equals("list")) {
                if (tasks.size() > 0) {
                    System.out.print(MESSAGE_LINE);
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("\t " + (i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println(MESSAGE_LINE);
                } else {
                    System.out.println(MESSAGE_LINE + MESSAGE_EMPTY_LIST + MESSAGE_LINE);
                }
            } else {
                tasks.add(input);
                System.out.println(MESSAGE_LINE + MESSAGE_ADD + input + "\n" + MESSAGE_LINE);
            }
        }
    }
}
