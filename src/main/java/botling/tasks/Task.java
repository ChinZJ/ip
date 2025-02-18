package botling.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Parent class for various <code>Task</code> objects.
 * A <code>Task</code> has a <code>name</code> and a <code>status</code> of completion.
 */
public abstract class Task {
    private static final String DATE_FORMAT = "dd MMM yyyy HHmm";
    private static final boolean TASK_NOT_DONE = false;

    private final String name;
    private boolean isDone;
    private LocalDateTime createDate;

    /**
     * Default constructor for Task object.
     * Assumes that the task is not done.
     * Utilizes base constructor (see below).
     *
     * @param name Name of task.
     */
    public Task(String name) {
        this(name, Task.TASK_NOT_DONE, LocalDateTime.now());
    }

    /**
     * Base constructor for Task object.
     * Allows for full specification of Task object construction.
     *
     * @param name Name of task.
     * @param isDone Status of task.
     */
    public Task(String name, boolean isDone, LocalDateTime createDate) {
        this.name = name;
        this.isDone = isDone;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Updates the status of the task.
     */
    public String updateTask(boolean isDone) {
        this.isDone = isDone;
        return " " + getTaskStatus();

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
            message += "true\n";
        } else {
            message += "false\n";
        }
        message += createDate
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT)).toString();
        return message;
    }

    /**
     * Checks if <code>Task</code> objects have a known recorded date.
     * Not to be confused with date of creation.
     */
    public boolean hasDate() {
        return false;
    }

    /**
     * Used by comparator class.
     * Returns date <code>Task</code> object was created
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Used by comparator class.
     * Returns start date of <code>Task</code>
     */
    public LocalDateTime getStartDate() {
        return createDate;
    }

    /**
     * Used by comparator class.
     */
    public LocalDateTime getEndDate() {
        return createDate;
    }
}
