package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.*;

public class PersonKeywordMatchnessComparator implements Comparator<Person> {

    private final List<String> keywords;

    public PersonKeywordMatchnessComparator(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public int compare(Person p1, Person p2) {
        int result1 = calculateNameMatchness(p2.getName());
        int result2 = calculateNameMatchness(p1.getName());
        int result = result1 - result2;
        if (result != 0) {
            return result;
        }
        result = calculateTagMatchness(p1.getTags()) - calculateTagMatchness(p2.getTags());
        return result;
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
