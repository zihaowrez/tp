package seedu.address.logic.commands.delete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.ENEMIES;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


class DeleteTagOnlyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());

    @Test
    public void execute_tagAcceptedByModel_addSuccessful() throws Exception {
        model.addTag(ENEMIES);

        CommandResult commandResult = new DeleteTagOnlyCommand(ENEMIES).execute(model);

        assertEquals(String.format(DeleteTagOnlyCommand.MESSAGE_DELETE_TAG_SUCCESS, ENEMIES),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_tagDoesNotExist_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteTagOnlyCommand(ENEMIES);

        assertThrows(CommandException.class,
                String.format(DeleteTagOnlyCommand.MESSAGE_TAG_NOT_FOUND, ENEMIES), () -> deleteCommand.execute(model));
    }

    @Test
    void testEquals() {
        DeleteCommand deleteFriendsCommand = new DeleteTagOnlyCommand(FRIENDS);
        DeleteCommand deleteEnemiesCommand = new DeleteTagOnlyCommand(ENEMIES);

        // same object -> returns true
        assertTrue(deleteFriendsCommand.equals(deleteFriendsCommand));

        // same values -> returns true
        DeleteCommand addFriendsCommandCopy = new DeleteTagOnlyCommand(FRIENDS);
        assertTrue(deleteFriendsCommand.equals(addFriendsCommandCopy));

        // different types -> returns false
        assertFalse(deleteFriendsCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFriendsCommand.equals(null));

        // different tag -> returns false
        assertFalse(deleteFriendsCommand.equals(deleteEnemiesCommand));
    }

}
