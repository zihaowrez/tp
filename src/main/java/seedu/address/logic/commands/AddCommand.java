package seedu.address.logic.commands;

import seedu.address.logic.commands.add.AddPersonCommand;
import seedu.address.logic.commands.add.AddSocialsToPersonCommand;
import seedu.address.logic.commands.add.AddTagToPersonCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents the intent of the user to add something.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Here are the possible uses for add \n \n"
        + AddPersonCommand.MESSAGE_USAGE + "\n \n"
        + AddTagToPersonCommand.MESSAGE_USAGE + "\n \n"
        + AddSocialsToPersonCommand.MESSAGE_USAGE;


    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);
}
