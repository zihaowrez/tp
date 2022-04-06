package seedu.address.logic.parser.meetings;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DURATION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LINK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LINK_TEAMS;
import static seedu.address.logic.commands.CommandTestUtil.LINK_ZOOM;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_NAME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_STRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_MEETING;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.logic.parser.meetingcommands.EditMeetingCommandParser;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditMeetingDescriptorBuilder;

public class EditMeetingCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_EDIT_COMMAND_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMeetingCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMeetingCommand.MESSAGE_USAGE);

    private EditMeetingCommandParser parser = new EditMeetingCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //TODO: can now specify by name so obsolete?
        // no index specified
        // assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        //TODO:
        // no field specified
        //assertParseFailure(parser, "1", EditPersonCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_EDIT_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_EDIT_COMMAND_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_EDIT_COMMAND_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 g/ string", MESSAGE_INVALID_EDIT_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + MEETING_NAME_CS2103
                + START_TIME + DURATION + INVALID_LINK_DESC, Link.MESSAGE_CONSTRAINTS); // invalid link
        assertParseFailure(parser, "1" + MEETING_NAME_CS2103
                + LINK_ZOOM + DURATION + INVALID_START_TIME_DESC, StartTime.MESSAGE_CONSTRAINTS); // invalid start time
        assertParseFailure(parser, "1" + MEETING_NAME_CS2103
                + LINK_ZOOM + START_TIME + INVALID_DURATION_DESC, Duration.MESSAGE_CONSTRAINTS); // invalid duration


        // invalid link followed by valid start time
        assertParseFailure(parser, "1" + INVALID_LINK_DESC
                + VALID_START_TIME_STRING, Link.MESSAGE_CONSTRAINTS);

        // valid link followed by invalid link. The test case for invalid link followed by valid link
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + LINK_ZOOM + INVALID_LINK_DESC, Link.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Meeting} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND
                + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MEETING;
        String userInput = targetIndex.getOneBased() + LINK_ZOOM + TAG_DESC_HUSBAND
                + START_TIME
                + DURATION + MEETING_NAME_CS2103 + TAG_DESC_FRIEND;

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withName(VALID_MEETING_NAME)
                .withLink(VALID_LINK).withStartTime(VALID_START_TIME)
                .withDuration(VALID_DURATION)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_MEETING;
        String userInput = targetIndex.getOneBased() + LINK_ZOOM + START_TIME;

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withLink(VALID_LINK)
                .withStartTime(VALID_START_TIME).build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_MEETING;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // link
        userInput = targetIndex.getOneBased() + LINK_ZOOM;
        descriptor = new EditMeetingDescriptorBuilder().withLink(VALID_LINK).build();
        expectedCommand = new EditMeetingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start time
        userInput = targetIndex.getOneBased() + START_TIME;
        descriptor = new EditMeetingDescriptorBuilder().withStartTime(VALID_START_TIME).build();
        expectedCommand = new EditMeetingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditMeetingDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditMeetingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //TODO // socials
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MEETING;
        String userInput = targetIndex.getOneBased() + LINK_TEAMS + LINK_ZOOM + START_TIME
                + DURATION + MEETING_NAME_CS2103 + TAG_DESC_FRIEND;

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                .withName(VALID_MEETING_NAME)
                .withLink(VALID_LINK)
                .withStartTime(VALID_START_TIME)
                .withDuration(VALID_DURATION)
                .withTags(VALID_TAG_FRIEND)
                .build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
