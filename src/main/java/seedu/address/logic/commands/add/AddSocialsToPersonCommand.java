package seedu.address.logic.commands.add;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA;
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
import seedu.address.model.socialmedia.SocialMedia;

public class AddSocialsToPersonCommand extends AddCommand {
    public static final String MESSAGE_ADD_NEW_SOCIALS_SUCCESS = "Added new social media handle %s to %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds new social media handles to a person in address book. "
            + "Parameters: "
            + "NAME "
            + "[" + PREFIX_SOCIAL_MEDIA + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_SOCIAL_MEDIA + "Instagram, @johndoe";

    private Target target;
    private SocialMedia newSocials;

    /**
     * @param target The target person in the list
     * @param newSocials new tag to be added
     */
    public AddSocialsToPersonCommand(Object target, SocialMedia newSocials) {
        assert target instanceof Name || target instanceof Index;

        this.newSocials = newSocials;
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

        Set<SocialMedia> personsSocials = targetPerson.getSocialMedias();
        Set<SocialMedia> updatedSocials = new HashSet<>(personsSocials);
        updatedSocials.add(newSocials);
        editPersonDescriptor.setSocials(updatedSocials);

        Person updatedPerson = EditPersonDescriptor.createEditedPerson(targetPerson, editPersonDescriptor);

        model.setPerson(targetPerson, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_NEW_SOCIALS_SUCCESS, newSocials, updatedPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagToPersonCommand // instanceof handles nulls
                && target.equals(((AddSocialsToPersonCommand) other).target)
                && newSocials.equals(((AddSocialsToPersonCommand) other).newSocials));

    }


}
