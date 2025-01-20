import java.util.ArrayList;

public class TaskList {
    private static final boolean TASK_IS_DONE = true;

    private ArrayList<Task> tasks;
    private boolean isActive;

    public TaskList() {
        tasks = new ArrayList<>();
        isActive = true;
    }

    public String list() {
        String message = MsgConstants.MSG_LINE.getString();
        if (this.size() == 0) {
            message += MsgConstants.MSG_EMPTY_LIST.getString();
        } else {
            message += MsgConstants.MSG_CURRENT_TASKS.getString();
            for (int i = 0; i < this.size(); i++) {
                message += "\t " + (i + 1) + ". " + this.get(i).getTaskStatus() + "\n";
            }
        }
        message += MsgConstants.MSG_LINE.getString();
        return message;
    }

    public void hasClose() {
        isActive = false;
    }

    public boolean isOpen() {
        return isActive;
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public String mark(int index) {
        return tasks.get(index).updateTask(TASK_IS_DONE);
    }

    public String unmark(int index) {
        return tasks.get(index).updateTask(!TASK_IS_DONE);
    }

    public String add(Task newTask) {
        tasks.add(newTask);
        String message = MsgConstants.MSG_LINE.getString()
                + MsgConstants.MSG_ADD.getString() + "\t " + newTask.getTaskStatus() + "\n"
                + MsgConstants.MSG_CURRENT_SIZE_P1.getString() + this.size()
                + MsgConstants.MSG_CURRENT_SIZE_P2.getString() + MsgConstants.MSG_LINE.getString();
        return message;
    }

    public String remove(int index) {
        Task task = tasks.get(index);
        tasks.remove(index);
        String message = MsgConstants.MSG_LINE.getString()
                + MsgConstants.MSG_TASK_DELETE.getString()
                + "\t " + task.getTaskStatus() + "\n"
                + MsgConstants.MSG_CURRENT_SIZE_P1.getString() + this.size()
                + MsgConstants.MSG_CURRENT_SIZE_P2.getString() + MsgConstants.MSG_LINE.getString();
        return message;
    }
}
