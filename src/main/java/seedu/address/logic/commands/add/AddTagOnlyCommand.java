package seedu.address.logic.commands.add;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class AddTagOnlyCommand extends AddCommand {
    public static final String MESSAGE_ADD_NEW_TAG_SUCCESS = "Added new tag %s";
    public static final String MESSAGE_TAG_ALREADY_EXISTS = "Tag %s already exists!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds new tags in address book. "
            + "Parameters: "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "friends ";

    private Tag newTag;

    /**
     * @param newTag the new tag to be added
     */
    public AddTagOnlyCommand(Tag newTag) {

        this.newTag = newTag;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Tag> tagList = model.getFilteredTagList();

        if (model.hasTag(newTag)) {
            throw new CommandException(String.format(MESSAGE_TAG_ALREADY_EXISTS, newTag));
        }

        model.addTag(newTag);


        return new CommandResult(String.format(MESSAGE_ADD_NEW_TAG_SUCCESS, newTag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagOnlyCommand // instanceof handles nulls
                && newTag.equals(((AddTagOnlyCommand) other).newTag));

    }


}
