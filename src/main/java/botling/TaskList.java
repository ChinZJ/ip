package botling;

import botling.tasks.Task;
import java.util.ArrayList;

/**
 * Stores all <code>Task</code> objects.
 * Possesses core list related functionality required to interact with <code>Task</code> objects.
 */
public class TaskList {
    private static final boolean TASK_IS_DONE = true;
    private static final String EMPTY_LIST = "\t There are currently no tasks!\n";

    private ArrayList<Task> tasks;
    private boolean isActive;

    /**
     * Constructor, always assumes to be active.
     * Loading history will be handled by a method separately.
     */
    public TaskList() {
        tasks = new ArrayList<>();
        isActive = TaskList.TASK_IS_DONE;
    }

    /**
     * Scans through all tasks present in the list, and returns them in String format.
     */
    public String list() {
        String message = "";
        if (this.size() == 0) {
            return message;
        } else {
            for (int i = 0; i < this.size(); i++) {
                message += "\t " + (i + 1) + ". " + this.get(i).getTaskStatus() + "\n";
            }
        }
        return message;
    }

    /**
     * Scans thorugh all tasks and returns only those that matches the input String.
     */
    public String find(String input) {
        String message = "";
        Task task;
        for (int i = 0; i < this.size(); i++) {
            task = this.get(i);
            if (task.toString().toLowerCase().contains(input.toLowerCase())) {
                message += "\t " + (i + 1) + ". " + this.get(i).getTaskStatus() + "\n";
            }
        }
        return message;
    }

    /**
     * Closes the TaskList and prevents further actions.
     */
    public void hasClose() {
        isActive = false;
    }

    /**
     * Checks if the TaskList is open for further actions.
     */
    public boolean isOpen() {
        return isActive;
    }

    /**
     * Returns the Task in the relative index position in the TaskList.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the current size of the TaskList.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Finds the indexed Task to be marked.
     *
     * @return The Task that has been marked.
     */
    public String mark(int index) {
        return tasks.get(index).updateTask(TASK_IS_DONE);
    }

    /**
     * Finds the indexed Task to be unmarked.
     *
     * @return The Task that has been unmarked.
     */
    public String unmark(int index) {
        return tasks.get(index).updateTask(!TASK_IS_DONE);
    }

    /**
     * Adds a new Task to the TaskList.
     *
     * @return The new Task that has been added, as well as the new size of the TaskList.
     */
    public String add(Task newTask) {
        tasks.add(newTask);
        return "\t " + newTask.getTaskStatus() + "\n";
    }

    /**
     * Removes the Task indexed in the TaskList.
     *
     * @return The Task that has been removed, as well as the new size of the TaskList.
     */
    public String remove(int index) {
        Task task = tasks.get(index);
        tasks.remove(index);
        return "\t " + task.getTaskStatus() + "\n";
    }

    /**
     * Generates a String format of all tasks to be read by <code>TsakListReader</code> object.
     */
    public String fileString() {
        String message = "";
        if (this.size() == 0) {
            return message;
        } else {
            for (int i = 0; i < this.size(); i++) {
                message += this.get(i).getTaskData() + "\n";
            }
        }
        return message;
    }

}
