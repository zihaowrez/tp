package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} or {@code Tag} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.stream()
                .anyMatch(keyword -> person.getName().fullName.toLowerCase().contains(keyword.toLowerCase()))) {
            return true;
        }
        if (keywords.stream()
                .anyMatch(keyword -> person.getPhone().value.toLowerCase().contains(keyword.toLowerCase()))) {
            return true;
        }
        if (keywords.stream()
                .anyMatch(keyword -> person.getEmail().value.toLowerCase().contains(keyword.toLowerCase()))) {
            return true;
        }
        for (SocialMedia socialMedia : person.getSocialMedias()) {
            if (keywords.stream()
                    .anyMatch(keyword -> socialMedia.getPlatformName().toString().toLowerCase()
                            .contains(keyword.toLowerCase()))) {
                return true;
            }
            if (keywords.stream()
                    .anyMatch(keyword -> socialMedia.getPlatformDescription().toString().toLowerCase()
                            .contains(keyword.toLowerCase()))) {
                return true;
            }
        }
        for (Tag tag : person.getTags()) {
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
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
