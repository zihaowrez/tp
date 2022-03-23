package seedu.address.logic.commands.meetingcommands;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddMeetingCommand extends AddCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the Meetings Tab. "
            + "Parameters: "
            + PREFIX_NAME + "NAME OF MEETING "
            + PREFIX_LINK + "LINK OR URL "
            + PREFIX_START_DATETIME + "START DATE AND TIME "
            + PREFIX_END_DATETIME + "END DATE AND TIME"
            + "Example: " + COMMAND_WORD + " n/CS2103 Meeting l/https://zoom.sg s/2022-10-10 1800 e/2022-10-10 2000";

    public static final String MESSAGE_SUCCESS = "New meeting added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book";

    private final Meeting toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddMeetingCommand(Meeting meeting) {
        requireNonNull(meeting);
        toAdd = meeting;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMeeting(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }

        model.addMeeting(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMeetingCommand // instanceof handles nulls
                && toAdd.equals(((AddMeetingCommand) other).toAdd));
    }
}
