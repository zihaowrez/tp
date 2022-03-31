package seedu.address.logic.commands.delete;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;


/**
 * Deletes a Social from a person.
 * The person in the list is identified using it's displayed index or name in the address book.
 */
public class DeletePersonsSocialCommand extends DeleteCommand {
    private static final String MESSAGE_SOCIALS_NOT_FOUND = "Social %s not found in %s!";
    private static final String MESSAGE_DELETE_SOCIAL_SUCCESS = "Deleted Social Media Handle: %1$s";
    private Target target;
    private SocialMedia socialsToDelete;

    /**
     * @param target the {@code Index} or {@code Name} being targetted in the addressbook list
     * @param socialsToDelete the social media handle to delete
     */
    public DeletePersonsSocialCommand(Object target, SocialMedia socialsToDelete) {
        assert target instanceof Name || target instanceof Index;

        this.socialsToDelete = socialsToDelete;

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
