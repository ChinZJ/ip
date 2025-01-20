import java.util.Scanner;

public class Botling {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        boolean isActive = true;
        TaskList tasks = new TaskList();

        System.out.println(MsgConstants.MSG_LINE.getString()
                + MsgConstants.MSG_GREET.getString()
                + MsgConstants.MSG_LINE.getString());

        while (tasks.isOpen()) {
            String input = reader.nextLine();
            // Leveraging on object pass by reference
            String message = CommandParser.parse(input, tasks);
            if (!message.equals(MsgConstants.MSG_EMPTY.getString())) {
                System.out.println(message);
            }
        }
    }
}
