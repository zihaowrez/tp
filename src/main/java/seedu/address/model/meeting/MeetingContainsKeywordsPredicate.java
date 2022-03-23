package seedu.address.model.meeting;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Meeting}'s information (including name, link, start dateTime, end dateTime and tags)
 * matches any of the keywords given.
 */
public class MeetingContainsKeywordsPredicate implements Predicate<Meeting> {
    private final List<String> keywords;

    public MeetingContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Meeting meeting) {
        return testName(meeting) || testLink(meeting) || testStartDateTime(meeting) || testEndDateTime(meeting) || testTags(meeting);
    }

    private boolean testName(Meeting meeting) {
        return keywords.stream()
                .anyMatch(keyword -> meeting.getName().name.toLowerCase().contains(keyword.toLowerCase()));
    }

    private boolean testLink(Meeting meeting) {
        return keywords.stream()
                .anyMatch(keyword -> meeting.getLink().link.toLowerCase().contains(keyword.toLowerCase()));
    }

    private boolean testStartDateTime(Meeting meeting) {
        return keywords.stream()
                .anyMatch(keyword -> meeting.getDateTime().startDateTime.toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    private boolean testEndDateTime(Meeting meeting) {
        return keywords.stream()
                .anyMatch(keyword -> meeting.getDateTime().endDateTime.toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    private boolean testTags(Meeting meeting) {
        for (Tag tag : meeting.getTags()) {
            if (keywords.stream()
                    .anyMatch(keyword -> tag.tagName.toLowerCase().contains(keyword.toLowerCase()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.meeting.MeetingContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((seedu.address.model.meeting.MeetingContainsKeywordsPredicate) other).keywords)); // state check
    }
}
