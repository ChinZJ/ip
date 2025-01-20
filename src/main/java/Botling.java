import java.util.Scanner;
import java.util.ArrayList;

public class Botling {
    public static void main(String[] args) {
        // For outputs by Botling
        final String MSG_LINE =
                "\t____________________________________________________________\n";
        final String MSG_GREET = "\t Hello! I'm Botling\n"
                + "\t What can I do for you?\n";
        final String MSG_FAREWELL = "\t Bye. Hope to see you again soon!\n";
        final String MSG_EMPTY_LIST = "\t There are currently no tasks!\n";
        final String MSG_ADD = "\t Got it. I've added this task: \n";
        final String MSG_CURRENT_SIZE_P1 = "\t Now you have ";
        final String MSG_CURRENT_SIZE_P2 = " tasks in the list.\n";
        final String MSG_CURRENT_TASKS = "\t Here are the tasks in your list:\n";
        final String MSG_TASK_DONE = "\t Nice! I've marked this task as done:\n";
        final String MSG_TASK_UNDONE = "\t Ok, I've marked this task as not done yet:\n";
        // Unique Commands
        final String CMD_BYE = "bye";
        final String CMD_LIST = "list";
        final String CMD_BY = " /by ";
        final String CMD_FROM = " /from ";
        final String  CMD_TO = " /to ";
        // Tasks constants
        final boolean TASK_IS_DONE = true;
        final int TASK_FIX_INDEX = 1;
        final int TASK_MARK_IDX= 5;
        final int TASK_UNMARK_IDX = 7;
        final int TASK_TODO_IDX = 4;
        final int TASK_EVENT_IDX = 5;
        final int TASK_DEADLINE_IDX = 8;
        // Tasks regex
        final String TASK_MARK = "mark \\d+";
        final String TASK_UNMARK = "unmark \\d+";
        final String TASK_TODO = "todo .+";
        final String TASK_DEADLINE = "deadline .+ /by .+";
        final String TASK_EVENT = "event .+ /from .+ /to .+";


        Scanner reader = new Scanner(System.in);
        boolean isActive = true;
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(MSG_LINE + MSG_GREET + MSG_LINE);


        while (isActive) {
            String input = reader.nextLine();
            if (input.equals(CMD_BYE)) {
                // "bye"
                isActive = false;
                System.out.println(MSG_LINE + MSG_FAREWELL + MSG_LINE);
            } else if (input.equals(CMD_LIST)) {
                // "list"
                if (tasks.size() > 0) {
                    System.out.print(MSG_LINE + MSG_CURRENT_TASKS);
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("\t " + (i + 1) + ". " + tasks.get(i).getTaskStatus());
                    }
                    System.out.println(MSG_LINE);
                } else {
                    System.out.println(MSG_LINE + MSG_EMPTY_LIST + MSG_LINE);
                }
            } else if (input.matches(TASK_MARK)) {
                // "mark X"
                int index = Integer.parseInt(input.substring(TASK_MARK_IDX)) - TASK_FIX_INDEX;
                tasks.get(index).updateTask(TASK_IS_DONE);
                System.out.println(MSG_LINE + MSG_TASK_DONE
                        + "\t\t " + tasks.get(index).getTaskStatus() + "\n" + MSG_LINE);
            } else if (input.matches(TASK_UNMARK)) {
                // "unmark X"
                int index = Integer.parseInt(input.substring(TASK_UNMARK_IDX)) - TASK_FIX_INDEX;
                tasks.get(index).updateTask(!TASK_IS_DONE);
                System.out.println(MSG_LINE + MSG_TASK_UNDONE
                        + "\t\t " + tasks.get(index).getTaskStatus() + "\n" + MSG_LINE);
            } else {
                // Check type of task to be added and act accordingly
                Task newTask;
                if (input.matches(TASK_TODO)) {
                  newTask = new ToDo(input.substring(TASK_TODO_IDX));
                } else if (input.matches(TASK_DEADLINE)) {
                    String withoutDeadline = input.substring(TASK_DEADLINE_IDX);
                    String[] firstSplit = withoutDeadline.split(CMD_BY);
                    String deadlineName = firstSplit[0];
                    String by = firstSplit[1];
                    newTask = new Deadlines(deadlineName, by);
                } else if (input.matches(TASK_EVENT)) {
                    String withoutEvent = input.substring(TASK_EVENT_IDX);
                    String[] firstSplit = withoutEvent.split(CMD_FROM);
                    String eventName = firstSplit[0];
                    String[] secondSplit = firstSplit[1].split(CMD_TO);
                    String eventFrom = secondSplit[0];
                    String eventTo = secondSplit[1];
                    newTask = new Events(eventName, eventFrom, eventTo);
                } else {
                    newTask = new Task(input);
                }
                tasks.add(newTask);
                System.out.println(MSG_LINE
                        + MSG_ADD + "\t " + newTask.getTaskStatus() + "\n"
                        + MSG_CURRENT_SIZE_P1 + tasks.size() + MSG_CURRENT_SIZE_P2
                        + MSG_LINE);
            }
        }
    }
}
