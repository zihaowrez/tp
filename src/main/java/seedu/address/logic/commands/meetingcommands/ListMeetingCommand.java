package seedu.address.logic.commands.meetingcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListMeetingCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = " meetings listed!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        int listSize = model.getFilteredMeetingList().size();
        return new CommandResult(listSize + MESSAGE_SUCCESS);
    }
}
