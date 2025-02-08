package botling.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * A <code>Task</code> object that has a <code>start</code> and an <code>end</code>.
 */
public class Events extends Task {
    private static final String DATE_ICON = "[DATE] ";
    private static final String DATE_FORMAT = "dd MMM yyyy HHmm";

    private final String from;
    private final String to;
    private final Optional<LocalDateTime> dateStart;
    private final Optional<LocalDateTime> dateEnd;

    /**
     * Default constructor.
     */
    public Events(String name, String from, String to,
                  Optional<LocalDateTime> dateStart, Optional<LocalDateTime> dateEnd) {
        super(name);
        this.from = dateStart
                .map(date -> date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).toString())
                .orElse(from);
        this.to = dateEnd
                .map(date -> date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).toString())
                .orElse(to);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    /**
     * Alternate constructor for full specification of attributes.
     *
     * @param name Name of the event.
     * @param isDone Marks the task as done or undone.
     * @param from Name of the start time, else overridden by <code>dateStart</code>.
     * @param to Name of the end time, else overridden by <code>dateEnd</code>.
     * @param dateStart Start time in date time format where applicable.
     * @param dateEnd End time in date time format where applicable.
     * @param createDate Date time format for creation.
     */
    public Events(String name, boolean isDone, String from, String to,
                  Optional<LocalDateTime> dateStart, Optional<LocalDateTime> dateEnd,
                  LocalDateTime createDate) {
        super(name, isDone, createDate);
        this.from = dateStart
                .map(date -> date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).toString())
                .orElse(from);
        this.to = dateEnd
                .map(date -> date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).toString())
                .orElse(to);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    /**
     * Updates parent method since it has a <code>LocalDateTime</code> object.
     */
    @Override
    public boolean hasDate() {
        return (dateStart.isPresent() || dateEnd.isPresent());
    }

    /**
     * Changes start date to from input where applicable.
     */
    @Override
    public LocalDateTime getStartDate() {
        return dateStart.orElse(super.getStartDate());
    }

    /**
     * Changes end date to to input where applicable.
     */
    @Override
    public LocalDateTime getEndDate() {
        if (dateEnd.isPresent()) {
            return dateEnd.get();
        }
        // Compare between start and create date, and return the later.
        LocalDateTime createDate = super.getStartDate();
        return dateStart.map(start -> start.isAfter(createDate) ? start : createDate)
                .orElse(createDate);
    }

    /**
     * Generates message to be printed.
     */
    @Override
    public String getTaskStatus() {
        String message = "[E]" + super.getTaskStatus() + " (from: " + from + " to: " + to + ")";
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
        return "event\n" + from + "\n" + to + "\n" + super.getTaskData();
    }

}
