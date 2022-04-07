package seedu.address.logic.parser.meetings;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.meetingcommands.AddMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.meetingcommands.AddMeetingCommandParser;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.StartTime;
import seedu.address.testutil.MeetingBuilder;

public class AddMeetingCommandParserTest {
    private AddMeetingCommandParser parser = new AddMeetingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {

        Meeting expectedMeeting = new MeetingBuilder().withTitle(VALID_TITLE_CS2103)
                .withLink(VALID_LINK_ZOOM).withStartTime(VALID_STARTTIME_CS2103)
                .withDuration(VALID_DURATION_INT_60).withTags(VALID_TAG_FRIEND).build();

        // multiple links - last link accepted
        assertParseSuccess(parser, PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + VALID_LINK_TEAMS + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS2103 + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                new AddMeetingCommand(expectedMeeting));


        // multiple startTime - last startTime accepted
        assertParseSuccess(parser, PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_PROJECT + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS2103 + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                new AddMeetingCommand(expectedMeeting));

        // multiple tags - all accepted
        expectedMeeting = new MeetingBuilder().withTitle(VALID_TITLE_CS2103)
                .withLink(VALID_LINK_ZOOM).withStartTime(VALID_STARTTIME_CS2103)
                .withDuration(VALID_DURATION_INT_60).withTags(VALID_TAG_FRIEND, VALID_TAG_PROJECT).build();
        assertParseSuccess(parser, PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS2103 + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND + " "
                + PREFIX_TAG + VALID_TAG_PROJECT,
                new AddMeetingCommand(expectedMeeting));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Meeting expectedMeeting = new MeetingBuilder().withTitle(VALID_TITLE_CS2103)
                .withLink(VALID_LINK_ZOOM).withStartTime(VALID_STARTTIME_CS2103).withTags().build();
        assertParseSuccess(parser, VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS2103 + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " ",
                new AddMeetingCommand(expectedMeeting));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS2103 + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                expectedMessage);

        // missing link prefix
        assertParseFailure(parser, PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS2103 + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                expectedMessage);

        // missing startTime prefix
        assertParseFailure(parser, PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + VALID_STARTTIME_CS2103 + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                expectedMessage);

        // missing duration prefix
        assertParseFailure(parser, PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS2103 + " "
                + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_CS2103 + " "
                + VALID_LINK_ZOOM + " "
                + VALID_STARTTIME_CS2103 + " "
                + VALID_DURATION_STRING_60 + " "
                + VALID_TAG_FRIEND,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid link
        assertParseFailure(parser, PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + INVALID_LINK + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS2103 + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                Link.MESSAGE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + INVALID_STARTTIME + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                StartTime.MESSAGE_CONSTRAINTS);

        // invalid duration
        assertParseFailure(parser, PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS2103 + " "
                + PREFIX_DURATION + INVALID_DURATION_STRING + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                Duration.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + INVALID_LINK + " "
                + PREFIX_STARTTIME + INVALID_STARTTIME + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                Link.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " "
                + PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + INVALID_LINK + " "
                + PREFIX_STARTTIME + INVALID_STARTTIME + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
    }
}
