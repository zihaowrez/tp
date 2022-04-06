package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Meeting's date and time in the meeting tab.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {

    public static final String MESSAGE_CONSTRAINTS =
            "Time must be in the format \"yyyy-M-d HHmm\"";

    public final LocalDateTime startTime;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param startTime A valid StartTime.
     *
     */
    public StartTime(String startTime) {
        requireNonNull(startTime);
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-M-d HHmm"));
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidStartTime(String test) {
        try {
            LocalDateTime.parse(test, DateTimeFormatter.ofPattern("yyyy-M-d HHmm"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isInThePast(StartTime startTime) {
        return startTime.startTime.isBefore(LocalDateTime.now());
    }

    /**
     * Returns a startTime string for Storage purposes.
     */
    public String jsonStartTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm").format(startTime);
    }

    @Override
    public String toString() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(startTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        int startTimeHashCode = startTime.hashCode();
        return startTimeHashCode;
    }

}
