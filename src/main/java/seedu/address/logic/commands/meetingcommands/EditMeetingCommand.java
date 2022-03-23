package seedu.address.logic.commands.meetingcommands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing meeting in the MeetingsTab.
 */
public class EditMeetingCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meeting identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_LINK + "LINK] "
            + "[" + PREFIX_START_DATETIME + "DATE AND TIME] "
            + "[" + PREFIX_START_DATETIME + "DATE AND TIME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINK + "https://zoom.sg/123456/CS2103 ";

    public static final String MESSAGE_EDIT_MEETING_SUCCESS = "Edited Meeting: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEETING= "This meeting already exists in the address book.";

    private final Index index;
    private final seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor editMeetingDescriptor;

    /**
     * @param index of the person in the filtered meeting list to edit
     * @param editMeetingDescriptor details to edit the meeting with
     */
    public EditMeetingCommand(Index index, seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor editMeetingDescriptor) {
        requireNonNull(index);
        requireNonNull(editMeetingDescriptor);

        this.index = index;
        this.editMeetingDescriptor = new seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor(editMeetingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meeting> lastShownList = model.getFilteredMeetingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Meeting meetingToEdit = lastShownList.get(index.getZeroBased());
        Meeting editedMeeting = seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor.createEditedMeeting(meetingToEdit, editMeetingDescriptor);

        if (!meetingToEdit.isSameMeeting(editedMeeting) && model.hasMeeting(editedMeeting)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
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
        if (!(other instanceof seedu.address.logic.commands.meetingcommands.EditMeetingCommand)) {
            return false;
        }

        // state check
        seedu.address.logic.commands.meetingcommands.EditMeetingCommand e = (seedu.address.logic.commands.meetingcommands.EditMeetingCommand) other;
        return index.equals(e.index)
                && editMeetingDescriptor.equals(e.editMeetingDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditMeetingDescriptor {
        private MeetingName name;
        private Link link;
        private DateTime dateTime;
        private Set<Tag> tags;

        public EditMeetingDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMeetingDescriptor(seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor toCopy) {
            setName(toCopy.name);
            setLink(toCopy.link);
            setDateTime(toCopy.dateTime);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, dateTime, tags);
        }

        public void setName(MeetingName name) {
            this.name = name;
        }

        public Optional<MeetingName> getName() {
            return Optional.ofNullable(name);
        }

        public void setLink(Link link) {
            this.link = link;
        }

        public Optional<Link> getLink() {
            return Optional.ofNullable(link);
        }

        public void setDateTime(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<DateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
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
         * Creates and returns a {@code Person} with the details of {@code personToEdit}
         * edited with {@code editPersonDescriptor}.
         */
        public static Meeting createEditedMeeting(Meeting meetingToEdit, seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor editMeetingDescriptor) {
            assert meetingToEdit != null;

            MeetingName updatedName = editMeetingDescriptor.getName().orElse(meetingToEdit.getName());
            Link updatedLink = editMeetingDescriptor.getLink().orElse(meetingToEdit.getLink());
            DateTime updatedDateTime = editMeetingDescriptor.getDateTime().orElse(meetingToEdit.getDateTime());
            Set<Tag> updatedTags = editMeetingDescriptor.getTags().orElse(meetingToEdit.getTags());

            return new Meeting(updatedName, updatedLink, updatedDateTime, updatedTags);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor)) {
                return false;
            }

            // state check
            seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor e = (seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor) other;

            return getName().equals(e.getName())
                    && getDateTime().equals(e.getDateTime())
                    && getTags().equals(e.getTags());
        }
    }
}
