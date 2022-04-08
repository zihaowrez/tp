package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Meeting's link in the meeting tab.
 * Guarantees: immutable; is valid as declared in {@link #isValidLink(String)}
 */
public class Link {

    public static final String MESSAGE_CONSTRAINTS =
            "Links should be in the form Protocol://Sub-domain.Domain.Top-Level Domain/Path "
            + "and adhere to the following constraints:\n"
            + "1. No whitespaces should be present between the protocol, sub-domain, domain, top-level domain "
            + "and or path\n"
            + "2. The domain name must:\n"
            + "- Only have characters from A-Z and 0-9, and hyphens\n"
            + "- Hyphens should not appear at the start or end\n"
            + "- Not contain any special characters like _!@#$%^&*,\n \n"
            + "eg. https://www.zoom.sg";
    public static final String PROTOCOL_CHECK = "https?:\\/\\/";
    public static final String SUBDOMAIN_CHECK = "(www\\.)?";
    public static final String START_DOMAIN_CHECK = "([^:;><.,?/\\\'\\\"\\[\\]{}+=_.!@#$%^&*()-])";
    public static final String MIDDLE_DOMAIN_CHECK = "[-a-zA-Z0-9]{0,253}";
    public static final String END_DOMAIN_CHECK = "[^:;><.,?/\\\'\\\"\\[\\]{}+=_.!@#$%^&*()-]\\.";
    public static final String TOPLEVEL_DOMAIN_AND_PATH_CHECK = "[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
    public static final String VALIDATION_REGEX = PROTOCOL_CHECK + SUBDOMAIN_CHECK + START_DOMAIN_CHECK
            + MIDDLE_DOMAIN_CHECK + END_DOMAIN_CHECK + TOPLEVEL_DOMAIN_AND_PATH_CHECK;

    public final String link;

    /**
     * Constructs a {@code Link}.
     *
     * @param link A valid link.
     */
    public Link(String link) {
        requireNonNull(link);
        checkArgument(isValidLink(link.trim()), MESSAGE_CONSTRAINTS);
        StringBuilder updatedLink = new StringBuilder();
        updatedLink.append(link);

        this.link = updatedLink.toString();
    }

    /**
     * Returns true if a given string is a valid link.
     */
    public static boolean isValidLink(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return link;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Link // instanceof handles nulls
                && link.equals(((Link) other).link)); // state check
    }

    @Override
    public int hashCode() {
        return link.hashCode();
    }
}
