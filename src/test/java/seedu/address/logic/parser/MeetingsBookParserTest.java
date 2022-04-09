package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.meetingcommands.AddMeetingCommand;
import seedu.address.logic.commands.meetingcommands.DeleteMeetingCommand;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.logic.commands.meetingcommands.FindMeetingCommand;
import seedu.address.logic.commands.meetingcommands.ListMeetingCommand;
import seedu.address.logic.commands.meetingcommands.MeetingTarget;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingContainsKeywordsPredicate;
import seedu.address.model.meeting.MeetingKeywordMatchnessComparator;
import seedu.address.testutil.EditMeetingDescriptorBuilder;
import seedu.address.testutil.MeetingBuilder;
import seedu.address.testutil.MeetingUtil;
import seedu.address.ui.CommandBox;

public class MeetingsBookParserTest {

    private final MeetingsBookParser parser = new MeetingsBookParser();

    private CommandBox commandBox;

    @Test
    public void parseCommand_add() throws Exception {
        Meeting meeting = new MeetingBuilder().build();

        AddMeetingCommand command =
                (AddMeetingCommand) parser.parseCommand(MeetingUtil.getAddMeetingCommand(meeting), commandBox);
        assertEquals(new AddMeetingCommand(meeting), command);
    }

    @Test
    public void parseCommand_deleteIndex() throws Exception {
        DeleteMeetingCommand command = (DeleteMeetingCommand) parser.parseCommand(
                DeleteMeetingCommand.COMMAND_WORD + " " + INDEX_FIRST_MEETING.getOneBased(), commandBox);
        assertEquals(new DeleteMeetingCommand(new MeetingTarget(INDEX_FIRST_MEETING)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Meeting meeting = new MeetingBuilder().build();
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(meeting).build();
        EditMeetingCommand command = (EditMeetingCommand) parser.parseCommand(EditMeetingCommand.COMMAND_WORD + " "
                + INDEX_FIRST_MEETING.getOneBased() + " "
                + MeetingUtil.getEditMeetingDescriptorDetails(descriptor), commandBox);
        assertEquals(new EditMeetingCommand(new MeetingTarget(INDEX_FIRST_PERSON), descriptor), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindMeetingCommand command = (FindMeetingCommand) parser.parseCommand(
                FindMeetingCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")), commandBox);
        assertEquals(new FindMeetingCommand(new MeetingContainsKeywordsPredicate(keywords),
                new MeetingKeywordMatchnessComparator(keywords)), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListMeetingCommand.COMMAND_WORD, commandBox) instanceof ListMeetingCommand);
        assertTrue(parser.parseCommand(ListMeetingCommand.COMMAND_WORD + " 3",
                commandBox) instanceof ListMeetingCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", commandBox));
    }
}
