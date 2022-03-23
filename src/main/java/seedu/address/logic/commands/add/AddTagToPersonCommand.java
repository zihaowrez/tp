package seedu.address.logic.commands.add;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.delete.Target;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class AddTagToPersonCommand extends AddCommand {
    public static final String MESSAGE_ADD_NEW_TAG_SUCCESS = "Added new tag %s to %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds new tags to a person in address book. "
            + "Parameters: "
            + "NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    private Target target;
    private Tag newTag;

    /**
     * @param target The target person in the list
     * @param newTag the new tag to be added
     */
    public AddTagToPersonCommand(Object target, Tag newTag) {
        assert target instanceof Name || target instanceof Index;

        this.newTag = newTag;
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
        Person targetPerson = target.targetPerson();
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        Set<Tag> personsTags = targetPerson.getTags();
        Set<Tag> updatedTags = new HashSet<>(personsTags);
        updatedTags.add(newTag);
        editPersonDescriptor.setTags(updatedTags);

        Person updatedPerson = EditPersonDescriptor.createEditedPerson(targetPerson, editPersonDescriptor);

        model.setPerson(targetPerson, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (!model.hasTag(newTag)) {
            model.addTag(newTag);
        }

        return new CommandResult(String.format(MESSAGE_ADD_NEW_TAG_SUCCESS, newTag, updatedPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagToPersonCommand // instanceof handles nulls
                && target.equals(((AddTagToPersonCommand) other).target)
                && newTag.equals(((AddTagToPersonCommand) other).newTag));

    }


}
