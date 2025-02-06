package botling.tasks;

import java.util.Comparator;

/**
 * Custom comparator class for Tasks.
 */
public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        // Anything with dates are prioritized first - those without are likely to be leisure.
        // First check if both tasks have dates
        if (t1.hasDate()) {
            if (t2.hasDate()) {
                return compareBothDates(t1, t2);
            }
            return -1;
        } else if (t2.hasDate()) {
            return 1;
        } else {
            return t1.toString().compareTo(t2.toString());
        }
    }

    /**
     * Helper method for comparator.
     * Compares tasks when they both have dates.
     */
    private int compareBothDates(Task t1, Task t2) {
        return Comparator.comparing(Task::getEndDate)
                .thenComparing(Task::getStartDate)
                .thenComparing(Task::getCreateDate)
                .thenComparing(Task::toString)
                .compare(t1, t2); // Lexographical name if all else fails.
    }
}

