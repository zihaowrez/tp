package seedu.address.logic.commands.meetingcommands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;

/**
 * Encapsulates a targeted Meeting on a given list of meeting, identified
 * either by {@code Name} or {@code Index}.
 */
public abstract class MeetingTarget {
    public static final String MESSAGE_MEETING_NOT_EXIST = "The meeting with this name %1$s does not exist!";
    private static final String NO_TARGET_LIST = "Cannot identify target on a list when no list is specified!";

    protected List<Meeting> meetings;

    private MeetingTarget(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    /**
     * Factory method for Target. Returns the correct subtype of target given a {@code target}.
     */
    public static seedu.address.logic.commands.meetingcommands.MeetingTarget of(MeetingName target, List<Meeting> meetings) {
        return new seedu.address.logic.commands.meetingcommands.MeetingTarget.NamedTarget(target, meetings);
    }

    /**
     * Factory method for Target. Returns the correct subtype of target given a {@code target}.
     */
    public static seedu.address.logic.commands.meetingcommands.MeetingTarget of(Index target, List<Meeting> meetings) {
        return new seedu.address.logic.commands.meetingcommands.MeetingTarget.IndexedTarget(target, meetings);
    }

    /**
     * Sets the list of persons that this target points to.
     * @param meetings The list of meetings this target points to.
     */
    public void setTargetList(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    /** Returns the {@code Person} that is target */
    public abstract Meeting targetMeeting() throws CommandException;

    @Override
    public abstract boolean equals(Object obj);

    /*----------------------Start of private classes---------------------------------------*/

    private static class NamedTarget extends seedu.address.logic.commands.meetingcommands.MeetingTarget {
        private MeetingName targetName;

        protected NamedTarget(MeetingName targetName) {
            super(null);
            this.targetName = targetName;
        }

        protected NamedTarget(MeetingName targetName, List<Meeting> meetings) {
            super(meetings);
            this.targetName = targetName;
        }

        @Override
        public Meeting targetMeeting() throws CommandException {
            if (meetings == null) {
                throw new CommandException(NO_TARGET_LIST);
            }

            Optional<Meeting> targetMeetingOptional = meetings
                    .stream()
                    .filter(meeting -> meeting.getName().equals(targetName))
                    .findFirst();

            if (targetMeetingOptional.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_MEETING_NOT_EXIST, targetName));
            }

            Meeting targettedMeeting = targetMeetingOptional.get();

            return targettedMeeting;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof seedu.address.logic.commands.meetingcommands.MeetingTarget.NamedTarget) {
                seedu.address.logic.commands.meetingcommands.MeetingTarget.NamedTarget other = (seedu.address.logic.commands.meetingcommands.MeetingTarget.NamedTarget) obj;
                Optional<List<Meeting>> personsOptional = Optional.ofNullable(meetings);
                return this.targetName.equals(other.targetName)
                        && personsOptional.equals(Optional.ofNullable(other.meetings));
            }

            return false;

        }
    }

    private static class IndexedTarget extends seedu.address.logic.commands.meetingcommands.MeetingTarget {
        private Index targetIndex;

        protected IndexedTarget(Index targetIndex) {
            super(null);
            this.targetIndex = targetIndex;
        }

        protected IndexedTarget(Index targetIndex, List<Meeting> meetings) {
            super(meetings);
            this.targetIndex = targetIndex;
        }

        @Override
        public Meeting targetMeeting() throws CommandException {
            if (meetings == null) {
                throw new CommandException(NO_TARGET_LIST);
            }

            if (targetIndex.getZeroBased() >= meetings.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Meeting targettedMeeting = meetings.get(targetIndex.getZeroBased());
            return targettedMeeting;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof seedu.address.logic.commands.meetingcommands.MeetingTarget.IndexedTarget) {
                seedu.address.logic.commands.meetingcommands.MeetingTarget.IndexedTarget other = (seedu.address.logic.commands.meetingcommands.MeetingTarget.IndexedTarget) obj;
                Optional<List<Meeting>> meetingsOptional = Optional.ofNullable(meetings);
                return this.targetIndex.equals(other.targetIndex)
                        && meetingsOptional.equals(Optional.ofNullable(other.meetings));
            }

            return false;

        }
    }
}
