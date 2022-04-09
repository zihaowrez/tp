package seedu.address.logic.commands.add;

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
import seedu.address.logic.parser.Target;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class AddTagToPersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());

    @Test
    public void execute_updatedPersonAcceptedByModel_addSuccessful() throws Exception {

        Person validPerson = new PersonBuilder().build();

        model.addPerson(validPerson);
        CommandResult commandResult = new AddTagToPersonCommand(new Target(validPerson.getName()), FRIENDS)
                .execute(model);

        assertEquals(String.format(AddTagToPersonCommand.MESSAGE_ADD_NEW_TAG_SUCCESS, FRIENDS, validPerson),
                commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_duplicateTag_throwsCommandException() throws CommandException {
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);

        AddCommand addCommand = new AddTagToPersonCommand(new Target(validPerson.getName()), FRIENDS);

        CommandResult commandResult = new AddTagToPersonCommand(new Target(validPerson.getName()), FRIENDS)
                .execute(model);


        assertThrows(CommandException.class,
            String.format(AddTagToPersonCommand.MESSAGE_TAG_ALREADY_EXISTS, FRIENDS,
                validPerson.getName()), () -> addCommand.execute(model));
    }

    @Test
    void testEquals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddTagToPersonCommand(new Target(alice.getName()), FRIENDS);
        AddCommand addBobCommand = new AddTagToPersonCommand(new Target(bob.getName()), FRIENDS);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }


}
