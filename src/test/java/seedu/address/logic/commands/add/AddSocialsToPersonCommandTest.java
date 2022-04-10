package seedu.address.logic.commands.add;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOCIAL_TELEGRAM;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Target;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.socialmedia.PlatformDescription;
import seedu.address.model.socialmedia.PlatformName;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.testutil.PersonBuilder;

class AddSocialsToPersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());
    private PlatformName validSocialMediaPlatform =
            new PlatformName(VALID_SOCIAL_TELEGRAM.split(", ")[0]);
    private PlatformDescription validSocialMediaDescription =
            new PlatformDescription(VALID_SOCIAL_TELEGRAM.split(", ")[1]);
    private SocialMedia validSocialMedia = new SocialMedia(validSocialMediaPlatform, validSocialMediaDescription);


    @Test
    public void execute_updatedPersonAcceptedByModel_addSuccessful() throws Exception {

        Person validPerson = new PersonBuilder().build();

        model.addPerson(validPerson);


        CommandResult commandResult = new AddSocialsToPersonCommand(new Target(validPerson.getName()),
                validSocialMedia).execute(model);

        assertEquals(String.format(AddSocialsToPersonCommand.MESSAGE_ADD_NEW_SOCIALS_SUCCESS,
                        validSocialMedia, validPerson),
              commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_duplicateSocialMedia_throwsCommandException() throws CommandException {
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);

        AddCommand addCommand = new AddSocialsToPersonCommand(new Target(validPerson.getName()), validSocialMedia);

        CommandResult commandResult = new AddSocialsToPersonCommand(new Target(validPerson.getName()), validSocialMedia)
                .execute(model);


        assertThrows(CommandException.class,
                String.format(AddSocialsToPersonCommand.MESSAGE_SOCIALS_ALREADY_EXISTS, validSocialMedia,
                        validPerson.getName()), () -> addCommand.execute(model));
    }

    @Test
    void testEquals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person aliceCopy = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddSocialsToPersonCommand(new Target(alice.getName()), validSocialMedia);
        AddCommand addBobCommand = new AddSocialsToPersonCommand(new Target(bob.getName()), validSocialMedia);

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
