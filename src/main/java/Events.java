public class Events extends Task {
    private String from;
    private String to;

    public Events(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    public Events(String name, boolean isDone, String from, String to) {
        super(name, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskStatus() {
        String message = "[E]" + super.getTaskStatus() + " (from: " + from + " to: " + to + ")";
        return message;
    }
}
