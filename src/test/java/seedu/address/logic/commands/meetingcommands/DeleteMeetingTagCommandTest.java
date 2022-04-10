package seedu.address.logic.commands.meetingcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showMeetingAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalMeetings.CS2103_MEETING;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.CS2103;
import static seedu.address.testutil.TypicalTags.ENEMIES;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.Title;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteMeetingTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        showMeetingAtIndex(model, INDEX_FIRST_MEETING);

        Meeting meetingToDelete = model.getSortedAndFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased());
        new AddTagToMeetingCommand(new MeetingTarget(meetingToDelete.getTitle()), ENEMIES).execute(model);

        CommandResult commandResult =
                new DeleteMeetingsTagCommand(new MeetingTarget(meetingToDelete.getTitle()), CS2103).execute(model);

        String expectedMessage = String.format(DeleteMeetingsTagCommand.MESSAGE_DELETE_TAG_SUCCESS, CS2103);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validNameUnfilteredList_success() throws CommandException {
        Meeting meetingToDelete = CS2103_MEETING;
        new AddTagToMeetingCommand(new MeetingTarget(meetingToDelete.getTitle()), ENEMIES).execute(model);

        CommandResult commandResult =
                new DeleteMeetingsTagCommand(new MeetingTarget(meetingToDelete.getTitle()), CS2103).execute(model);

        String expectedMessage = String.format(DeleteMeetingsTagCommand.MESSAGE_DELETE_TAG_SUCCESS, CS2103);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }


    @Test
    public void execute_incompleteNameUnfilteredList_throwsCommandException() throws CommandException {
        Meeting meetingToDelete = CS2103_MEETING;
        Title targetTitle = new Title("CS2103 Meetin");
        new AddTagToMeetingCommand(new MeetingTarget(meetingToDelete.getTitle()), ENEMIES).execute(model);
        DeleteCommand deleteMeetingCommand = new DeleteMeetingsTagCommand(new MeetingTarget(targetTitle), FRIENDS);

        String expectedMessage = String.format(MeetingTarget.MESSAGE_MEETING_NOT_EXIST, targetTitle);

        assertCommandFailure(deleteMeetingCommand, model, expectedMessage);
    }

    @Test
    public void execute_nameNotExistUnfilteredList_throwsCommandException() throws CommandException {
        Meeting meetingToDelete = CS2103_MEETING;
        Title targetTitle = new Title("CS3230");
        new AddTagToMeetingCommand(new MeetingTarget(meetingToDelete.getTitle()), ENEMIES).execute(model);
        DeleteCommand deleteMeetingCommand = new DeleteMeetingsTagCommand(new MeetingTarget(targetTitle), FRIENDS);

        String expectedMessage = String.format(MeetingTarget.MESSAGE_MEETING_NOT_EXIST, targetTitle);

        assertCommandFailure(deleteMeetingCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedAndFilteredMeetingList().size() + 1);
        DeleteCommand deleteMeetingCommand = new DeleteMeetingsTagCommand(new MeetingTarget(outOfBoundIndex), FRIENDS);

        assertCommandFailure(deleteMeetingCommand, model, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        showMeetingAtIndex(model, INDEX_FIRST_MEETING);

        Meeting meetingToDelete = model.getSortedAndFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased());
        new AddTagToMeetingCommand(new MeetingTarget(meetingToDelete.getTitle()), ENEMIES).execute(model);

        CommandResult commandResult =
                new DeleteMeetingsTagCommand(new MeetingTarget(meetingToDelete.getTitle()), CS2103).execute(model);

        String expectedMessage = String.format(DeleteMeetingsTagCommand.MESSAGE_DELETE_TAG_SUCCESS, CS2103);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMeetingAtIndex(model, INDEX_FIRST_MEETING);

        Index outOfBoundIndex = INDEX_SECOND_MEETING;
        // ensures that outOfBoundIndex is still in bounds of meeting book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMeetingsBook().getMeetingList().size());

        DeleteCommand deleteCommand = new DeleteMeetingsTagCommand(new MeetingTarget(outOfBoundIndex), FRIENDS);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand =
                new DeleteMeetingsTagCommand(new MeetingTarget(INDEX_FIRST_MEETING), FRIENDS);
        DeleteCommand deleteSecondCommand =
                new DeleteMeetingsTagCommand(new MeetingTarget(INDEX_SECOND_MEETING), FRIENDS);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy =
                new DeleteMeetingsTagCommand(new MeetingTarget(INDEX_FIRST_MEETING), FRIENDS);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different meeting -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
