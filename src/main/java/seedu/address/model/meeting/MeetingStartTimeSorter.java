package seedu.address.model.meeting;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Compares the start times of two {@code Meeting}s.
 */
public class MeetingStartTimeSorter implements Comparator<Meeting> {

    /**
     * Compares the start times of two {@code Meeting}s.
     */
    @Override
    public int compare(Meeting m1, Meeting m2) {
        if (m1.getStartTime().startTime.isBefore(LocalDateTime.now())) {
            if (m1.getStartTime().startTime.isAfter(m2.getStartTime().startTime)) {
                return -1;
            }
            return 1;
        }
        if (m2.getStartTime().startTime.isBefore(LocalDateTime.now())
                || m2.getStartTime().startTime.isAfter(m1.getStartTime().startTime)) {
            return -1;
        }
        return 1;
    }

}
