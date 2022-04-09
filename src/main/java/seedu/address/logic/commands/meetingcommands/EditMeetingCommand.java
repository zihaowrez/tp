package seedu.address.logic.commands.meetingcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.meeting.Title;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing meeting in the MeetingsBook.
 */
public class EditMeetingCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meeting identified "
            + "by the index number used in the displayed meeting list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_LINK + "LINK] "
            + "[" + PREFIX_STARTTIME + "DATE AND TIME] "
            + "[" + PREFIX_DURATION + "DATE AND TIME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINK + "https://zoom.sg/123456/CS2103 ";

    public static final String MESSAGE_EDIT_MEETING_SUCCESS = "Edited Meeting: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book.";
    public static final String MESSAGE_PAST_MEETING = "Cannot edit a meeting to start in the past";

    private final MeetingTarget meetingTarget;
    private final EditMeetingDescriptor editMeetingDescriptor;

    /**
     * @param meetingTarget of the meeting in the filtered meeting list to edit
     * @param editMeetingDescriptor details to edit the meeting with
     */
    public EditMeetingCommand(MeetingTarget meetingTarget, EditMeetingDescriptor editMeetingDescriptor) {
        requireNonNull(meetingTarget);
        requireNonNull(editMeetingDescriptor);

        this.meetingTarget = meetingTarget;
        this.editMeetingDescriptor = new EditMeetingDescriptor(editMeetingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meeting> lastShownList = model.getSortedAndFilteredMeetingList();

        Meeting meetingToEdit = meetingTarget.targetMeeting(lastShownList);
        Meeting editedMeeting = EditMeetingDescriptor.createEditedMeeting(meetingToEdit, editMeetingDescriptor);

        if (!meetingToEdit.isSameMeeting(editedMeeting) && model.hasMeeting(editedMeeting)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }


        if (StartTime.isInThePast(editedMeeting.getStartTime())) {
            throw new CommandException(MESSAGE_PAST_MEETING);
        }

        for (Tag tag: editedMeeting.getTags()) {
            if (!model.hasTag(tag)) {
                model.addTag(tag);
            }
        }

        model.setMeeting(meetingToEdit, editedMeeting);
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        return new CommandResult(String.format(MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMeetingCommand)) {
            return false;
        }

        // state check
        EditMeetingCommand e = (EditMeetingCommand) other;
        return meetingTarget.equals(e.meetingTarget)
                && editMeetingDescriptor.equals(e.editMeetingDescriptor);
    }

    /**
     * Stores the details to edit the meeting with. Each non-empty field value will replace the
     * corresponding field value of the meeting.
     */
    public static class EditMeetingDescriptor {
        private Title title;
        private Link link;
        private StartTime startTime;
        private Duration duration;
        private Set<Tag> tags;

        public EditMeetingDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMeetingDescriptor(EditMeetingDescriptor toCopy) {
            setTitle(toCopy.title);
            setLink(toCopy.link);
            setStartTime(toCopy.startTime);
            setDuration(toCopy.duration);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, link, startTime, duration, tags);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setLink(Link link) {
            this.link = link;
        }

        public Optional<Link> getLink() {
            return Optional.ofNullable(link);
        }

        public void setStartTime(StartTime startTime) {
            this.startTime = startTime;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<StartTime> getStartTime() {
            return Optional.ofNullable(this.startTime);
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(this.duration);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Creates and returns a {@code Meeting} with the details of {@code meetingToEdit}
         * edited with {@code editMeetingDescriptor}.
         */
        public static Meeting createEditedMeeting(Meeting meetingToEdit, EditMeetingDescriptor editMeetingDescriptor) {
            assert meetingToEdit != null;

            Title updatedTitle = editMeetingDescriptor.getTitle().orElse(meetingToEdit.getTitle());
            Link updatedLink = editMeetingDescriptor.getLink().orElse(meetingToEdit.getLink());
            StartTime updatedStartTime = editMeetingDescriptor.getStartTime().orElse(meetingToEdit.getStartTime());
            Duration updatedDuration = editMeetingDescriptor.getDuration().orElse(meetingToEdit.getDuration());
            Set<Tag> updatedTags = editMeetingDescriptor.getTags().orElse(meetingToEdit.getTags());

            return new Meeting(updatedTitle, updatedLink, updatedStartTime, updatedDuration, updatedTags);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMeetingDescriptor)) {
                return false;
            }

            // state check
            EditMeetingDescriptor e = (EditMeetingDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getStartTime().equals(e.getStartTime())
                    && getTags().equals(e.getTags());
        }
    }
}
