package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s information (including name, phone, email, social media handles, and tags)
 * matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return String.join(" ", keywords);
    }

    @Override
    public boolean test(Person person) {
        return testName(person) || testPhone(person) || testEmail(person) || testSocialMedias(person)
                || testTags(person);
    }

    private boolean testName(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getName().fullName.toLowerCase().contains(keyword.toLowerCase()));
    }

    private boolean testPhone(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getPhone().value.toLowerCase().contains(keyword.toLowerCase()));
    }

    private boolean testEmail(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getEmail().value.toLowerCase().contains(keyword.toLowerCase()));
    }

    private boolean testSocialMedias(Person person) {
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
        return false;
    }

    private boolean testTags(Person person) {
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
