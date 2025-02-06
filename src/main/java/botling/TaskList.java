package botling;

import java.util.ArrayList;

import botling.tasks.Task;
import botling.tasks.TaskComparator;

/**
 * Stores all <code>Task</code> objects.
 * Possesses core list related functionality required to interact with <code>Task</code> objects.
 */
public class TaskList {
    private static final boolean TASK_IS_DONE = true;

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

    public void clear() {
        tasks.clear();
    }

    /**
     * Scans through all tasks present in the list, and returns them in String format.
     */
    public String[] list() {
        String[] items = new String[this.size()];
        for (int i = 0; i < this.size(); i++) {
            String entry = " " + (i + 1) + ". " + this.get(i).getTaskStatus();
            if (i != this.size() - 1) {
                entry += "\n";
            }
            items[i] = entry;
        }
        return items;
    }

    /**
     * Scans through all tasks and returns only those that matches the input String.
     */
    public String[] find(String input) {
        String[] items = new String[this.size()];
        Task task;
        boolean isNotFirst = false;
        for (int i = 0; i < this.size(); i++) {
            task = this.get(i);
            String entry = ""; // Dummy initialization.
            if (task.toString().toLowerCase().contains(input.toLowerCase())) {
                if (!isNotFirst) {
                    isNotFirst = true;
                } else {
                    items[i - 1] += "\n";
                }
                entry += " " + (i + 1) + ". " + this.get(i).getTaskStatus();
            }
            items[i] = entry;
        }
        return items;
    }

    /**
     * Opens the TaskList and allows for actions.
     */
    public void hasOpen() {
        isActive = true;
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
        tasks.sort(new TaskComparator());
        return " " + newTask.getTaskStatus();
    }

    /**
     * Removes the Task indexed in the TaskList.
     *
     * @return The Task that has been removed, as well as the new size of the TaskList.
     */
    public String remove(int index) {
        Task task = tasks.get(index);
        tasks.remove(index);
        return " " + task.getTaskStatus();
    }

    /**
     * Generates a String format of all tasks to be read by <code>TaskListWriter</code> object.
     * Can be optimized in future to become lazy.
     */
    public String fileString() {
        StringBuilder strCreator = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            strCreator.append(this.get(i).getTaskData());
            if (i != this.size() - 1) {
                strCreator.append("\n");
            }
        }
        return strCreator.toString();
    }

}
