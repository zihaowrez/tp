package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.meetingcommands.MeetingTarget;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.meeting.Title;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.socialmedia.PlatformDescription;
import seedu.address.model.socialmedia.PlatformName;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX =
        "Index provided is invalid. It has a valid range of 1 to 2147483647 inclusive.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String Title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        return new Title(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String socialMedia} into a {@code SocialMedia}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static SocialMedia parseSocialMedia(String socialMedia) throws ParseException {
        requireNonNull(socialMedia);
        String trimmedString = socialMedia.trim();
        int firstIndexOfQuotedToken = trimmedString.indexOf("\"");
        int lastIndexOfQuotedToken = trimmedString.lastIndexOf("\"");
        SocialMedia sm;

        if (firstIndexOfQuotedToken != -1 && firstIndexOfQuotedToken != lastIndexOfQuotedToken
                && firstIndexOfQuotedToken == 0) {
            sm = parseQuotedSocialMedia(trimmedString, firstIndexOfQuotedToken, lastIndexOfQuotedToken);
        } else {
            sm = parseUnquotedSocialMedia(trimmedString);
        }

        return sm;
    }


    private static SocialMedia parseUnquotedSocialMedia(String unquotedSocialMedia) throws ParseException {

        List<String> socialMediaDetails = Arrays.asList(unquotedSocialMedia.split(",", 2));

        if (socialMediaDetails.size() != 2) {
            throw new ParseException(SocialMedia.MESSAGE_CONSTRAINTS);
        }

        socialMediaDetails.forEach(detail -> detail.trim());
        PlatformName platformName = parsePlatformName(socialMediaDetails.get(0));
        PlatformDescription platformDescription = parsePlatformDescription(socialMediaDetails.get(1));

        return new SocialMedia(platformName, platformDescription);
    }

    private static SocialMedia parseQuotedSocialMedia(String quotedSocialMedia,
            int firstIndexOfQuote, int lastIndexOfQuote) throws ParseException {

        String platformNameStr = quotedSocialMedia.substring(firstIndexOfQuote + 1, lastIndexOfQuote);
        String remainder = quotedSocialMedia.substring(lastIndexOfQuote + 1).trim();
        List<String> socialMediaDetails = Arrays.asList(remainder.split("," , 2));

        if (socialMediaDetails.size() != 2) {
            throw new ParseException(SocialMedia.MESSAGE_CONSTRAINTS);
        }

        if (socialMediaDetails.get(0).trim().length() != 0) {
            throw new ParseException(SocialMedia.MESSAGE_CONSTRAINTS);
        }

        PlatformName platformName = parsePlatformName(platformNameStr);
        PlatformDescription platformDescription = parsePlatformDescription(socialMediaDetails.get(1).trim());
        return new SocialMedia(platformName, platformDescription);
    }

    /**
     * Parses {@code Collection<String> socialMedias} into a {@code Set<SocialMedia>}.
     */
    public static List<SocialMedia> parseSocialMedias(Collection<String> socialMedias) throws ParseException {
        requireNonNull(socialMedias);
        final List<SocialMedia> socialMediaSet = new ArrayList<>();
        for (String socialMedia : socialMedias) {
            socialMediaSet.add(parseSocialMedia(socialMedia));
        }
        return socialMediaSet;
    }

    /**
     * Parses {@code String platformName} into a {@code PlatformName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code platformName} is invalid.
     */
    public static PlatformName parsePlatformName(String platformName) throws ParseException {
        requireNonNull(platformName);
        String trimmedPlatformName = platformName.trim();

        return new PlatformName(trimmedPlatformName);
    }

    /**
     * Parses {@code String platformDescription} into a {@code PlatformDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code platformDescription} is invalid.
     */
    public static PlatformDescription parsePlatformDescription(String platformDescription) throws ParseException {
        requireNonNull(platformDescription);
        String trimmedPlatformName = platformDescription.trim();

        return new PlatformDescription(trimmedPlatformName);
    }

    /**
     * Parses {@code String args} into a {@code Object target}.
     * A valid target is either a valid Name or valid Index
     *
     * @param args The index or name to be parsed.
     * @throws ParseException if the given Index/Name is invalid
     */
    public static Target parseTarget(String args) throws ParseException {

        Target target = new Target(args);
        return target;
    }

    /**
     * Parses {@code String args} into a {@code Object target}.
     * A valid target is either a valid Title or valid Index
     * @param args The index or title to be parsed
     *
     * @throws ParseException if the given Index/Title is invalid
     */
    public static MeetingTarget parseMeetingTarget(String args) throws ParseException {

        MeetingTarget target = new MeetingTarget(args);
        return target;
    }

    /**
     * Parses a {@code String Link} into a {@code Link}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code link} is invalid.
     */
    public static Link parseLink(String link) throws ParseException {
        requireNonNull(link);
        String trimmedLink = link.trim();
        if (!Link.isValidLink(trimmedLink)) {
            throw new ParseException(Link.MESSAGE_CONSTRAINTS);
        }
        return new Link(trimmedLink);
    }


    /**
     * Parses a {@code String startTime} into a {@code StartTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code startTime} is invalid.
     */
    public static StartTime parseStartTime(String startTime) throws ParseException {
        requireNonNull(startTime);
        String trimmedStartTime = startTime.trim();
        if (!StartTime.isValidStartTime(trimmedStartTime)) {
            throw new ParseException(StartTime.MESSAGE_CONSTRAINTS);
        }
        return new StartTime(trimmedStartTime);
    }

    /**
     * Parses a {@code String duration} into a {@code Duration}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code duration} is invalid.
     */
    public static Duration parseDuration(String duration) throws ParseException {
        requireNonNull(duration);
        int mins;
        try {
            mins = Integer.parseInt(duration);
        } catch (NumberFormatException ite) {
            throw new ParseException(Duration.MESSAGE_CONSTRAINTS);
        }
        if (!Duration.isValidDuration(mins)) {
            throw new ParseException(Duration.MESSAGE_CONSTRAINTS);
        }
        return new Duration(mins);
    }

}
