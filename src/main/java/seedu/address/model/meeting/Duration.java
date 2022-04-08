package seedu.address.model.meeting;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Meeting's mins in the MeetingsBook.
 * Guarantees: immutable; is valid as declared in {@Duration #(String)}
 */
public class Duration {

    public static final String MESSAGE_CONSTRAINTS =
            "Duration (in minutes) should be a positive integer in between 1 and 10,000 (Inclusive)";

    public final int mins;

    /**
     * Constructs a {@code Name}.
     *
     * @param mins Length of the meeting in minutes.
     */
    public Duration(int mins) {
        checkArgument(isValidDuration(mins), MESSAGE_CONSTRAINTS);
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
