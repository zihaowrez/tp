package seedu.address.logic.commands.meetingcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalMeetings.CS2103_MEETING;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.Title;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteMeetingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Meeting meetingToDelete = model.getSortedAndFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased());
        DeleteCommand deleteCommand = new DeleteMeetingCommand(new MeetingTarget(INDEX_FIRST_MEETING));

        String expectedMessage = String.format(DeleteMeetingCommand.MESSAGE_DELETE_MEETING_SUCCESS, meetingToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getMeetingsBook(), new UserPrefs());
        expectedModel.deleteMeeting(meetingToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validNameUnfilteredList_success() {
        Meeting meetingToDelete = CS2103_MEETING;
        DeleteCommand deleteCommand = new DeleteMeetingCommand(new MeetingTarget(meetingToDelete.getTitle()));

        String expectedMessage = String.format(DeleteMeetingCommand.MESSAGE_DELETE_MEETING_SUCCESS, meetingToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getMeetingsBook(), new UserPrefs());
        expectedModel.deleteMeeting(meetingToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_incompleteNameUnfilteredList_throwsCommandException() {
        Meeting meetingToDelete = CS2103_MEETING;
        Title targetName = new Title("CS210");
        DeleteCommand deleteCommand = new DeleteMeetingCommand(new MeetingTarget(targetName));

        String expectedMessage = String.format(MeetingTarget.MESSAGE_MEETING_NOT_EXIST, targetName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getMeetingsBook(), new UserPrefs());
        expectedModel.deleteMeeting(meetingToDelete);

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_nameNotExistUnfilteredList_throwsCommandException() {
        Meeting meetingToDelete = CS2103_MEETING;
        Title targetName = new Title("CS3230");
        DeleteCommand deleteCommand = new DeleteMeetingCommand(new MeetingTarget(targetName));

        String expectedMessage = String.format(MeetingTarget.MESSAGE_MEETING_NOT_EXIST, targetName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getMeetingsBook(), new UserPrefs());
        expectedModel.deleteMeeting(meetingToDelete);

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedAndFilteredMeetingList().size() + 1);
        DeleteCommand deleteCommand = new DeleteMeetingCommand(new MeetingTarget(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteMeetingCommand(new MeetingTarget(INDEX_FIRST_MEETING));
        DeleteCommand deleteSecondCommand = new DeleteMeetingCommand(new MeetingTarget(INDEX_SECOND_MEETING));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteMeetingCommand(new MeetingTarget(INDEX_FIRST_MEETING));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoMeeting(Model model) {
        model.updateFilteredMeetingList(p -> false);

        assertTrue(model.getSortedAndFilteredMeetingList().isEmpty());
    }
}
