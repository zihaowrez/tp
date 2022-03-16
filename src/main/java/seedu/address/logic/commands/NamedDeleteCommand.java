package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed name from the address book.
 */
public class NamedDeleteCommand extends DeleteCommand {

    private final Name targetName;

    public NamedDeleteCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Optional<Person> personToDeleteOptional = lastShownList
                .stream()
                .filter(person -> person.getName().equals(targetName))
                .findFirst();
        if (personToDeleteOptional.isEmpty()) {
            throw new CommandException(String.format(DeleteCommand.MESSAGE_PERSON_NOT_EXIST, targetName));
        } else {
            Person personToDelete = personToDeleteOptional.get();
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NamedDeleteCommand // instanceof handles nulls
                && targetName.equals(((NamedDeleteCommand) other).targetName)); // state check
    }
}

