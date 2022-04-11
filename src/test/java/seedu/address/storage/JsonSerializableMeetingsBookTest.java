package seedu.meeting.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.MeetingsBook;
import seedu.address.storage.JsonSerializableMeetingsBook;
import seedu.address.testutil.TypicalMeetings;

public class JsonSerializableMeetingsBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableMeetingsBookTest");
    private static final Path TYPICAL_MEETINGS_FILE = TEST_DATA_FOLDER.resolve("typicalMeetingsMeetingsBook.json");
    private static final Path INVALID_MEETING_FILE = TEST_DATA_FOLDER.resolve("invalidMeetingMeetingsBook.json");
    private static final Path DUPLICATE_MEETING_FILE = TEST_DATA_FOLDER.resolve("duplicateMeetingMeetingsBook.json");

    @Test
    public void toModelType_typicalMeetingsFile_success() throws Exception {
        JsonSerializableMeetingsBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEETINGS_FILE,
                JsonSerializableMeetingsBook.class).get();
        MeetingsBook meetingsBookFromFile = dataFromFile.toModelType();
        MeetingsBook typicalMeetingsMeetingsBook = TypicalMeetings.getTypicalMeetingsBook();
        assertEquals(meetingsBookFromFile, typicalMeetingsMeetingsBook);
    }

    @Test
    public void toModelType_invalidMeetingFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMeetingsBook dataFromFile = JsonUtil.readJsonFile(INVALID_MEETING_FILE,
                JsonSerializableMeetingsBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateMeetings_throwsIllegalValueException() throws Exception {
        JsonSerializableMeetingsBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEETING_FILE,
                JsonSerializableMeetingsBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMeetingsBook.MESSAGE_DUPLICATE_MEETINGS,
                dataFromFile::toModelType);
    }

}
