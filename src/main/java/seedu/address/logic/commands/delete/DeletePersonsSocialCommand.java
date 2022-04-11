package seedu.address.logic.commands.delete;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
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
 * Deletes a targetted social media from a person.
 * The person in the list is identified using it's displayed index or name in the address book.
 */
public class DeletePersonsSocialCommand extends DeleteCommand {
    public static final String MESSAGE_SOCIALS_NOT_FOUND = "Social %s not found in %s!";
    public static final String MESSAGE_DELETE_SOCIAL_SUCCESS = "Deleted Social Media Handle: %1$s";
    public static final String MESSAGE_CANNOT_DELETE_SOCIALS_OF_EMERGENCY =
            "Socials of Emergency Contacts cannot be deleted";

    private Target target;
    private SocialMedia socialsToDelete;

    /**
     * @param target the {@code Index} or {@code Name} being targetted in the addressbook list
     * @param socialsToDelete the social media handle to delete
     */
    public DeletePersonsSocialCommand(Target target, SocialMedia socialsToDelete) {
        this.socialsToDelete = socialsToDelete;
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Person> lastShownList = model.getSortedAndFilteredPersonList();
        Person targetPerson = target.targetPerson(lastShownList);

        if (targetPerson instanceof EmergencyContact) {
            throw new CommandException(MESSAGE_CANNOT_DELETE_SOCIALS_OF_EMERGENCY);
        }

        List<SocialMedia> personsSocials = targetPerson.getSocialMedias();
        List<SocialMedia> updatedSocials = new ArrayList<>(personsSocials);

        if (!updatedSocials.remove(socialsToDelete)) {
            throw new CommandException(String.format(MESSAGE_SOCIALS_NOT_FOUND, socialsToDelete, targetPerson));
        }

        Person updatedPerson = createUpdatedPerson(targetPerson, updatedSocials);

        model.setPerson(targetPerson, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_SOCIAL_SUCCESS, socialsToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePersonsSocialCommand // instanceof handles nulls
                && target.equals(((DeletePersonsSocialCommand) other).target) // state check
                && socialsToDelete.equals(((DeletePersonsSocialCommand) other).socialsToDelete));
    }


    private Person createUpdatedPerson(Person personToEdit, List<SocialMedia> updatedSocials) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, updatedSocials, tags);
    }
}
