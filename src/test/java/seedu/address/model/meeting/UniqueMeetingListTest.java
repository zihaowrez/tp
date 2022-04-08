package seedu.address.model.meeting;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMeetings.CS2103_MEETING;
import static seedu.address.testutil.TypicalMeetings.CS3230_MEETING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
import seedu.address.model.meeting.exceptions.MeetingNotFoundException;
import seedu.address.testutil.MeetingBuilder;

public class UniqueMeetingListTest {

    private final UniqueMeetingList uniqueMeetingList = new UniqueMeetingList();

    @Test
    public void contains_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.contains(null));
    }

    @Test
    public void contains_meetingNotInList_returnsFalse() {
        Assertions.assertFalse(uniqueMeetingList.contains(CS2103_MEETING));
    }

    @Test
    public void contains_meetingInList_returnsTrue() {
        uniqueMeetingList.add(CS2103_MEETING);
        Assertions.assertTrue(uniqueMeetingList.contains(CS2103_MEETING));
    }

    @Test
    public void contains_meetingWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMeetingList.add(CS2103_MEETING);
        Meeting editedAlice = new MeetingBuilder(CS2103_MEETING)//.withAddress(VALID_ADDRESS_CS3230_MEETING)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        Assertions.assertTrue(uniqueMeetingList.contains(editedAlice));
    }

    @Test
    public void add_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.add(null));
    }

    @Test
    public void add_duplicateMeeting_throwsDuplicateMeetingException() {
        uniqueMeetingList.add(CS2103_MEETING);
        assertThrows(DuplicateMeetingException.class, () -> uniqueMeetingList.add(CS2103_MEETING));
    }

    @Test
    public void setMeeting_nullTargetMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.setMeeting(null, CS2103_MEETING));
    }

    @Test
    public void setMeeting_nullEditedMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.setMeeting(CS2103_MEETING, null));
    }

    @Test
    public void setMeeting_targetMeetingNotInList_throwsMeetingNotFoundException() {
        assertThrows(MeetingNotFoundException.class, () ->
                uniqueMeetingList.setMeeting(CS2103_MEETING, CS2103_MEETING));
    }

    @Test
    public void setMeeting_editedMeetingIsSameMeeting_success() {
        uniqueMeetingList.add(CS2103_MEETING);
        uniqueMeetingList.setMeeting(CS2103_MEETING, CS2103_MEETING);
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        expectedUniqueMeetingList.add(CS2103_MEETING);
        Assertions.assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeeting_editedMeetingHasSameIdentity_success() {
        uniqueMeetingList.add(CS2103_MEETING);
        Meeting editedAlice = new MeetingBuilder(CS2103_MEETING)//.withAddress(VALID_ADDRESS_CS3230_MEETING)
                .withTags(VALID_TAG_HUSBAND).build();
        uniqueMeetingList.setMeeting(CS2103_MEETING, editedAlice);
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        expectedUniqueMeetingList.add(editedAlice);
        Assertions.assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeeting_editedMeetingHasDifferentIdentity_success() {
        uniqueMeetingList.add(CS2103_MEETING);
        uniqueMeetingList.setMeeting(CS2103_MEETING, CS3230_MEETING);
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        expectedUniqueMeetingList.add(CS3230_MEETING);
        Assertions.assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeeting_editedMeetingHasNonUniqueIdentity_throwsDuplicateMeetingException() {
        uniqueMeetingList.add(CS2103_MEETING);
        uniqueMeetingList.add(CS3230_MEETING);
        assertThrows(DuplicateMeetingException.class, () ->
                uniqueMeetingList.setMeeting(CS2103_MEETING, CS3230_MEETING));
    }

    @Test
    public void remove_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.remove(null));
    }

    @Test
    public void remove_meetingDoesNotExist_throwsMeetingNotFoundException() {
        assertThrows(MeetingNotFoundException.class, () -> uniqueMeetingList.remove(CS2103_MEETING));
    }

    @Test
    public void remove_existingMeeting_removesMeeting() {
        uniqueMeetingList.add(CS2103_MEETING);
        uniqueMeetingList.remove(CS2103_MEETING);
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        Assertions.assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeetings_nullUniqueMeetingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.setMeetings((UniqueMeetingList) null));
    }

    @Test
    public void setMeetings_uniqueMeetingList_replacesOwnListWithProvidedUniqueMeetingList() {
        uniqueMeetingList.add(CS2103_MEETING);
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        expectedUniqueMeetingList.add(CS3230_MEETING);
        uniqueMeetingList.setMeetings(expectedUniqueMeetingList);
        Assertions.assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeetings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.setMeetings((List<Meeting>) null));
    }

    @Test
    public void setMeetings_list_replacesOwnListWithProvidedList() {
        uniqueMeetingList.add(CS2103_MEETING);
        List<Meeting> meetingList = Collections.singletonList(CS3230_MEETING);
        uniqueMeetingList.setMeetings(meetingList);
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        expectedUniqueMeetingList.add(CS3230_MEETING);
        Assertions.assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeetings_listWithDuplicateMeetings_throwsDuplicateMeetingException() {
        List<Meeting> listWithDuplicateMeetings = Arrays.asList(CS2103_MEETING, CS2103_MEETING);
        assertThrows(DuplicateMeetingException.class, () -> uniqueMeetingList.setMeetings(listWithDuplicateMeetings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueMeetingList.asUnmodifiableObservableList().remove(0));
    }
}
