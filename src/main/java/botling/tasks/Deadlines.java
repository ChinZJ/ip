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
     * Alternate constructor for full specification of attributes.
     *
     * @param name Name of the event.
     * @param isDone Marks the task as done or undone.
     * @param by Name of the dateline, else overridden by <code>dateBy</code>.
     * @param dateBy Deadline in date time format where applicable.
     * @param createDate Date time format for creation.
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
     * Sets end date to the deadline date.
     */
    @Override
    public LocalDateTime getEndDate() {
        return dateBy.orElse(super.getStartDate());
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
