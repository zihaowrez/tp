package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Meeting's mins in the MeetingsBook.
 * Guarantees: immutable; is valid as declared in {@Duration #(String)}
 */
public class Duration {

    public static final String MESSAGE_CONSTRAINTS =
            "Duration (in mins) should be a positive integer smaller than 10,000";

    public final int mins;

    /**
     * Constructs a {@code Name}.
     *
     * @param mins Length of the meeting in minutes.
     */
    public Duration(int mins) {
        requireNonNull(mins);
        this.mins = mins;
    }

    /**
     * Returns true if a given string is a valid link.
     */
    public static boolean isValidDuration(int mins) {
        return mins > 0 && mins <= 10000;
    }

    @Override
    public String toString() {
        return String.valueOf(mins);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duration // instanceof handles nulls
                && mins == ((Duration) other).mins); // state check
    }

}
