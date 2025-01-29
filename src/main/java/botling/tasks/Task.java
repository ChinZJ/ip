package botling.tasks;

/**
 * Parent class for various <code>Task</code> objects.
 * A <code>Task</code> has a <code>name</code> and a <code>status</code> of completion.
 */
public abstract class Task {
    private static final boolean TASK_UNDONE = false;

    private final String name;
    private boolean isDone;

    /**
     * Default constructor for Task object.
     * Assumes that the task is not done.
     * Utilizes base constructor (see below).
     *
     * @param name Name of task.
     */
    public Task(String name) {
        this(name, Task.TASK_UNDONE);
    }

    /**
     * Base constructor for Task object.
     * Allows for full specification of Task object construction.
     *
     * @param name Name of task.
     * @param done Status of task.
     */
    public Task(String name, boolean done) {
        this.name = name;
        this.isDone = done;
    }

    /**
     * <code>toString</code> method.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Updates the status of the task.
     */
    public String updateTask(boolean isDone) {
        this.isDone = isDone;
        return " " + this.getTaskStatus();

    }

    /**
     * Generates the String of the task status.
     * To be called by "list".
     *
     * @return message String containing status of task.
     */
    public String getTaskStatus() {
        String message;
        if (isDone) {
            message = ("[X] " + name);
        } else {
            message = ("[ ] " + name);
        }
        return message;
    }

    /**
     * Generates the data version of the task status.
     */
    public String getTaskData() {
        String message = name + "\n";
        if (isDone) {
            message += "true";
        } else {
            message += "false";
        }
        return message;
    }

    /**
     * To be overridden by <code>Task</code> objects with date.
     */
    public boolean hasDate() {
        return false;
    }
}
