package botling.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <code>Event</code> object that has a <code>date</code> for a start and end time.
 * <code>date</code>s are in the format yyyy-mm-dd HHmm (24-hour format).
 */
public class EventDate extends Events {
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    /**
     * Default constructor.
     */
    public EventDate(String name, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(name, startDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"))
                        .toString(),
                endDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"))
                        .toString());
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Alternative constructor for full specification of attributes.
     */
    public EventDate(String name, boolean isDone,
                     LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(name, isDone,
                startDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm")).toString(),
                endDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm")).toString());
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
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
