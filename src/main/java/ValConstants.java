public enum ValConstants {
    // Tasks constants.
    TASK_FIX_IDX(1),
    TASK_MARK_IDX(5),
    TASK_UNMARK_IDX(7),
    TASK_TODO_IDX(5),
    TASK_EVENT_IDX(6),
    TASK_DEADLINE_IDX(9),
    TASK_BY_IDX(5),
    TASK_FROM_IDX(7),
    TASK_TO_IDX(5);

    private final int val;

    ValConstants(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
