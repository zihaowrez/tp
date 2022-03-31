package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents the intent of the user to add something.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Here are the possible uses for add \n \n"
            + "Adding a New Person: `add n/NAME p/PHONE e/EMAIL [d/{SOCIAL MEDIA PLATFORM}, {ID}]… [t/TAG]…` \n"
            + "   Example: " + COMMAND_WORD + " n/Amy p/948999384 e/amy@u.nus.edu t/2103 \n \n"
            + "Adding a New Tag to a Person: `add NAME [t/TAG]…` \n"
            + "   Example: " + COMMAND_WORD + " Amy t/nus \n \n"
            + "Adding a New Detail to a Person: `[d/{SOCIAL MEDIA PLATFORM}, {ID}]… \n"
            + "   Example: " + COMMAND_WORD + " Amy d/Telegram,@amy4593 \n \n"
            + "Please refer to the HELP tab for more details \n";


    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);
}
