package botling.tasks;

/**
 * A "default" <code>Task</code> object.
 * Does not have additional functionalities.
 */
public class ToDo extends Task {

    /**
     * Default constructor.
     */
    public ToDo(String name) {
        super(name);
    }

    /**
     * Alternative constructor for full specification of attributes.
     */
    public ToDo(String name, boolean isDone) {
        super(name, isDone);
    }

    /**
     * Generates message to be printed.
     */
    @Override
    public String getTaskStatus() {
        String message = "[T]" + super.getTaskStatus();
        return message;
    }

    /**
     * Generates the data version of the task status.
     */
    @Override
    public String getTaskData() {
        String message = "todo\n" + super.getTaskData();
        return message;
    }

}
