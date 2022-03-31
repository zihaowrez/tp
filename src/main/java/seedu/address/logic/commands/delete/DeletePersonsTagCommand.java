package seedu.address.logic.commands.delete;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EmergencyContact;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;


/**
 * Deletes a Tag from a person.
 * The person in the list is identified using it's displayed index or name in the address book.
 */
public class DeletePersonsTagCommand extends DeleteCommand {
    private static final String MESSAGE_TAG_NOT_FOUND = "Tag %s not found in %s!";
    private static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    private static final String MESSAGE_CANNOT_DELETE_EMERGENCY_TAG = "Tags of emergency contacts cannot be deleted!";
    private Target target;
    private Tag tagToDelete;

    /**
     * @param target the {@code Index} or {@code Name} being targetted in the addressbook list
     * @param tagToDelete the tag to delete
     */
    public DeletePersonsTagCommand(Object target, Tag tagToDelete) {
        assert target instanceof Name || target instanceof Index;

        this.tagToDelete = tagToDelete;

        if (target instanceof Name) {
            this.target = Target.of((Name) target, null);
        } else if (target instanceof Index) {
            this.target = Target.of((Index) target, null);
        } else {
            this.target = null;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Person> lastShownList = model.getSortedAndFilteredPersonList();
        target.setTargetList(lastShownList);
        Person targetPerson = target.targetPerson();

        if (targetPerson instanceof EmergencyContact) {
            throw new CommandException(MESSAGE_CANNOT_DELETE_EMERGENCY_TAG);
        }

        Set<Tag> personsTags = targetPerson.getTags();
        Set<Tag> updatedTags = new HashSet<>(personsTags);

        if (!updatedTags.remove(tagToDelete)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagToDelete, targetPerson.getName()));
        }

        Person updatedPerson = createUpdatedPerson(targetPerson, updatedTags);

        model.setPerson(targetPerson, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
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
        Set<SocialMedia> socialMedias = personToEdit.getSocialMedias();

        return new Person(name, phone, email, socialMedias, updatedTags);
    }
}
