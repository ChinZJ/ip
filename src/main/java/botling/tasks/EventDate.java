package botling.tasks;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * <code>Event</code> object that has a <code>date</code> for a start and end time.
 * <code>date</code>s are in the format yyyy-mm-dd hhmm (24 hour format).
 */
public class EventDate extends Events{
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    /**
     * Default constructor.
     */
    public EventDate(String name, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(name, startDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm")).toString()
                , endDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm")).toString());
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Alternative constructor for full specification of attributes.
     */
    public EventDate(String name, boolean isDone, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(name, isDone, startDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm")).toString()
                , endDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm")).toString());
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
