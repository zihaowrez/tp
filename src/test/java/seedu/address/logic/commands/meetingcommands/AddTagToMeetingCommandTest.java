package seedu.address.logic.commands.meetingcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.testutil.MeetingBuilder;

class AddTagToMeetingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());

    @Test
    public void execute_updatedMeetingAcceptedByModel_addSuccessful() throws Exception {

        Meeting validMeeting = new MeetingBuilder().build();

        model.addMeeting(validMeeting);
        CommandResult commandResult = new AddTagToMeetingCommand(new MeetingTarget(validMeeting.getTitle()), FRIENDS)
                .execute(model);

        assertEquals(String.format(AddTagToMeetingCommand.MESSAGE_ADD_NEW_TAG_SUCCESS, FRIENDS, validMeeting),
                commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_duplicateTag_throwsCommandException() throws CommandException {
        Meeting validMeeting = new MeetingBuilder().build();
        model.addMeeting(validMeeting);

        AddCommand addMeetingCommand = new AddTagToMeetingCommand(new MeetingTarget(validMeeting.getTitle()), FRIENDS);

        CommandResult commandResult = new AddTagToMeetingCommand(new MeetingTarget(validMeeting.getTitle()), FRIENDS)
                .execute(model);


        assertThrows(CommandException.class,
                String.format(AddTagToMeetingCommand.MESSAGE_TAG_ALREADY_EXISTS, FRIENDS,
                        validMeeting.getTitle()), () -> addMeetingCommand.execute(model));
    }

    @Test
    void testEquals() {
        Meeting cS2103 = new MeetingBuilder().withTitle("CS2103 Meeting").build();
        Meeting cS3230 = new MeetingBuilder().withTitle("CS3230 Meeting").build();
        AddCommand addCS2103Command = new AddTagToMeetingCommand(new MeetingTarget(cS2103.getTitle()), FRIENDS);
        AddCommand addCS3230Command = new AddTagToMeetingCommand(new MeetingTarget(cS3230.getTitle()), FRIENDS);

        // same object -> returns true
        assertTrue(addCS2103Command.equals(addCS2103Command));

        // different types -> returns false
        assertFalse(addCS2103Command.equals(1));

        // null -> returns false
        assertFalse(addCS2103Command.equals(null));

        // different meeting -> returns false
        assertFalse(addCS2103Command.equals(addCS3230Command));
    }


}
