package seedu.address.model.meeting;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Tests that a {@code Meeting}'s start time is within 24 hours from now.
 */
public class MeetingIn24HoursPredicate implements Predicate<Meeting> {

    @Override
    public boolean test(Meeting meeting) {
        return meeting.getStartTime().startTime.isAfter(LocalDateTime.now())
                && meeting.getStartTime().startTime.isBefore(LocalDateTime.now().plusHours(24));
    }

}
