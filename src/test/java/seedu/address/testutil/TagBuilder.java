package seedu.address.testutil;

import seedu.address.model.tag.Tag;


/**
 * A utility class to help with building Person objects.
 */
public class TagBuilder {

    public static final String DEFAULT_TAG_NAME = "friends";

    private String tagName;

    /**
     * Creates a {@code TagBuilder} with the default details.
     */
    public TagBuilder() {
        tagName = DEFAULT_TAG_NAME;
    }

    /**
     * Initializes the TagBuilder with the data of {@code tagToCopy}.
     */
    public TagBuilder(Tag tagToCopy) {
        tagName = tagToCopy.getTagName();
    }

    /**
     * Sets the name of the {@code Tag} that we are building.
     */
    public TagBuilder withName(String name) {
        this.tagName = name;
        return this;
    }



    public Tag build() {
        return new Tag(tagName);
    }

}
