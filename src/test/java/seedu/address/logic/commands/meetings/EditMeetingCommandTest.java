package seedu.address.logic.commands.meetings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMeetingAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand;
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
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Meeting editedMeeting = new MeetingBuilder().build();
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(editedMeeting).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new MeetingsBook(model.getMeetingsBook()), new UserPrefs());
        expectedModel.setMeeting(model.getSortedAndFilteredMeetingList().get(0), editedMeeting);

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMeeting = Index.fromOneBased(model.getSortedAndFilteredMeetingList().size());
        Meeting lastMeeting = model.getSortedAndFilteredMeetingList().get(indexLastMeeting.getZeroBased());

        MeetingBuilder meetingInList = new MeetingBuilder(lastMeeting);
        Meeting editedMeeting = meetingInList.withTitle(VALID_NAME_BOB).withLink(VALID_LINK)
                .withTags(VALID_TAG_HUSBAND).build();

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withName(VALID_NAME_BOB)
                .withLink(VALID_LINK).withTags(VALID_TAG_HUSBAND).build();
        EditMeetingCommand editCommand = new EditMeetingCommand(indexLastMeeting, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getMeetingsBook(), new UserPrefs());
        expectedModel.setMeeting(lastMeeting, editedMeeting);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, new EditMeetingDescriptor());
        Meeting editedMeeting = model.getSortedAndFilteredMeetingList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getMeetingsBook(), new UserPrefs());

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMeetingAtIndex(model, INDEX_FIRST_PERSON);

        Meeting meetingInFilteredList = model.getSortedAndFilteredMeetingList().get(INDEX_FIRST_PERSON.getZeroBased());
        Meeting editedMeeting = new MeetingBuilder(meetingInFilteredList).withTitle(VALID_MEETING_NAME).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON,
                new EditMeetingDescriptorBuilder().withName(VALID_MEETING_NAME).build());

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new MeetingsBook(model.getMeetingsBook()), new UserPrefs());
        expectedModel.setMeeting(model.getSortedAndFilteredMeetingList().get(0), editedMeeting);

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMeetingUnfilteredList_failure() {
        Meeting firstMeeting = model.getSortedAndFilteredMeetingList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(firstMeeting).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editMeetingCommand, model, EditMeetingCommand.MESSAGE_DUPLICATE_MEETING);
    }

    @Test
    public void execute_duplicateMeetingFilteredList_failure() {
        showMeetingAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Meeting meetingInList = model.getMeetingsBook().getMeetingList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON,
                new EditMeetingDescriptorBuilder(meetingInList).build());

        assertCommandFailure(editMeetingCommand, model, EditMeetingCommand.MESSAGE_DUPLICATE_MEETING);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedAndFilteredMeetingList().size() + 1);
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editMeetingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidMeetingIndexFilteredList_failure() {
        showMeetingAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMeetingsBook().getMeetingList().size());

        EditMeetingCommand editCommand = new EditMeetingCommand(outOfBoundIndex,
                new EditMeetingDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditMeetingCommand standardCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, DESC_CS2103);

        // same values -> returns true
        EditMeetingDescriptor copyDescriptor = new EditMeetingDescriptorBuilder(DESC_CS2103).build();
        EditMeetingCommand commandWithSameValues = new EditMeetingCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMeetingCommand(INDEX_SECOND_PERSON, DESC_CS2103)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMeetingCommand(INDEX_FIRST_PERSON, DESC_CS3230)));
    }

}
