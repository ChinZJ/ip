import java.util.Scanner;

public class Botling {
    public static void main(String[] args) {
        final String MESSAGE_LINE =
                "\t____________________________________________________________\n";
        final String MESSAGE_GREET = "\t Hello! I'm Botling\n"
                + "\t What can I do for you?\n";
        final String MESSAGE_FAREWELL = "\t Bye. Hope to see you again soon!\n";

        Scanner reader = new Scanner(System.in);
        boolean isActive = true;

        System.out.println(MESSAGE_LINE + MESSAGE_GREET + MESSAGE_LINE);


        while (isActive) {
            String input = reader.nextLine();
            if (input.equals("bye")) {
                isActive = false;
                System.out.println(MESSAGE_LINE + MESSAGE_FAREWELL + MESSAGE_LINE);
            } else {
                System.out.println(MESSAGE_LINE + "\t " + input + "\n" + MESSAGE_LINE);
            }
        }
    }
}
