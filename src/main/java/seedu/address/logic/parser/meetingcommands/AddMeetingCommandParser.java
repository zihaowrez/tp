package seedu.address.logic.parser.meetingcommands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.add.AddTagOnlyCommand;
import seedu.address.logic.commands.meetingcommands.AddMeetingCommand;
import seedu.address.logic.commands.meetingcommands.AddTagToMeetingCommand;
import seedu.address.logic.commands.meetingcommands.MeetingTarget;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.meeting.Title;
import seedu.address.model.tag.Tag;

public class AddMeetingCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LINK,
                PREFIX_STARTTIME, PREFIX_DURATION, PREFIX_TAG);

        if (argMultimap.getPreamble().isEmpty() && arePrefixesPresent(argMultimap, PREFIX_TAG)
                && argMultimap.noOtherPrefixes(PREFIX_TAG)) {

            Tag newTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new AddTagOnlyCommand(newTag);
        }

        if (!argMultimap.getPreamble().isEmpty() && arePrefixesPresent(argMultimap, PREFIX_TAG)
                && argMultimap.noOtherPrefixes(PREFIX_TAG)) {

            MeetingTarget target = ParserUtil.parseMeetingTarget(argMultimap.getPreamble());
            Tag newTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new AddTagToMeetingCommand(target, newTag);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_LINK, PREFIX_STARTTIME, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        Title name = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_NAME).get());
        Link link = ParserUtil.parseLink(argMultimap.getValue(PREFIX_LINK).get());
        StartTime startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_STARTTIME).get());
        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Meeting meeting = new Meeting(name, link, startTime, duration, tagList);

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
