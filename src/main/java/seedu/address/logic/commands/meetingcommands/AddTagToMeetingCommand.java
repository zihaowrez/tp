package seedu.address.logic.commands.meetingcommands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.tag.Tag;

public class AddTagToMeetingCommand extends AddCommand {
    public static final String MESSAGE_ADD_NEW_TAG_SUCCESS = "Added new tag %s to %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds new tags to a meetings in the meetings tab. "
            + "Parameters: "
            + "NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2103 Meeting "
            + PREFIX_TAG + "cs2103 "
            + PREFIX_TAG + "tutorial group";

    private MeetingTarget target;
    private Tag newTag;

    /**
     * @param target The target meeting in the list
     * @param newTag the new tag to be added
     */
    public AddTagToMeetingCommand(Object target, Tag newTag) {
        assert target instanceof MeetingName || target instanceof Index;

        this.newTag = newTag;
        if (target instanceof MeetingName) {
            this.target = MeetingTarget.of((MeetingName) target, null);
        } else if (target instanceof Index) {
            this.target = MeetingTarget.of((Index) target, null);
        } else {
            this.target = null;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Meeting> lastShownList = model.getFilteredMeetingList();
        target.setTargetList(lastShownList);
        Meeting targetMeeting = target.targetMeeting();
        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();

        Set<Tag> meetingTags = targetMeeting.getTags();
        Set<Tag> updatedTags = new HashSet<>(meetingTags);
        updatedTags.add(newTag);
        editMeetingDescriptor.setTags(updatedTags);

        Meeting updatedMeeting = EditMeetingDescriptor.createEditedMeeting(targetMeeting, editMeetingDescriptor);

        model.setMeeting(targetMeeting, updatedMeeting);
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        return new CommandResult(String.format(MESSAGE_ADD_NEW_TAG_SUCCESS, newTag, updatedMeeting));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.meetingcommands.AddTagToMeetingCommand // instanceof handles nulls
                && target.equals(((seedu.address.logic.commands.meetingcommands.AddTagToMeetingCommand) other).target)
                && newTag.equals(((seedu.address.logic.commands.meetingcommands.AddTagToMeetingCommand) other).newTag));
    }


}
