package seedu.address.logic.commands.delete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.ENEMIES;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.add.AddTagToPersonCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Target;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePersonsTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getSortedAndFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        new AddTagToPersonCommand(new Target(personToDelete.getName()), ENEMIES).execute(model);

        CommandResult commandResult = new DeletePersonsTagCommand(new Target(personToDelete.getName()), FRIENDS)
                .execute(model);

        String expectedMessage = String.format(DeletePersonsTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                FRIENDS, personToDelete.getName());

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validNameUnfilteredList_success() throws CommandException {
        Person personToDelete = ALICE;
        new AddTagToPersonCommand(new Target(personToDelete.getName()), ENEMIES).execute(model);

        CommandResult commandResult = new DeletePersonsTagCommand(new Target(personToDelete.getName()), FRIENDS)
                .execute(model);

        String expectedMessage = String.format(DeletePersonsTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                FRIENDS, personToDelete.getName());

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }


    @Test
    public void execute_incompleteNameUnfilteredList_throwsCommandException() throws CommandException {
        Person personToDelete = ALICE;
        Name targetName = new Name("Alice");
        new AddTagToPersonCommand(new Target(personToDelete.getName()), ENEMIES).execute(model);
        DeleteCommand deleteCommand = new DeletePersonsTagCommand(new Target(targetName), FRIENDS);

        String expectedMessage = String.format(Target.MESSAGE_PERSON_NOT_EXIST, targetName);

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_nameNotExistUnfilteredList_throwsCommandException() throws CommandException {
        Person personToDelete = ALICE;
        Name targetName = new Name("Bob Pauline");
        new AddTagToPersonCommand(new Target(personToDelete.getName()), ENEMIES).execute(model);
        DeleteCommand deleteCommand = new DeletePersonsTagCommand(new Target(targetName), FRIENDS);

        String expectedMessage = String.format(Target.MESSAGE_PERSON_NOT_EXIST, targetName);

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedAndFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeletePersonsTagCommand(new Target(outOfBoundIndex), FRIENDS);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getSortedAndFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        new AddTagToPersonCommand(new Target(personToDelete.getName()), ENEMIES).execute(model);

        CommandResult commandResult = new DeletePersonsTagCommand(new Target(personToDelete.getName()), FRIENDS)
                .execute(model);

        String expectedMessage = String.format(DeletePersonsTagCommand.MESSAGE_DELETE_TAG_SUCCESS,
                FRIENDS, personToDelete.getName());

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeletePersonsTagCommand(new Target(outOfBoundIndex), FRIENDS);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeletePersonsTagCommand(new Target(INDEX_FIRST_PERSON), FRIENDS);
        DeleteCommand deleteSecondCommand = new DeletePersonsTagCommand(new Target(INDEX_SECOND_PERSON), FRIENDS);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeletePersonsTagCommand(new Target(INDEX_FIRST_PERSON), FRIENDS);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
