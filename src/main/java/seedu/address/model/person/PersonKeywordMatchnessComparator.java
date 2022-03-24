package seedu.address.model.person;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Compares two {@code Person}s according to how much their information
 * (including name, phone, email, social media handles, and tags)
 * matches the keywords (case is ignored)
 *
 * Priority:
 * 1. Full name is matched exactly
 * 2. Any word in the name is matched exactly
 * 3. Name is matched partially
 * 4. Tag is matched exactly
 * 5. Tag is matched partially
 * 6. Other information is matched
 */
public class PersonKeywordMatchnessComparator implements Comparator<Person> {

    private final List<String> keywords;

    public PersonKeywordMatchnessComparator(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Compares two {@code Person}s according to how much their information
     * (including name, phone, email, social media handles, and tags)
     * matches the keywords (case is ignored)
     *
     * Priority:
     * 1. Full name is matched exactly
     * 2. Any word in the name is matched exactly
     * 3. Name is matched partially
     * 4. Tag is matched exactly
     * 5. Tag is matched partially
     * 6. Other information is matched
     */
    @Override
    public int compare(Person p1, Person p2) {
        if (p1.getName().fullName.equalsIgnoreCase(String.join(" ", keywords))) {
            return -1;
        } else if (p2.getName().fullName.equalsIgnoreCase(String.join(" ", keywords))) {
            return 1;
        }
        int result = calculateNameMatchness(p2.getName()) - calculateNameMatchness(p1.getName());
        if (result != 0) {
            return result;
        }
        return calculateTagMatchness(p2.getTags()) - calculateTagMatchness(p1.getTags());
    }

    /**
     * Returns an integer to represent the extent to which the {@code name} matches the keywords.
     * 2 points for each keyword that matches a word in the name exactly;
     * 1 point for each partial match.
     */
    private int calculateNameMatchness(Name name) {
        int matchness = 0;
        for (String nameWord : name.fullName.split(" ")) {
            matchness += keywords.stream().filter(keyword -> nameWord.toLowerCase()
                    .equals(keyword.toLowerCase())).count();
            matchness += keywords.stream().filter(keyword -> nameWord.toLowerCase()
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
            matchness += keywords.stream().filter(keyword -> tag.tagName.toLowerCase()
                    .equals(keyword.toLowerCase())).count();
            matchness += keywords.stream().filter(keyword -> tag.tagName.toLowerCase()
                    .contains(keyword.toLowerCase())).count();

        }
        return matchness;
    }

    /**
     * Checks whether this {@code PersonKeywordMatchnessComparator} equals another object.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonKeywordMatchnessComparator // instanceof handles nulls
                && keywords.equals(((PersonKeywordMatchnessComparator) other).keywords)); // state check
    }

}
