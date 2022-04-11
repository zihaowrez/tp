package seedu.address.logic.commands.delete;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Target;
import seedu.address.model.EmergencyContact;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;


/**
 * Deletes {@code Tag} from a person.
 * The person in the list is identified using it's displayed index or name in the address book.
 */
public class DeletePersonsTagCommand extends DeleteCommand {
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag %s not found in %s!";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag %s from %s successfully!";
    public static final String MESSAGE_CANNOT_DELETE_EMERGENCY_TAG = "Tags of emergency contacts cannot be deleted!";
    private Target target;
    private Tag tagToDelete;

    /**
     * @param target the {@code Index} or {@code Name} being targetted in the addressbook list
     * @param tagToDelete the tag to delete
     */
    public DeletePersonsTagCommand(Target target, Tag tagToDelete) {

        this.tagToDelete = tagToDelete;
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Person> lastShownList = model.getSortedAndFilteredPersonList();
        Person targetPerson = target.targetPerson(lastShownList);

        if (targetPerson instanceof EmergencyContact) {
            throw new CommandException(MESSAGE_CANNOT_DELETE_EMERGENCY_TAG);
        }

        Set<Tag> personsTags = targetPerson.getTags();
        Set<Tag> updatedTags = new HashSet<>(personsTags);

        if (!updatedTags.remove(tagToDelete)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagToDelete, targetPerson));
        }

        Person updatedPerson = createUpdatedPerson(targetPerson, updatedTags);

        model.setPerson(targetPerson, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS,
                tagToDelete, updatedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePersonsTagCommand // instanceof handles nulls
                && target.equals(((DeletePersonsTagCommand) other).target) // state check
                && tagToDelete.equals(((DeletePersonsTagCommand) other).tagToDelete));
    }

    private Person createUpdatedPerson(Person personToEdit, Set<Tag> updatedTags) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        List<SocialMedia> socialMedias = personToEdit.getSocialMedias();

        return new Person(name, phone, email, socialMedias, updatedTags);
    }
}
