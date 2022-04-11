package seedu.address.logic.commands.meetingcommands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.meeting.Title;
import seedu.address.model.tag.Tag;


/**
 * Deletes a Tag from a meeting.
 * The meeting in the list is identified using it's displayed index in the meetings book.
 */
public class DeleteMeetingsTagCommand extends DeleteCommand {
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag %s not found in %s!";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    private MeetingTarget target;
    private Tag tagToDelete;

    /**
     * @param target the {@code Index} or {@code Name} being targetted in the MeetingsBook list
     * @param tagToDelete the tag to delete
     */
    public DeleteMeetingsTagCommand(MeetingTarget target, Tag tagToDelete) {
        this.tagToDelete = tagToDelete;
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Meeting> lastShownList = model.getSortedAndFilteredMeetingList();

        Meeting targetMeeting = target.targetMeeting(lastShownList);

        Set<Tag> meetingTags = targetMeeting.getTags();
        Set<Tag> updatedTags = new HashSet<>(meetingTags);

        if (!updatedTags.remove(tagToDelete)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagToDelete, targetMeeting));
        }

        Meeting updatedMeeting = createUpdatedMeeting(targetMeeting, updatedTags);

        model.setMeeting(targetMeeting, updatedMeeting);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMeetingsTagCommand // instanceof handles nulls
                && target.equals(((DeleteMeetingsTagCommand) other).target) // state check
                && tagToDelete.equals(((DeleteMeetingsTagCommand) other).tagToDelete));
    }

    private Meeting createUpdatedMeeting(Meeting meetingToEdit, Set<Tag> updatedTags) {
        assert meetingToEdit != null;

        Title name = meetingToEdit.getTitle();
        Link link = meetingToEdit.getLink();
        StartTime startTime = meetingToEdit.getStartTime();
        Duration duration = meetingToEdit.getDuration();

        return new Meeting(name, link, startTime, duration, updatedTags);
    }
}
