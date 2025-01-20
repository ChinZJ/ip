public class ToDo extends Task {


    public ToDo(String name) {
        super(name);
    }

    public ToDo(String name, boolean isDone) {
        super(name, isDone);
    }

    @Override
    public String getTaskStatus() {
        String message = "[T]" + super.getTaskStatus();
        return message;
    }
}
