import java.lang.Exception;

public class Task {
    private static final boolean TASK_UNDONE = false;

    private String name;
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
     * tostring() method.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Updates the status of the task.
     */
    public String updateTask(boolean isDone) {
        this.isDone = isDone;
        String message = "";
        if (isDone) {
            message = MsgConstants.MSG_LINE.getString()
                    + MsgConstants.MSG_TASK_DONE.getString()
                    + "\t\t " + this.getTaskStatus() + "\n"
                    + MsgConstants.MSG_LINE.getString();
        } else {
            message = MsgConstants.MSG_LINE.getString()
                    + MsgConstants.MSG_TASK_UNDONE.getString()
                    + "\t\t " + this.getTaskStatus() + "\n"
                    + MsgConstants.MSG_LINE.getString();
        }
        return message;

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
            message = ("[ ]" + name);
        }
        return message;
    }
}
