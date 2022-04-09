package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.add.AddPersonCommand;
import seedu.address.logic.commands.add.AddSocialsToPersonCommand;
import seedu.address.logic.commands.add.AddTagOnlyCommand;
import seedu.address.logic.commands.add.AddTagToPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_SOCIAL_MEDIA, PREFIX_TAG);

        if (argMultimap.getPreamble().isEmpty() && arePrefixesPresent(argMultimap, PREFIX_TAG)
                && argMultimap.noOtherPrefixes(PREFIX_TAG)) {

            Tag newTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new AddTagOnlyCommand(newTag);
        }

        if (!argMultimap.getPreamble().isEmpty() && arePrefixesPresent(argMultimap, PREFIX_TAG)
                && argMultimap.noOtherPrefixes(PREFIX_TAG)) {

            Target target = ParserUtil.parseTarget(argMultimap.getPreamble());
            Tag newTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new AddTagToPersonCommand(target, newTag);
        }

        if (!argMultimap.getPreamble().isEmpty() && arePrefixesPresent(argMultimap, PREFIX_SOCIAL_MEDIA)
                && argMultimap.noOtherPrefixes(PREFIX_SOCIAL_MEDIA)) {

            Target target = ParserUtil.parseTarget(argMultimap.getPreamble());
            SocialMedia newSocialMedia = ParserUtil.parseSocialMedia(argMultimap.getValue(PREFIX_SOCIAL_MEDIA).get());
            return new AddSocialsToPersonCommand(target, newSocialMedia);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).orElse("-"));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse("-"));
        List<SocialMedia> socialMedias = ParserUtil.parseSocialMedias(argMultimap.getAllValues(PREFIX_SOCIAL_MEDIA));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, socialMedias, tagList);

        return new AddPersonCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
