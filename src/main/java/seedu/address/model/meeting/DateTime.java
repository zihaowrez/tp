package seedu.address.model.meeting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Meeting's date and time in the meeting tab.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS =
            "DATE AND TIME SHOULD BE IN THE FORMAT YYYY-MM-DD HHmm";

    public static final String ENDDATETIME_AFTER_STARTDATETIME =
            "End Date/Time cannot be before Start Date/Time";
    /*
     * The year must have 4 characters, i.e. 2019
     * The month must have 2 characters, i.e. 01
     * The date must have 2 characters, i.e. 15
     * The hour must have 2 characters, i.e. 18
     * The minute can have 1 to 2 characters, i.e. 0 or 30
     */
    public static final String VALIDATION_REGEX = "(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12]\\d|3[01]) ([01]?\\d|2[0-3])([0-5]?\\d)";

    public final LocalDateTime startDateTime;
    public final LocalDateTime endDateTime;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param startDateTime A valid StartTime.
     * @param endDateTime A valid StartTime.
     *
     */
    public DateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        requireNonNull(startDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a startDateTime string for Storage purposes.
     */
    public String jsonStartDateTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm").format(startDateTime);
    }

    /**
     * Returns a endDateTime string for Storage purposes.
     */
    public String jsonEndDateTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm").format(endDateTime);
    }

    @Override
    public String toString() {
        /* StringBuilder dateTimeString = new StringBuilder();
        if (startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
            dateTimeString.append(this.startDateTime.toLocalDate());
            dateTimeString.append(" (");
            dateTimeString.append(startDateTime.toLocalTime().toString() + "-");
            dateTimeString.append(endDateTime.toLocalTime().toString() + ")");
        } else {
            dateTimeString.append(this.startDateTime.toLocalDate().toString());
            dateTimeString.append(" ");
            dateTimeString.append(this.startDateTime.toLocalTime().toString());
            dateTimeString.append(" to ");
            dateTimeString.append(this.endDateTime.toLocalDate().toString());
            dateTimeString.append(" ");
            dateTimeString.append(this.endDateTime.toLocalTime().toString());
        }
        return dateTimeString.toString(); */
        return "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && startDateTime.equals(((DateTime) other).startDateTime)
                && endDateTime.equals(((DateTime) other).endDateTime)); // state check
    }

    @Override
    public int hashCode() {
        int startHashCode = startDateTime.hashCode();
        int endHashCode = endDateTime.hashCode();
        return startHashCode + endHashCode;
    }

}
