package seedu.address.logic.parser.meetingcommands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.delete.DeleteTagOnlyCommand;
import seedu.address.logic.commands.meetingcommands.DeleteMeetingCommand;
import seedu.address.logic.commands.meetingcommands.DeleteMeetingsTagCommand;
import seedu.address.logic.commands.meetingcommands.MeetingTarget;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteMeetingCommand object
 */
public class DeleteMeetingCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        String preamble = argMultimap.getPreamble();
        if (preamble.isEmpty() && !argMultimap.arePrefixesPresent(PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        if (preamble.isEmpty() && argMultimap.arePrefixesPresent(PREFIX_TAG)) {
            Tag targetTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new DeleteTagOnlyCommand(targetTag);
        }

        MeetingTarget target = ParserUtil.parseMeetingTarget(preamble);

        if (!argMultimap.arePrefixesPresent(PREFIX_TAG)) {
            return new DeleteMeetingCommand(target) {
            };
        } else {
            Tag targetTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new DeleteMeetingsTagCommand(target, targetTag);
        }
    }

}
