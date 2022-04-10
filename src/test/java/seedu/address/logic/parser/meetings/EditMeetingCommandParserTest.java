package seedu.address.logic.parser.meetings;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION_STRING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LINK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_INT_50;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_INT_60;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_STRING_50;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_STRING_60;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_TEAMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_ZOOM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_MEETING;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.logic.commands.meetingcommands.MeetingTarget;
import seedu.address.logic.parser.meetingcommands.EditMeetingCommandParser;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditMeetingDescriptorBuilder;

public class EditMeetingCommandParserTest {

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
        //assertParseFailure(parser, "1", EditMeetingCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_EDIT_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + PREFIX_TITLE + VALID_TITLE_CS2103,
                MESSAGE_INVALID_EDIT_COMMAND_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string ", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 g/string ", MESSAGE_INVALID_EDIT_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " " + INDEX_FIRST_MEETING.getOneBased() + " "
                + PREFIX_LINK + INVALID_LINK,
                Link.MESSAGE_CONSTRAINTS); // invalid link
        assertParseFailure(parser, " " + INDEX_FIRST_MEETING.getOneBased() + " "
                + PREFIX_STARTTIME + INVALID_STARTTIME,
                StartTime.MESSAGE_CONSTRAINTS); // invalid start time
        assertParseFailure(parser, " " + INDEX_FIRST_MEETING.getOneBased() + " "
                + PREFIX_DURATION + INVALID_DURATION_STRING,
                Duration.MESSAGE_CONSTRAINTS); // invalid duration

        // invalid link followed by valid start time
        assertParseFailure(parser, " " + INDEX_FIRST_MEETING.getOneBased() + " "
                + PREFIX_LINK + INVALID_LINK + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_PROJECT,
                Link.MESSAGE_CONSTRAINTS);

        // valid link followed by invalid link. The test case for invalid link followed by valid link
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, " " + INDEX_FIRST_MEETING.getOneBased() + " "
                + PREFIX_LINK + VALID_LINK_TEAMS + " "
                + PREFIX_LINK + INVALID_LINK,
                Link.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Meeting} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, " " + INDEX_FIRST_MEETING.getOneBased() + " "
                + PREFIX_TAG + " "
                + PREFIX_TAG + VALID_TAG_PROJECT,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + INDEX_FIRST_MEETING.getOneBased() + " "
                + PREFIX_TAG + VALID_TAG_PROJECT + " "
                + PREFIX_TAG,
                Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_MEETING;
        String userInput = targetIndex.getOneBased() + " "
                + PREFIX_TITLE + VALID_TITLE_CS3230 + " "
                + PREFIX_LINK + VALID_LINK_TEAMS + " "
                + PREFIX_TAG + VALID_TAG_PROJECT + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS3230 + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_50;

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withTitle(VALID_TITLE_CS3230)
                .withLink(VALID_LINK_TEAMS).withStartTime(VALID_STARTTIME_CS3230)
                .withDuration(VALID_DURATION_INT_50)
                .withTags(VALID_TAG_PROJECT)
                .build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(new MeetingTarget(targetIndex), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MEETING;
        String userInput = targetIndex.getOneBased() + " "
                + PREFIX_LINK + VALID_LINK_TEAMS + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_PROJECT;

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withLink(VALID_LINK_TEAMS)
                .withStartTime(VALID_STARTTIME_PROJECT).build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(new MeetingTarget(targetIndex), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = INDEX_THIRD_MEETING;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_TITLE + VALID_TITLE_CS3230;
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withTitle(VALID_TITLE_CS3230).build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(new MeetingTarget(targetIndex), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // link
        userInput = targetIndex.getOneBased() + " " + PREFIX_LINK + VALID_LINK_ZOOM;
        descriptor = new EditMeetingDescriptorBuilder().withLink(VALID_LINK_ZOOM).build();
        expectedCommand = new EditMeetingCommand(new MeetingTarget(targetIndex), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start time
        userInput = targetIndex.getOneBased() + " " + PREFIX_STARTTIME + VALID_STARTTIME_CS3230;
        descriptor = new EditMeetingDescriptorBuilder().withStartTime(VALID_STARTTIME_CS3230).build();
        expectedCommand = new EditMeetingCommand(new MeetingTarget(targetIndex), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + " " + PREFIX_TAG + VALID_TAG_FRIEND;
        descriptor = new EditMeetingDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditMeetingCommand(new MeetingTarget(targetIndex), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MEETING;
        String userInput = targetIndex.getOneBased() + " "
                + PREFIX_TITLE + VALID_TITLE_PROJECT + " "
                + PREFIX_LINK + VALID_LINK_TEAMS + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_PROJECT + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60 + " "
                + PREFIX_TAG + VALID_TAG_FRIEND;

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                .withTitle(VALID_TITLE_PROJECT)
                .withLink(VALID_LINK_ZOOM)
                .withStartTime(VALID_STARTTIME_PROJECT)
                .withDuration(VALID_DURATION_INT_60)
                .withTags(VALID_TAG_FRIEND)
                .build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(new MeetingTarget(targetIndex), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
