package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

    public int calculateNameMatchness(Name name) {
        int matchness = 0;
        for (String nameWord : name.fullName.split(" ")) {
            matchness += keywords.stream().filter(keyword -> nameWord.toLowerCase()
                    .equals(keyword.toLowerCase())).count();
            matchness += keywords.stream().filter(keyword -> nameWord.toLowerCase()
                    .contains(keyword.toLowerCase())).count();
        }
        return matchness;
    }

    public int calculateTagMatchness(Set<Tag> tags) {
        int matchness = 0;
        for (Tag tag : tags) {
            matchness += keywords.stream().filter(keyword -> tag.tagName.toLowerCase()
                    .equals(keyword.toLowerCase())).count();
            matchness += keywords.stream().filter(keyword -> tag.tagName.toLowerCase()
                    .contains(keyword.toLowerCase())).count();

        }
        return matchness;
    }

}
