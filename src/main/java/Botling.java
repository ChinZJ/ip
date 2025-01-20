import java.util.Scanner;
import java.util.ArrayList;

public class Botling {
    public static void main(String[] args) {
        // For outputs by Botling
        final String MESSAGE_LINE =
                "\t____________________________________________________________\n";
        final String MESSAGE_GREET = "\t Hello! I'm Botling\n"
                + "\t What can I do for you?\n";
        final String MESSAGE_FAREWELL = "\t Bye. Hope to see you again soon!\n";
        final String MESSAGE_EMPTY_LIST = "\t There are currently no tasks!\n";
        final String MESSAGE_ADD = "\t added: ";
        final String MESSAGE_TASK_DONE = "\t Nice! I've marked this task as done:\n";
        final String MESSAGE_TASK_UNDONE = "\t Ok, I've marked this task as not done yet:\n";
        // Unique Commands
        final String CMD_BYE = "bye";
        final String CMD_LIST = "list";
        // Regex to check for tasks
        final boolean TASK_IS_DONE = true;
        final int TASK_FIX_INDEX = 1;
        final String TASK_MARK = "mark \\d+";
        final int TASK_MARK_IDX = 5;
        final String TASK_UNMARK = "unmark \\d+";
        final int TASK_UNMARK_IDX = 7;

        Scanner reader = new Scanner(System.in);
        boolean isActive = true;
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(MESSAGE_LINE + MESSAGE_GREET + MESSAGE_LINE);


        while (isActive) {
            String input = reader.nextLine();
            if (input.equals(CMD_BYE)) {
                isActive = false;
                System.out.println(MESSAGE_LINE + MESSAGE_FAREWELL + MESSAGE_LINE);
            } else if (input.equals(CMD_LIST)) {
                if (tasks.size() > 0) {
                    System.out.print(MESSAGE_LINE);
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("\t " + (i + 1) + ". " + tasks.get(i).getTaskStatus());
                    }
                    System.out.println(MESSAGE_LINE);
                } else {
                    System.out.println(MESSAGE_LINE + MESSAGE_EMPTY_LIST + MESSAGE_LINE);
                }
            } else if (input.matches(TASK_MARK)) {
                int index = Integer.parseInt(input.substring(TASK_MARK_IDX)) - TASK_FIX_INDEX;
                tasks.get(index).updateTask(TASK_IS_DONE);
                System.out.println(MESSAGE_LINE + MESSAGE_TASK_DONE
                        + "\t " + tasks.get(index).getTaskStatus() + "\n" + MESSAGE_LINE);
            } else if (input.matches(TASK_UNMARK)) {
                int index = Integer.parseInt(input.substring(TASK_UNMARK_IDX)) - TASK_FIX_INDEX;
                tasks.get(index).updateTask(!TASK_IS_DONE);
                System.out.println(MESSAGE_LINE + MESSAGE_TASK_UNDONE
                        + "\t " + tasks.get(index).getTaskStatus() + "\n" + MESSAGE_LINE);
            } else {
                Task newTask = new Task(input);
                tasks.add(newTask);
                System.out.println(MESSAGE_LINE + MESSAGE_ADD + input + "\n" + MESSAGE_LINE);
            }
        }
    }
}
