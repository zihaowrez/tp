package seedu.address.logic.commands.add;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Target;
import seedu.address.model.EmergencyContact;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.socialmedia.SocialMedia;

public class AddSocialsToPersonCommand extends AddCommand {
    public static final String MESSAGE_ADD_NEW_SOCIALS_SUCCESS = "Added new social media handle %s to %s";
    public static final String MESSAGE_CANNOT_ADD_SOCIALS_EMERGENCY =
            "Social medias cannot be added to emergency contacts";
    public static final String MESSAGE_SOCIALS_ALREADY_EXISTS = "Socials %s already exists in %s!";

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
     * @param newSocials new social media to be added
     */
    public AddSocialsToPersonCommand(Target target, SocialMedia newSocials) {

        this.newSocials = newSocials;
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Person> lastShownList = model.getSortedAndFilteredPersonList();
        Person targetPerson = target.targetPerson(lastShownList);

        if (targetPerson instanceof EmergencyContact) {
            throw new CommandException(MESSAGE_CANNOT_ADD_SOCIALS_EMERGENCY);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        List<SocialMedia> personsSocials = targetPerson.getSocialMedias();
        List<SocialMedia> updatedSocials = new ArrayList<>(personsSocials);

        if (updatedSocials.contains(newSocials)) {
            throw new CommandException(String.format(MESSAGE_SOCIALS_ALREADY_EXISTS,
                    newSocials, targetPerson.getName()));
        }

        updatedSocials.add(newSocials);
        editPersonDescriptor.setSocials(updatedSocials);

        Person updatedPerson = EditPersonDescriptor.createEditedPerson(targetPerson, editPersonDescriptor);

        model.setPerson(targetPerson, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_NEW_SOCIALS_SUCCESS, newSocials, targetPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagToPersonCommand // instanceof handles nulls
                && target.equals(((AddSocialsToPersonCommand) other).target)
                && newSocials.equals(((AddSocialsToPersonCommand) other).newSocials));

    }


}
