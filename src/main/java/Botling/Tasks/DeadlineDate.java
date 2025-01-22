package Botling.Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <code>Deadline</code> object that also has a <code>date</code> for a deadline.
 * <code>date</code>s are in the format yyyy-mm-dd hhmm (24 hour format).
 */
public class DeadlineDate extends Deadlines{
    private LocalDateTime dateTime;

    /**
     * Default constructor.
     */
    public DeadlineDate(String name, LocalDateTime dateTime) {
        super(name, dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm")).toString());
        this.dateTime = dateTime;
    }

    /**
     * Alternative constructor for full specification of attributes.
     */
    public DeadlineDate(String name, boolean isDone, LocalDateTime dateTime) {
        super(name, isDone, dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm")).toString());
        this.dateTime = dateTime;
    }

    /**
     * Updates parent method since it has a <code>LocalDateTime</code> object.
     */
    @Override
    public boolean hasDate() {
        return true;
    }

    /**
     * Generates message to be printed.
     */
    @Override
    public String getTaskStatus() {
        String message = super.getTaskStatus() + " (date)";
        return message;
    }

}
