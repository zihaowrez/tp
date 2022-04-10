package seedu.address.logic.commands.meetingcommands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.tag.Tag;

public class AddTagToMeetingCommand extends AddCommand {
    public static final String MESSAGE_ADD_NEW_TAG_SUCCESS = "Added new tag %s to %s";
    public static final String MESSAGE_TAG_ALREADY_EXISTS = "Tag %s already exists in %s!";
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
    public AddTagToMeetingCommand(MeetingTarget target, Tag newTag) {
        this.newTag = newTag;
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Meeting> lastShownList = model.getSortedAndFilteredMeetingList();

        Meeting targetMeeting = target.targetMeeting(lastShownList);

        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();

        Set<Tag> meetingTags = targetMeeting.getTags();

        if (meetingTags.contains(newTag)) {
            throw new CommandException(String.format(MESSAGE_TAG_ALREADY_EXISTS, newTag, targetMeeting.getTitle()));
        }

        Set<Tag> updatedTags = new HashSet<>(meetingTags);
        updatedTags.add(newTag);
        editMeetingDescriptor.setTags(updatedTags);

        Meeting updatedMeeting = EditMeetingDescriptor.createEditedMeeting(targetMeeting, editMeetingDescriptor);

        if (!model.hasTag(newTag)) {
            model.addTag(newTag);
        }
        model.setMeeting(targetMeeting, updatedMeeting);
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        return new CommandResult(String.format(MESSAGE_ADD_NEW_TAG_SUCCESS, newTag, targetMeeting));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagToMeetingCommand
                && target.equals(((AddTagToMeetingCommand) other).target)
                && newTag.equals(((AddTagToMeetingCommand) other).newTag));
    }


}
