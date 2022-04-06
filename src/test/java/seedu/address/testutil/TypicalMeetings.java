package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.MeetingsBook;
import seedu.address.model.meeting.Meeting;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalMeetings {

    public static final Meeting CS2103_MEETING = new MeetingBuilder().withTitle("CS2103 Meeting")
            .withLink("https://zoom.sg")
            .withStartTime("2022-3-7 1800")
            .withDuration(120)
            .withTags("cs2103").build();
    public static final Meeting CS3230_PE = new MeetingBuilder().withTitle("CS3230 PE")
            .withLink("https://zoom.sg")
            .withStartTime("2022-4-14 0900")
            .withDuration(60)
            .withTags("cs3230").build();
    public static final Meeting CS2103_FINAL = new MeetingBuilder().withTitle("CS2103 Final")
            .withLink("https://zoom.sg")
            .withStartTime("2022-5-5 1700")
            .withDuration(150)
            .withTags("final", "cs2103").build();

    public static final String KEYWORD_MATCHING_MEIER = "cs2103"; // A keyword that matches cs2103

    private TypicalMeetings() {} // prevents instantiation

    /**
     * Returns an {@code MeetingsTab} with all the typical meetings.
     */
    public static MeetingsBook getTypicalMeetingsBook() {
        MeetingsBook mb = new MeetingsBook();
        for (Meeting meeting : getTypicalMeetings()) {
            mb.addMeeting(meeting);
        }
        return mb;
    }

    public static List<Meeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(CS2103_MEETING, CS3230_PE, CS2103_FINAL));
    }
}
