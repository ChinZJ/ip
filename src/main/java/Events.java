/**
 * Events object that has a start date and an end date.
 */
public class Events extends Task {

    private String from;
    private String to;

    /**
     * Default constructor.
     */
    public Events(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    /**
     * Alternative constructor for full specification of attributes.
     */
    public Events(String name, boolean isDone, String from, String to) {
        super(name, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Generates message to be printed.
     */
    @Override
    public String getTaskStatus() {
        String message = "[E]" + super.getTaskStatus() + " (from: " + from + " to: " + to + ")";
        return message;
    }
}
