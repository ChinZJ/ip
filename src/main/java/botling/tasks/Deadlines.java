package botling.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * A <code>Task</code> object that has a deadline.
 */
public class Deadlines extends Task {
    private static final String DATE_ICON = "[DATE] ";
    private static final String DATE_FORMAT = "dd MMM yyyy HHmm";

    private final String by;
    private final Optional<LocalDateTime> dateBy;

    /**
     * Default constructor.
     */
    public Deadlines(String name, String by, Optional<LocalDateTime> dateBy) {
        super(name);
        this.by = dateBy
                .map(date -> date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).toString())
                .orElse(by);
        this.dateBy = dateBy;
    }

    /**
     * Alternative constructor for full specification of attributes.
     */
    public Deadlines(String name, boolean isDone, String by,
                     Optional<LocalDateTime> dateBy, LocalDateTime createDate) {
        super(name, isDone, createDate);
        this.by = dateBy
                .map(date -> date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).toString())
                .orElse(by);
        this.dateBy = dateBy;
    }

    /**
     * Updates parent method since it has a <code>LocalDateTime</code> object.
     */
    @Override
    public boolean hasDate() {
        return dateBy.isPresent();
    }

    /**
     * Generates message to be printed.
     */
    @Override
    public String getTaskStatus() {
        String message = "[D]" + super.getTaskStatus() + " (by: " + by + ")";
        if (hasDate()) {
            message = DATE_ICON + message;
        }
        return message;
    }

    /**
     * Generates the data version of the task status.
     */
    @Override
    public String getTaskData() {
        return "deadline\n" + by + "\n" + super.getTaskData();
    }

}
