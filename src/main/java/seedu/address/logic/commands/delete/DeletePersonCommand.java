package seedu.address.logic.commands.delete;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Target;
import seedu.address.model.EmergencyContact;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes {@code Person} from the address book.
 * The person in the list is identified using it's displayed index or name in the address book.
 */
public class DeletePersonCommand extends DeleteCommand {
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_CANNOT_DELETE_EMERGENCY_CONTACT = "Emergency Contacts cannot be deleted";

    private Target target;

    /**
     * @param target the {@code Index} or {@code Name} being targetted in the addressbook list
     */
    public DeletePersonCommand(Target target) {
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Person> lastShownList = model.getSortedAndFilteredPersonList();
        Person personToDelete = target.targetPerson(lastShownList);

        if (personToDelete instanceof EmergencyContact) {
            throw new CommandException(MESSAGE_CANNOT_DELETE_EMERGENCY_CONTACT);
        }

        if (personToDelete.equals(model.getCurrentlySelectedPerson().get())) {
            model.updateSelectedPerson(null);
            model.updateSelectedIndex(null);
        }
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePersonCommand // instanceof handles nulls
                && target.equals(((DeletePersonCommand) other).target)); // state check
    }


}
