package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOCIAL_GMAIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOCIAL_TELEGRAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.MeetingsBook;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalMeetings {

    public static final Meeting CS2103_MEETING = new MeetingBuilder().withTitle("CS2103 Meeting")
            .withLink("https://zoom.sg")
            .withStartTime("2018-10-10 1800")
            .withDuration(60)
            .withTags("cs2103").build();
    public static final Meeting CS3230_MEETING = new MeetingBuilder().withTitle("CS3230 Meeting")
            .withLink("https://zoom.sg")
            .withStartTime("2019-10-10 1800")
            .withDuration(60)
            .withTags("cs3230").build();
    public static final Meeting PROJECT_MEETING = new MeetingBuilder().withTitle("Project Meeting")
            .withLink("https://zoom.sg")
            .withStartTime("2020-10-10 1800")
            .withDuration(50)
            .withTags("project").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withSocials(VALID_SOCIAL_GMAIL)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)//.withAddress(VALID_ADDRESS_BOB)
            .withSocials(VALID_SOCIAL_TELEGRAM, VALID_SOCIAL_GMAIL)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMeetings() {} // prevents instantiation

    /**
     * Returns an {@code MeetingsTab} with all the typical meetings.
     */
    public static MeetingsBook getTypicalMeetingsBook() {
        MeetingsBook ab = new MeetingsBook();
        for (Meeting meeting : getTypicalMeetings()) {
            ab.addMeeting(meeting);
        }
        return ab;
    }

    public static List<Meeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(CS2103_MEETING, CS3230_MEETING, PROJECT_MEETING));
    }
}
