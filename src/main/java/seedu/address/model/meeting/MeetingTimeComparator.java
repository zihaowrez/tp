package seedu.address.model.meeting;

import java.util.Comparator;

/**
 * Compares the start times of two {@code Meeting}s.
 */
public class MeetingTimeComparator implements Comparator<Meeting> {

    /**
     * Compares the start times of two {@code Meeting}s.
     */
    @Override
    public int compare(Meeting m1, Meeting m2) {
        if (m1.getStartTime().startTime.isBefore(m2.getStartTime().startTime)) {
            return -1;
        }
        return 1;
    }

}
