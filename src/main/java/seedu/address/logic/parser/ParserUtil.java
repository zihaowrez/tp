package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.MeetingName;
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

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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

    public static MeetingName parseMeetingName(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        return new MeetingName(trimmedName);
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
        List<String> socialMediaDetails = Arrays.asList(socialMedia.split(","));
        if (socialMediaDetails.size() != 2) {
            throw new ParseException(SocialMedia.MESSAGE_CONSTRAINTS);
        }

        socialMediaDetails.forEach(detail -> detail.trim());
        PlatformName platformName = parsePlatformName(socialMediaDetails.get(0));
        PlatformDescription platformDescription = parsePlatformDescription(socialMediaDetails.get(1));

        return new SocialMedia(platformName, platformDescription);
    }

    /**
     * Parses {@code Collection<String> socialMedias} into a {@code Set<SocialMedia>}.
     */
    public static Set<SocialMedia> parseSocialMedias(Collection<String> socialMedias) throws ParseException {
        requireNonNull(socialMedias);
        final Set<SocialMedia> socialMediaSet = new HashSet<>();
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
     * The runtime type of {@code Object} is guaranteed to be a {@code Name} or {@code Index}
     * @param args The index or name to be parsed.
     * @throws ParseException if the given Index/Name is invalid
     */
    public static Object parseTarget(String args) throws ParseException {

        if (StringUtil.isInt(args)) {
            return ParserUtil.parseIndex(args);
        } else {
            return ParserUtil.parseName(args);
        }
    }

    public static DateTime parseDateTime(String startDateTime, String endDateTime) throws ParseException {
        Pattern DATE_TIME_FORMAT = Pattern.compile("(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12]\\d|3[01])\\s+([01]\\d|2[0-3])([0-5]?\\d)");
        Matcher matcherStart = DATE_TIME_FORMAT.matcher(startDateTime.trim());
        Matcher matcherEnd = DATE_TIME_FORMAT.matcher(endDateTime.trim());

        matcherStart.find();
        LocalDateTime localStartDateTime = LocalDateTime.of(Integer.parseInt(matcherStart.group(1)), Integer.parseInt(matcherStart.group(2)),
                Integer.parseInt(matcherStart.group(3)), Integer.parseInt(matcherStart.group(4)), Integer.parseInt(matcherStart.group(5)));

        matcherEnd.find();
        LocalDateTime localEndDateTime = LocalDateTime.of(Integer.parseInt(matcherEnd.group(1)), Integer.parseInt(matcherEnd.group(2)),
                Integer.parseInt(matcherEnd.group(3)), Integer.parseInt(matcherEnd.group(4)), Integer.parseInt(matcherEnd.group(5)));

        if (localEndDateTime.isBefore(localStartDateTime)) {
            throw new ParseException(DateTime.ENDDATETIME_AFTER_STARTDATETIME);
        }
        return new DateTime(localStartDateTime, localEndDateTime);
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        Pattern DATE_TIME_FORMAT = Pattern.compile("(\\d{4})-(0?[1-9]|1[0-2])-(0?[1-9]|[12]\\d|3[01])\\s+([01]\\d|2[0-3])([0-5]?\\d)");
        Matcher matcherDateTime = DATE_TIME_FORMAT.matcher(dateTime.trim());

        matcherDateTime.find();
        return LocalDateTime.of(Integer.parseInt(matcherDateTime.group(1)), Integer.parseInt(matcherDateTime.group(2)),
                Integer.parseInt(matcherDateTime.group(3)), Integer.parseInt(matcherDateTime.group(4)), Integer.parseInt(matcherDateTime.group(5)));
    }

    public static Link parseLink(String link) throws ParseException {
        requireNonNull(link);
        String trimmedLink = link.trim();
        if (!Link.isValidLink(link)) {
            throw new ParseException(Link.MESSAGE_CONSTRAINTS);
        }
        return new Link(trimmedLink);
    }
}
