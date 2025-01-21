/**
 * ToDo object that serves as a "default" Task.
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
}
