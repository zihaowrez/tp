package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Target;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.socialmedia.PlatformDescription;
import seedu.address.model.socialmedia.PlatformName;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.testutil.EditPersonDescriptorBuilder;



class EditSocialMediaCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());
    private PlatformDescription validPlatformDescription = new PlatformDescription("@dummy123");
    private PlatformName validPlatformName = new PlatformName("Discord");

    @Test
    public void execute_editPlatformName_success() throws CommandException {
        Person editedPerson = model.getSortedAndFilteredPersonList().get(0);
        SocialMedia socialMediaToEdit = editedPerson.getSocialMedias().get(0);
        EditCommand editCommand = new EditSocialMediaCommand(new Target(INDEX_FIRST_PERSON),
                INDEX_FIRST_PERSON, validPlatformName.getValue(), true);

        SocialMedia updatedSocialMedia = new SocialMedia(validPlatformName, socialMediaToEdit.getPlatformDescription());

        String expectedMessage = String.format(EditSocialMediaCommand.MESSAGE_EDIT_SOCIALS_SUCCESS,
                editedPerson.getName(), socialMediaToEdit, updatedSocialMedia);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getMeetingsBook(), new UserPrefs());
        expectedModel.setPerson(model.getSortedAndFilteredPersonList().get(0), editedPerson);

        assertEquals(expectedMessage, editCommand.execute(model).getFeedbackToUser());
    }

    @Test
    public void execute_editPlatformDescription_success() throws CommandException {
        Person editedPerson = model.getSortedAndFilteredPersonList().get(0);
        SocialMedia socialMediaToEdit = editedPerson.getSocialMedias().get(0);
        EditCommand editCommand = new EditSocialMediaCommand(new Target(INDEX_FIRST_PERSON),
                INDEX_FIRST_PERSON, validPlatformDescription.getValue(), false);

        SocialMedia updatedSocialMedia = new SocialMedia(socialMediaToEdit.getPlatformName(), validPlatformDescription);

        String expectedMessage = String.format(EditSocialMediaCommand.MESSAGE_EDIT_SOCIALS_SUCCESS,
                editedPerson.getName(), socialMediaToEdit, updatedSocialMedia);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getMeetingsBook(), new UserPrefs());
        expectedModel.setPerson(model.getSortedAndFilteredPersonList().get(0), editedPerson);

        assertEquals(expectedMessage, editCommand.execute(model).getFeedbackToUser());
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedAndFilteredPersonList().size() + 1);
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditPersonCommand(new Target(outOfBoundIndex), descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void execute_invalidSocialMediaIndex_failure() {
        Person person = model.getSortedAndFilteredPersonList().get(0);
        Index outOfBoundIndex = Index.fromZeroBased(person.getSocialMedias().size());

        EditSocialMediaCommand editCommand = new EditSocialMediaCommand(new Target(INDEX_FIRST_PERSON),
                outOfBoundIndex, validPlatformName.getValue(), true);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_SOCIAL_MEDIA_DISPLAYED_INDEX);

    }


}
