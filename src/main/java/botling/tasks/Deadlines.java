package botling.tasks;

/**
 * A <code>Task</code> object that has a deadline.
 */
public class Deadlines extends Task {

    private final String by;

    /**
     * Default constructor.
     */
    public Deadlines(String name, String by) {
        super(name);
        this.by = by;
    }

    /**
     * Alternative constructor for full specification of attributes.
     */
    public Deadlines(String name, boolean isDone, String by) {
        super(name, isDone);
        this.by = by;
    }

    /**
     * Generates message to be printed.
     */
    @Override
    public String getTaskStatus() {
        return "[D]" + super.getTaskStatus() + " (by: " + by + ")";
    }

    /**
     * Generates the data version of the task status.
     */
    @Override
    public String getTaskData() {
        return "deadline\n" + by + "\n" + super.getTaskData();
    }

}
