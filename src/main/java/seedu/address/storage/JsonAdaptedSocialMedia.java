package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.socialmedia.PlatformDescription;
import seedu.address.model.socialmedia.PlatformName;
import seedu.address.model.socialmedia.SocialMedia;

/**
 * Jackson-friendly version of {@link SocialMedia}.
 */
public class JsonAdaptedSocialMedia {
    private final String platformName;
    private final String platformDescription;

    /**
     * Constructs a {@code JsonAdaptedSocialMedia} with the given SocialMedia details.
     */
    @JsonCreator
    public JsonAdaptedSocialMedia(@JsonProperty("platformName") String platformName,
        @JsonProperty("platformDescription") String platformDescription) {

        this.platformName = platformName;
        this.platformDescription = platformDescription;
    }

    /**
     * Converts a given {@code SocialMedia} into this class for Jackson use.
     */
    public JsonAdaptedSocialMedia(SocialMedia source) {
        platformName = source.getPlatformName().getValue();
        platformDescription = source.getPlatformDescription().getValue();
    }

    public String getPlatformName() {
        return platformName;
    }

    public String getPlatformDescription() {
        return platformDescription;
    }

    /**
     * Converts this Jackson-friendly adapted social media object into the model's {@code SocialMedia} object.
     */
    public SocialMedia toModelType() {
        final PlatformName modelPlatformName = new PlatformName(platformName);
        final PlatformDescription modelPlatformDescription = new PlatformDescription(platformDescription);

        return new SocialMedia(modelPlatformName, modelPlatformDescription);
    }


}
