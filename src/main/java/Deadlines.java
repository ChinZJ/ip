/**
 * Deadlines object that has a completion date.
 */
public class Deadlines extends Task {

    private String by;

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
        String message = "[D]" + super.getTaskStatus() + " (by: " + by + ")";
        return message;
    }
}
