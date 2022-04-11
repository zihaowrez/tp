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
            .withStartTime("2022-10-10 1800")
            .withDuration(60)
            .withTags("cs2103").build();
    public static final Meeting CS3230_MEETING = new MeetingBuilder().withTitle("CS3230 Meeting")
            .withLink("https://nus-sg.zoom.us/j/92307270969?pwd=VVMvNWFPWFpyVHRIcXR0VkJlNkg0dz09")
            .withStartTime("2022-1-10 0900")
            .withDuration(60)
            .withTags("cs3230").build();
    public static final Meeting PROJECT_MEETING = new MeetingBuilder().withTitle("Project Meeting")
            .withLink("https://teams.sg")
            .withStartTime("2024-10-10 1800")
            .withDuration(50)
            .withTags("project").build();
    public static final Meeting CS2030_PE = new MeetingBuilder().withTitle("CS2030 PE")
            .withLink("https://zoom.sg")
            .withStartTime("2022-4-1 0900")
            .withDuration(120)
            .withTags("PE").build();
    public static final Meeting CS2040_PE = new MeetingBuilder().withTitle("CS2040 PE")
            .withLink("https://zoom.sg")
            .withStartTime("2022-4-23 0900")
            .withDuration(120)
            .withTags("PE").build();
    public static final Meeting CS2103_FINAL = new MeetingBuilder().withTitle("CS2103 FINAL")
            .withLink("https://zoom.sg")
            .withStartTime("2022-5-5 0900")
            .withDuration(150)
            .withTags("FINAL").build();

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
        return new ArrayList<>(Arrays.asList(CS2103_MEETING, CS3230_MEETING, PROJECT_MEETING));
    }
}
