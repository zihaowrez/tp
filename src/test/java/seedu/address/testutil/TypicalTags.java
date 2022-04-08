package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {

    public static final Tag FRIENDS = new TagBuilder().build();
    public static final Tag ENEMIES = new TagBuilder().withName("enemies").build();
    public static final Tag NEIGHBOURS = new TagBuilder().withName("neighbours").build();
    public static final Tag CLASSMATES = new TagBuilder().withName("classmates").build();

    public static final String KEYWORD_MATCHING_FRIENDS = "friends"; // A keyword that matches MEIER

    private TypicalTags() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tags.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Tag tag : getTypicalTags()) {
            ab.addTag(tag, "tail");
        }
        return ab;
    }

    public static List<Tag> getTypicalTags() {
        return new ArrayList<>(Arrays.asList(FRIENDS, ENEMIES, NEIGHBOURS, CLASSMATES));
    }
}
