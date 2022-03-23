package seedu.address.logic.parser.meetingcommands;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.meetingcommands.AddMeetingCommand;
import seedu.address.logic.commands.meetingcommands.AddTagToMeetingCommand;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class AddMeetingCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LINK,
                PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty() && arePrefixesPresent(argMultimap, PREFIX_TAG)
                && argMultimap.noOtherPrefixes(PREFIX_TAG)) {

            Object target = ParserUtil.parseTarget(argMultimap.getPreamble());
            Tag newTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new AddTagToMeetingCommand(target, newTag);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LINK, PREFIX_START_DATETIME, PREFIX_END_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        MeetingName name = ParserUtil.parseMeetingName(argMultimap.getValue(PREFIX_NAME).get());
        Link link = ParserUtil.parseLink(argMultimap.getValue(PREFIX_LINK).get());
        DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATETIME).get(),
                argMultimap.getValue(PREFIX_END_DATETIME).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Meeting meeting = new Meeting(name, link, dateTime, tagList);

        return new AddMeetingCommand(meeting);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
