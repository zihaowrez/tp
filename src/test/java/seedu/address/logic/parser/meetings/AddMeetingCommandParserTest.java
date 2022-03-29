package seedu.address.logic.parser.meetings;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalMeetings.CS2103_MEETING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.meetingcommands.AddMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.meetingcommands.AddMeetingCommandParser;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.MeetingBuilder;

public class AddMeetingCommandParserTest {
    private AddMeetingCommandParser parser = new AddMeetingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Meeting expectedMeeting = new MeetingBuilder().withTitle(VALID_MEETING_NAME).withLink(VALID_LINK).withStartTime(VALID_START_TIME_STRING)
                .withDuration(VALID_DURATION_INT).withTags(VALID_TAG_FRIEND).build();

        // multiple links - last link accepted
        assertParseSuccess(parser,MEETING_NAME_CS2103 + LINK_ZOOM + START_TIME + DURATION
                + TAG_DESC_FRIEND, new AddMeetingCommand(expectedMeeting));


        // multiple end dateTime - last end dateTime accepted
        assertParseSuccess(parser, MEETING_NAME_CS2103 + LINK_ZOOM + START_TIME + DURATION
                + TAG_DESC_FRIEND + TAG_DESC_FRIEND, new AddMeetingCommand(expectedMeeting));

        // multiple tags - all accepted
        Meeting expectedMeetingMultipleTags = new MeetingBuilder().withTitle(VALID_MEETING_NAME).withLink(VALID_LINK).withStartTime(VALID_START_TIME_STRING)
                .withDuration(VALID_DURATION_INT).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, MEETING_NAME_CS2103 + LINK_ZOOM + START_TIME + DURATION
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddMeetingCommand(expectedMeetingMultipleTags));

    }

    /*
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Meeting expectedMeeting = new MeetingBuilder(CS2103_MEETING).withLink(VALID_LINK)
                .withDateTime(VALID_START_DATETIME, VALID_END_DATETIME).withTags().build();
        assertParseSuccess(parser, MEETING_NAME_CS2103 + LINK_ZOOM + START_DATE_TIME + END_DATE_TIME,
                new AddMeetingCommand(expectedMeeting));
    }
     */

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_MEETING_NAME + LINK_ZOOM + START_TIME + DURATION,
                expectedMessage);

        // missing link prefix
        assertParseFailure(parser, MEETING_NAME_CS2103 + VALID_LINK + START_TIME + DURATION,
                expectedMessage);

        // missing start dateTime prefix
        assertParseFailure(parser,  MEETING_NAME_CS2103 + LINK_ZOOM + VALID_START_TIME_STRING + DURATION,
                expectedMessage);

        // missing duration prefix
        assertParseFailure(parser,  VALID_MEETING_NAME + LINK_ZOOM + START_TIME + VALID_DURATION_STRING,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MEETING_NAME + VALID_LINK + VALID_START_DATETIME + VALID_DURATION_STRING,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid link
        assertParseFailure(parser, MEETING_NAME_CS2103 + INVALID_LINK_DESC + START_TIME + DURATION
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Link.MESSAGE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, MEETING_NAME_CS2103 + LINK_ZOOM + INVALID_START_TIME_DESC + DURATION
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, StartTime.MESSAGE_CONSTRAINTS);

        // invalid duration
        assertParseFailure(parser, MEETING_NAME_CS2103 + LINK_ZOOM + START_TIME + INVALID_DURATION_DESC
                + VALID_TAG_FRIEND, Duration.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, MEETING_NAME_CS2103 + INVALID_LINK_DESC + INVALID_START_TIME_DESC// + INVALID_ADDRESS_DESC,
                        + DURATION + VALID_TAG_FRIEND,
                Link.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MEETING_NAME_CS2103 + LINK_ZOOM + START_TIME
                        + DURATION + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
    }
}
