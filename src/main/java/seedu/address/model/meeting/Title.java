package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Meeting's title in the MeetingsBook.
 * Guarantees: immutable; is valid as declared in {@link #(String)}
 */
public class Title {

    public final String title;

    /**
     * Constructs a {@code Name}.
     *
     * @param title A valid title.
     */
    public Title(String title) {
        requireNonNull(title);
        this.title = title;
    }


    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && title.equals(((Title) other).title)); // state check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

}
