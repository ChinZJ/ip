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
     * Alternative constructor for full specification of attributes.
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
