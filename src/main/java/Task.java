public class Task {
    private static final boolean TASK_UNDONE = false;

    private String name;
    private boolean isDone;

    /**
     * Default constructor for Task object.
     * Assumes that the task is not done.
     *
     * @param name Name of task.
     */
    public Task(String name) {
        this.name = name;
        isDone = Task.TASK_UNDONE;
    }

    /**
     * Alternate constructor for Task object.
     *
     * @param name Name of task.
     * @param done Status of task.
     */
    public Task(String name, boolean done) {
        this.name = name;
        this.isDone = done;
    }

    /**
     * Updates the status of the task.
     *
     * @param isDone
     */
    public void updateTask(boolean isDone) {
        this.isDone = isDone;
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
