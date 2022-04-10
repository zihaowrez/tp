package seedu.address.model.socialmedia;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class PlatformName {

    public static final String MESSAGE_CONSTRAINTS = "PLATFORM NAME CONSTRAINTS!";
    private final String platformName;

    /**
     * Every field must be present and not null.
     */
    public PlatformName(String platformName) {
        requireAllNonNull(platformName);
        this.platformName = platformName;
    }

    public String getValue() {
        return platformName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlatformName)) {
            return false;
        }

        PlatformName other = (PlatformName) obj;
        return other.platformName.equalsIgnoreCase(platformName);
    }

    @Override
    public String toString() {
        return platformName;
    }

    @Override
    public int hashCode() {
        return platformName.hashCode();
    }


}
