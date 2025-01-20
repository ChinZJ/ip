import java.util.ArrayList;

public class TaskList {
    private static final boolean TASK_IS_DONE = true;

    private ArrayList<Task> tasks;
    private boolean isActive;

    public TaskList() {
        tasks = new ArrayList<>();
        isActive = true;
    }

    public void list() {
        if (this.size() == 0) {
            System.out.print(MsgConstants.MSG_LINE.getString()
                    + MsgConstants.MSG_EMPTY_LIST.getString());
        } else {
            System.out.println(MsgConstants.MSG_LINE.getString()
                    + MsgConstants.MSG_CURRENT_TASKS.getString());
            for (int i = 0; i < this.size(); i++) {
                System.out.println("\t " + (i + 1) + ". " + this.get(i).getTaskStatus());
            }
        }
        System.out.println(MsgConstants.MSG_LINE.getString());
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

    public void mark(int index) {
        tasks.get(index).updateTask(TASK_IS_DONE);
    }

    public void unmark(int index) {
        tasks.get(index).updateTask(!TASK_IS_DONE);
    }

    public void add(Task newTask) {
        tasks.add(newTask);
        System.out.println(MsgConstants.MSG_LINE.getString()
                + MsgConstants.MSG_ADD.getString() + "\t " + newTask.getTaskStatus() + "\n"
                + MsgConstants.MSG_CURRENT_SIZE_P1.getString() + this.size()
                + MsgConstants.MSG_CURRENT_SIZE_P2.getString() + MsgConstants.MSG_LINE.getString());
    }
}
