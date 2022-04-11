package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMeetings.CS2103_FINAL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.meeting.Title;

public class JsonAdaptedMeetingTest {
    private static final String INVALID_TITLE = " ";
    private static final String INVALID_LINK = "www";
    private static final String INVALID_STARTTIME = "2022";
    private static final String INVALID_DURATION = "0";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TITLE = CS2103_FINAL.getTitle().title;
    private static final String VALID_LINK = CS2103_FINAL.getLink().link;
    private static final String VALID_STARTTIME = CS2103_FINAL.getStartTime().jsonStartTime();
    private static final String VALID_DURATION = CS2103_FINAL.getDuration().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = CS2103_FINAL.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validMeetingDetails_returnsMeeting() throws Exception {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(CS2103_FINAL);
        assertEquals(CS2103_FINAL, meeting.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(INVALID_TITLE, VALID_LINK, VALID_STARTTIME, VALID_DURATION, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(null, VALID_LINK, VALID_STARTTIME, VALID_DURATION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidLink_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, INVALID_LINK, VALID_STARTTIME, VALID_DURATION, VALID_TAGS);
        String expectedMessage = Link.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullLink_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, null, VALID_STARTTIME, VALID_DURATION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Link.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, VALID_LINK, INVALID_STARTTIME, VALID_DURATION, VALID_TAGS);
        String expectedMessage = StartTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, VALID_LINK, null, VALID_DURATION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, VALID_LINK, VALID_STARTTIME, INVALID_DURATION, VALID_TAGS);
        String expectedMessage = Duration.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, VALID_LINK, VALID_STARTTIME, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Duration.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, VALID_LINK, VALID_STARTTIME, VALID_DURATION, invalidTags);
        assertThrows(IllegalValueException.class, meeting::toModelType);
    }

}
