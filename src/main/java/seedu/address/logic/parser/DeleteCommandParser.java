package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.delete.DeletePersonCommand;
import seedu.address.logic.commands.delete.DeletePersonsSocialCommand;
import seedu.address.logic.commands.delete.DeletePersonsTagCommand;
import seedu.address.logic.commands.delete.DeleteTagOnlyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_SOCIAL_MEDIA);
        String preamble = argMultimap.getPreamble();
        if (preamble.isEmpty() && !argMultimap.arePrefixesPresent(PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        if (preamble.isEmpty() && argMultimap.arePrefixesPresent(PREFIX_TAG)) {
            Tag targetTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new DeleteTagOnlyCommand(targetTag);
        }

        Target target = ParserUtil.parseTarget(preamble);

        if (!argMultimap.arePrefixesPresent(PREFIX_TAG) && !argMultimap.arePrefixesPresent(PREFIX_SOCIAL_MEDIA)) {
            return new DeletePersonCommand(target);
        } else if (argMultimap.arePrefixesPresent(PREFIX_TAG)) {
            Tag targetTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new DeletePersonsTagCommand(target, targetTag);
        } else {
            SocialMedia targetSocial = ParserUtil.parseSocialMedia(argMultimap.getValue(PREFIX_SOCIAL_MEDIA).get());
            return new DeletePersonsSocialCommand(target, targetSocial);

        }
    }

}
