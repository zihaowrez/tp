package seedu.address.logic.commands.meetingcommands;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;

public class DeleteMeetingCommand extends DeleteCommand {
    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Deleted Meeting: %1$s";

    private MeetingTarget target;

    /**
     * @param target the {@code Index} or {@code Title} being targetted in the MeetingsBook list
     */
    public DeleteMeetingCommand(MeetingTarget target) {
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Meeting> lastShownList = model.getSortedAndFilteredMeetingList();
        Meeting meetingToDelete = target.targetMeeting(lastShownList);

        model.deleteMeeting(meetingToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS, meetingToDelete));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMeetingCommand // instanceof handles nulls
                && target.equals(((DeleteMeetingCommand) other).target)); // state check
    }


}
