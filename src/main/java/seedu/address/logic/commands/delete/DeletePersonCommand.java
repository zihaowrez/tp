package seedu.address.logic.commands.delete;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a person from the addressbook.
 * The person in the list is identified using it's displayed index or name in the address book.
 */
public class DeletePersonCommand extends DeleteCommand {
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private Target target;

    /**
     * @param target the {@code Index} or {@code Name} being targetted in the addressbook list
     */
    public DeletePersonCommand(Object target) {
        assert target instanceof Name || target instanceof Index;

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
        List<Person> lastShownList = model.getFilteredPersonList();
        target.setTargetList(lastShownList);
        Person personToDelete = target.targetPerson();

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
