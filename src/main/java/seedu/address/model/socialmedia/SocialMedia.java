package seedu.address.model.socialmedia;

import java.util.Objects;

public class SocialMedia {
    public static final String MESSAGE_CONSTRAINTS = "Provide the name of the platform, "
            + "followed by the description associated with this platform, separated by a comma.\n"
            + "If there are commas or double quotes in the platform name, surround the name with double quotes.";
    public static final String TELEGRAM_URL = "https://t.me/";
    public final PlatformName platformName;
    public final PlatformDescription platformDescription;

    /**
     * Every field must be present and not null.
     */
    public SocialMedia(PlatformName platformName, PlatformDescription platformDescription) {
        assert platformName != null && platformDescription != null;
        this.platformName = platformName;
        this.platformDescription = platformDescription;
    }

    public PlatformName getPlatformName() {
        return platformName;
    }

    public Boolean isTelegram() {
        return (platformName.getValue().toLowerCase().equals("telegram"));
    }

    public PlatformDescription getPlatformDescription() {
        return platformDescription;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SocialMedia)) {
            return false;
        }

        SocialMedia otherSocialMedia = (SocialMedia) other;
        return platformName.equals(otherSocialMedia.platformName)
            && platformDescription.equals(otherSocialMedia.platformDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platformName, platformDescription);
    }

    @Override
    public String toString() {
        return platformName.toString() + ", " + platformDescription.toString();
    }

}
