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
        return testTitle(meeting) || testLink(meeting) || testStartTime(meeting)
                || testDuration(meeting) || testTags(meeting);
    }

    private boolean testTitle(Meeting meeting) {
        return keywords.stream()
                .anyMatch(keyword -> meeting.getTitle().title.toLowerCase().contains(keyword.toLowerCase()));
    }

    private boolean testLink(Meeting meeting) {
        return keywords.stream()
                .anyMatch(keyword -> meeting.getLink().link.toLowerCase().contains(keyword.toLowerCase()));
    }

    private boolean testStartTime(Meeting meeting) {
        return keywords.stream()
                .anyMatch(keyword -> meeting.getStartTime().toString().contains(keyword.toLowerCase()));
    }

    private boolean testDuration(Meeting meeting) {
        return keywords.stream()
                .anyMatch(keyword -> meeting.getDuration().toString().contains(keyword.toLowerCase()));
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
                || (other instanceof MeetingContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((MeetingContainsKeywordsPredicate) other).keywords)); // state check
    }
}
