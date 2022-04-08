package seedu.address.logic.commands.meetingcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.MeetingsBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.testutil.EditMeetingDescriptorBuilder;
import seedu.address.testutil.MeetingBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditMeetingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Meeting editedMeeting = new MeetingBuilder().build();
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(editedMeeting).build();
        EditMeetingCommand editMeetingCommand =
                new EditMeetingCommand(new MeetingTarget(INDEX_FIRST_MEETING), descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new MeetingsBook(model.getMeetingsBook()), new UserPrefs());
        expectedModel.setMeeting(model.getSortedAndFilteredMeetingList().get(0), editedMeeting);

        CommandTestUtil.assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Index indexLastMeeting = Index.fromOneBased(model.getSortedAndFilteredMeetingList().size());
        Meeting lastMeeting = model.getSortedAndFilteredMeetingList().get(indexLastMeeting.getZeroBased());

        MeetingBuilder meetingInList = new MeetingBuilder(lastMeeting);
        Meeting editedMeeting = meetingInList.withTitle(CommandTestUtil.VALID_TITLE_CS2103)
                .withLink(CommandTestUtil.VALID_LINK_TEAMS).withTags(CommandTestUtil.VALID_TAG_PROJECT).build();

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                .withTitle(CommandTestUtil.VALID_TITLE_CS2103).withLink(CommandTestUtil.VALID_LINK_TEAMS)
                .withTags(CommandTestUtil.VALID_TAG_PROJECT).build();
        EditMeetingCommand editCommand = new EditMeetingCommand(new MeetingTarget(indexLastMeeting), descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getMeetingsBook(), new UserPrefs());
        expectedModel.setMeeting(lastMeeting, editedMeeting);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        EditMeetingCommand editMeetingCommand =
                new EditMeetingCommand(new MeetingTarget(INDEX_FIRST_MEETING), new EditMeetingDescriptor());
        Meeting editedMeeting = model.getSortedAndFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased());

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getMeetingsBook(), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMeeting_failure() {
        Meeting firstMeeting = model.getSortedAndFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased());
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(firstMeeting).build();
        EditMeetingCommand editMeetingCommand =
                new EditMeetingCommand(new MeetingTarget(INDEX_SECOND_MEETING), descriptor);

        CommandTestUtil.assertCommandFailure(editMeetingCommand, model, EditMeetingCommand.MESSAGE_DUPLICATE_MEETING);
    }

    @Test
    public void execute_invalidMeetingIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedAndFilteredMeetingList().size() + 1);
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                .withTitle(CommandTestUtil.VALID_TITLE_CS3230).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(new MeetingTarget(outOfBoundIndex), descriptor);

        CommandTestUtil.assertCommandFailure(editMeetingCommand, model,
                Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_startTimeInThePast_failure() {
        Meeting firstMeeting = model.getSortedAndFilteredMeetingList().get(INDEX_FIRST_MEETING.getZeroBased());
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(firstMeeting)
                .withStartTime("2022-4-5 1200").build();
        EditMeetingCommand editMeetingCommand =
                new EditMeetingCommand(new MeetingTarget(INDEX_FIRST_MEETING), descriptor);

        CommandTestUtil.assertCommandFailure(editMeetingCommand, model,
                EditMeetingCommand.MESSAGE_PAST_MEETING);
    }

    @Test
    public void equals() {
        final EditMeetingCommand standardCommand =
                new EditMeetingCommand(new MeetingTarget(INDEX_FIRST_MEETING), CommandTestUtil.DESC_CS2103);

        // same values -> returns true
        EditMeetingDescriptor copyDescriptor = new EditMeetingDescriptorBuilder(CommandTestUtil.DESC_CS2103).build();
        EditMeetingCommand commandWithSameValues =
                new EditMeetingCommand(new MeetingTarget(INDEX_FIRST_MEETING), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMeetingCommand(
                new MeetingTarget(INDEX_SECOND_MEETING), CommandTestUtil.DESC_CS2103)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMeetingCommand(
                new MeetingTarget(INDEX_FIRST_MEETING), CommandTestUtil.DESC_CS3230)));
    }

}
