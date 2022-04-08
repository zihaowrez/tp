package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_INT_50;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_TEAMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_CS3230;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MeetingBuilder;
import seedu.address.testutil.TypicalMeetings;

public class MeetingTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Meeting meeting = new MeetingBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> meeting.getTags().remove(0));
    }

    @Test
    public void isSameMeeting() {
        // same object -> returns true
        assertTrue(TypicalMeetings.CS2103_MEETING.isSameMeeting(TypicalMeetings.CS2103_MEETING));

        // null -> returns false
        assertFalse(TypicalMeetings.CS2103_MEETING.isSameMeeting(null));

        // same name, all other attributes different -> returns true
        Meeting editedCs2103 = new MeetingBuilder(TypicalMeetings.CS2103_MEETING).withLink(VALID_LINK_TEAMS)
                .withDuration(VALID_DURATION_INT_50)
                .withTags(VALID_TAG_PROJECT).build();
        assertTrue(TypicalMeetings.CS2103_MEETING.isSameMeeting(editedCs2103));

        // different title, all other attributes same -> returns false
        editedCs2103 = new MeetingBuilder(TypicalMeetings.CS2103_MEETING).withTitle(VALID_TITLE_CS3230).build();
        assertFalse(TypicalMeetings.CS2103_MEETING.isSameMeeting(editedCs2103));

        // different start time, all other attributes same -> returns false
        editedCs2103 = new MeetingBuilder(TypicalMeetings.CS2103_MEETING).withStartTime(VALID_STARTTIME_CS3230).build();
        assertFalse(TypicalMeetings.CS2103_MEETING.isSameMeeting(editedCs2103));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Meeting cs2103Copy = new MeetingBuilder(TypicalMeetings.CS2103_MEETING).build();
        assertTrue(TypicalMeetings.CS2103_MEETING.equals(cs2103Copy));

        // same object -> returns true
        assertTrue(TypicalMeetings.CS2103_MEETING.equals(TypicalMeetings.CS2103_MEETING));

        // null -> returns false
        assertFalse(TypicalMeetings.CS2103_MEETING.equals(null));

        // different type -> returns false
        assertFalse(TypicalMeetings.CS2103_MEETING.equals(5));

        // different meeting -> returns false
        assertFalse(TypicalMeetings.CS2103_MEETING.equals(TypicalMeetings.CS3230_MEETING));

        // different title -> returns false
        Meeting editedCs2103 = new MeetingBuilder(TypicalMeetings.CS2103_MEETING).withTitle(VALID_TITLE_CS3230).build();
        assertFalse(TypicalMeetings.CS2103_MEETING.equals(editedCs2103));

        // different link -> returns false
        editedCs2103 = new MeetingBuilder(TypicalMeetings.CS2103_MEETING).withLink(VALID_LINK_TEAMS).build();
        assertFalse(TypicalMeetings.CS2103_MEETING.equals(editedCs2103));

        // different start time -> returns false
        editedCs2103 = new MeetingBuilder(TypicalMeetings.CS2103_MEETING).withStartTime(VALID_STARTTIME_CS3230).build();
        assertFalse(TypicalMeetings.CS2103_MEETING.equals(editedCs2103));

        // different address -> returns false
        // editedCs2103 = new MeetingBuilder(CS2103_MEETING).withAddress(VALID_ADDRESS_BOB).build();
        // assertFalse(CS2103_MEETING.equals(editedCs2103));

        // different tags -> returns false
        editedCs2103 = new MeetingBuilder(TypicalMeetings.CS2103_MEETING).withTags(VALID_TAG_PROJECT).build();
        assertFalse(TypicalMeetings.CS2103_MEETING.equals(editedCs2103));
    }
}
