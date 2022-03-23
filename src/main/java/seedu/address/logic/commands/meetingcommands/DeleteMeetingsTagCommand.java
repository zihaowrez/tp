package seedu.address.logic.commands.meetingcommands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.tag.Tag;


/**
 * Deletes a Tag from a person.
 * The person in the list is identified using it's displayed index or name in the address book.
 */
public class DeleteMeetingsTagCommand extends DeleteCommand {
    private static final String MESSAGE_TAG_NOT_FOUND = "Tag %s not found in %s!";
    private static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    private MeetingTarget target;
    private Tag tagToDelete;

    /**
     * @param target the {@code Index} or {@code Name} being targetted in the MeetingsTab list
     * @param tagToDelete the tag to delete
     */
    public DeleteMeetingsTagCommand(Object target, Tag tagToDelete) {
        assert target instanceof MeetingName || target instanceof Index;

        this.tagToDelete = tagToDelete;

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
                || (other instanceof seedu.address.logic.commands.meetingcommands.DeleteMeetingsTagCommand // instanceof handles nulls
                && target.equals(((seedu.address.logic.commands.meetingcommands.DeleteMeetingsTagCommand) other).target) // state check
                && tagToDelete.equals(((seedu.address.logic.commands.meetingcommands.DeleteMeetingsTagCommand) other).tagToDelete));
    }

    private Meeting createUpdatedMeeting(Meeting meetingToEdit, Set<Tag> updatedTags) {
        assert meetingToEdit != null;

        MeetingName name = meetingToEdit.getName();
        Link link = meetingToEdit.getLink();
        DateTime dateTime = meetingToEdit.getDateTime();

        return new Meeting(name, link, dateTime, updatedTags);
    }
}
