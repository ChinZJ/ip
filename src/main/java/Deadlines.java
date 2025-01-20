public class Deadlines extends Task {
    private String by;

    public Deadlines(String name, String by) {
        super(name);
        this.by = by;
    }

    public Deadlines(String name, boolean isDone, String by) {
        super(name, isDone);
        this.by = by;
    }

    @Override
    public String getTaskStatus() {
        String message = "[D]" + super.getTaskStatus() + " (by: " + by + ")";
        return message;
    }
}
