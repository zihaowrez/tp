package seedu.address.logic.commands.meetingcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.MeetingsBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearMeetingsCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Meetings book has been cleared! ";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMeetingsBook(new MeetingsBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
