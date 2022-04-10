package seedu.address.logic.commands.delete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOCIAL_TELEGRAM;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.add.AddSocialsToPersonCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Target;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.socialmedia.PlatformDescription;
import seedu.address.model.socialmedia.PlatformName;
import seedu.address.model.socialmedia.SocialMedia;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePersonsSocialCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());
    private PlatformName validSocialMediaPlatform = new PlatformName(VALID_SOCIAL_TELEGRAM.split(", ")[0]);
    private PlatformDescription validSocialMediaDescription =
            new PlatformDescription(VALID_SOCIAL_TELEGRAM.split(", ")[1]);
    private SocialMedia validSocialMedia = new SocialMedia(validSocialMediaPlatform, validSocialMediaDescription);

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getSortedAndFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        new AddSocialsToPersonCommand(new Target(personToDelete.getName()), validSocialMedia).execute(model);

        CommandResult commandResult = new DeletePersonsSocialCommand(new Target(personToDelete.getName()),
                validSocialMedia).execute(model);

        String expectedMessage = String.format(DeletePersonsSocialCommand.MESSAGE_DELETE_SOCIAL_SUCCESS,
                validSocialMedia);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validNameUnfilteredList_success() throws CommandException {
        Person personToDelete = ALICE;
        new AddSocialsToPersonCommand(new Target(personToDelete.getName()), validSocialMedia).execute(model);

        CommandResult commandResult = new DeletePersonsSocialCommand(new Target(personToDelete.getName()),
                validSocialMedia).execute(model);

        String expectedMessage = String.format(DeletePersonsSocialCommand.MESSAGE_DELETE_SOCIAL_SUCCESS,
                validSocialMedia);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }


    @Test
    public void execute_incompleteNameUnfilteredList_throwsCommandException() throws CommandException {
        Person personToDelete = ALICE;
        Name targetName = new Name("Alice");
        new AddSocialsToPersonCommand(new Target(personToDelete.getName()), validSocialMedia).execute(model);
        DeleteCommand deleteCommand = new DeletePersonsSocialCommand(new Target(targetName), validSocialMedia);

        String expectedMessage = String.format(Target.MESSAGE_PERSON_NOT_EXIST, targetName);

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_nameNotExistUnfilteredList_throwsCommandException() throws CommandException {
        Person personToDelete = ALICE;
        Name targetName = new Name("Bob Pauline");
        new AddSocialsToPersonCommand(new Target(personToDelete.getName()), validSocialMedia).execute(model);
        DeleteCommand deleteCommand = new DeletePersonsSocialCommand(new Target(targetName), validSocialMedia);

        String expectedMessage = String.format(Target.MESSAGE_PERSON_NOT_EXIST, targetName);

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedAndFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeletePersonsSocialCommand(new Target(outOfBoundIndex), validSocialMedia);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getSortedAndFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        new AddSocialsToPersonCommand(new Target(personToDelete.getName()), validSocialMedia).execute(model);

        CommandResult commandResult = new DeletePersonsSocialCommand(new Target(personToDelete.getName()),
                validSocialMedia).execute(model);

        String expectedMessage = String.format(DeletePersonsSocialCommand.MESSAGE_DELETE_SOCIAL_SUCCESS,
                validSocialMedia);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeletePersonsSocialCommand(new Target(outOfBoundIndex), validSocialMedia);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeletePersonsSocialCommand(new Target(INDEX_FIRST_PERSON),
                validSocialMedia);
        DeleteCommand deleteSecondCommand = new DeletePersonsSocialCommand(new Target(INDEX_SECOND_PERSON),
                validSocialMedia);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeletePersonsSocialCommand(new Target(INDEX_FIRST_PERSON),
                validSocialMedia);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
