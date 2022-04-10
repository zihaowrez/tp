package seedu.address.logic.commands.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.delete.Target;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EmergencyContact;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class EditPersonCommand extends EditCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            // + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_SOCIAL_MEDIA + "PLATFORM, PLATFORM_DESCRIPTION]...\n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_CANNOT_EDIT_EMERGENCY_CONTACTS = "Emergency Contacts cannot be edited";

    private final Target target;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param target The target person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditPersonCommand(Object target, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(target);
        requireNonNull(editPersonDescriptor);
        assert target instanceof Name || target instanceof Index;

        if (target instanceof Name) {
            this.target = Target.of((Name) target, null);
        } else if (target instanceof Index) {
            this.target = Target.of((Index) target, null);
        } else {
            this.target = null;
        }

        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getSortedAndFilteredPersonList();
        target.setTargetList(lastShownList);

        Person personToEdit = target.targetPerson();

        if (personToEdit instanceof EmergencyContact) {
            throw new CommandException(MESSAGE_CANNOT_EDIT_EMERGENCY_CONTACTS);
        }
        Person editedPerson = EditPersonDescriptor.createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        for (Tag tag: editedPerson.getTags()) {
            if (!model.hasTag(tag)) {
                model.addTag(tag);
            }
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateSelectedPerson(editedPerson);
        model.updateSelectedIndex(null);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonCommand)) {
            return false;
        }

        // state check
        EditPersonCommand e = (EditPersonCommand) other;
        return target.equals(e.target)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }
}
