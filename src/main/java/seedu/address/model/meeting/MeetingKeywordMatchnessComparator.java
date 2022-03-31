package seedu.address.model.meeting;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Compares two {@code Meeting}s according to how much their information
 * (including title, start time, and tags)
 * matches the keywords (case is ignored)
 *
 * Priority:
 * 1. Full title is matched exactly
 * 2. Any word in the title is matched exactly
 * 3. Title is matched partially
 * 4. Tag is matched exactly
 * 5. Tag is matched partially
 * 6. Other information is matched
 */
public class MeetingKeywordMatchnessComparator implements Comparator<Meeting> {

    private final List<String> keywords;

    public MeetingKeywordMatchnessComparator(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Compares two {@code Meeting}s according to how much their information
     * (including title, phone, email, social media handles, and tags)
     * matches the keywords (case is ignored)
     *
     * Priority:
     * 1. Full title is matched exactly
     * 2. Any word in the title is matched exactly
     * 3. Title is matched partially
     * 4. Tag is matched exactly
     * 5. Tag is matched partially
     * 6. Other information is matched
     */
    @Override
    public int compare(Meeting m1, Meeting m2) {
        if (m1.getTitle().title.equalsIgnoreCase(String.join(" ", keywords))) {
            return -1;
        } else if (m2.getTitle().title.equalsIgnoreCase(String.join(" ", keywords))) {
            return 1;
        }
        int result = calculateTitleMatchness(m2.getTitle()) - calculateTitleMatchness(m1.getTitle());
        if (result != 0) {
            return result;
        }
        return calculateTagMatchness(m2.getTags()) - calculateTagMatchness(m1.getTags());
    }

    /**
     * Returns an integer to represent the extent to which the {@code title} matches the keywords.
     * 2 points for each keyword that matches a word in the title exactly;
     * 1 point for each partial match.
     */
    private int calculateTitleMatchness(Title title) {
        int matchness = 0;
        for (String titleWord : title.title.split(" ")) {
            matchness += keywords.stream().filter(titleWord::equalsIgnoreCase).count();
            matchness += keywords.stream().filter(keyword -> titleWord.toLowerCase()
                    .contains(keyword.toLowerCase())).count();
        }
        return matchness;
    }

    /**
     * Returns an integer to represent the extent to which the set of tags matches the keywords.
     * 2 points for each keyword that matches a tag exactly;
     * 1 point for each partial match.
     */
    private int calculateTagMatchness(Set<Tag> tags) {
        int matchness = 0;
        for (Tag tag : tags) {
            matchness += keywords.stream().filter(tag.tagName::equalsIgnoreCase).count();
            matchness += keywords.stream().filter(keyword -> tag.tagName.toLowerCase()
                    .contains(keyword.toLowerCase())).count();

        }
        return matchness;
    }

    /**
     * Checks whether this {@code MeetingKeywordMatchnessComparator} equals another object.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingKeywordMatchnessComparator // instanceof handles nulls
                && keywords.equals(((MeetingKeywordMatchnessComparator) other).keywords)); // state check
    }

}
