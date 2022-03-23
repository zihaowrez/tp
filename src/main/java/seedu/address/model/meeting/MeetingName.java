package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Meeting's name in the MeetingsTab.
 * Guarantees: immutable; is valid as declared in {@link #(String)}
 */
public class MeetingName {

    public final String name;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public MeetingName(String name) {
        requireNonNull(name);
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingName // instanceof handles nulls
                && name.equals(((MeetingName) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
