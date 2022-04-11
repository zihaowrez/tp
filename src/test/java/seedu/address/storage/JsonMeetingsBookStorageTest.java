package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMeetings.CS2030_PE;
import static seedu.address.testutil.TypicalMeetings.CS2103_FINAL;
import static seedu.address.testutil.TypicalMeetings.CS3230_MEETING;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.MeetingsBook;
import seedu.address.model.ReadOnlyMeetingsBook;

public class JsonMeetingsBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMeetingsBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readMeetingsBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readMeetingsBook(null));
    }

    private java.util.Optional<ReadOnlyMeetingsBook> readMeetingsBook(String filePath) throws Exception {
        return new JsonMeetingsBookStorage(Paths.get(filePath)).readMeetingsBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMeetingsBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readMeetingsBook("notJsonFormatMeetingsBook.json"));
    }

    @Test
    public void readMeetingsBook_invalidMeetingMeetingsBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMeetingsBook("invalidMeetingMeetingsBook.json"));
    }

    @Test
    public void readMeetingsBook_invalidAndValidMeetingMeetingsBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readMeetingsBook("invalidAndValidMeetingMeetingsBook.json"));
    }

    @Test
    public void readAndSaveMeetingsBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempMeetingsBook.json");
        MeetingsBook original = getTypicalMeetingsBook();
        JsonMeetingsBookStorage jsonMeetingsBookStorage = new JsonMeetingsBookStorage(filePath);

        // Save in new file and read back
        jsonMeetingsBookStorage.saveMeetingsBook(original, filePath);
        ReadOnlyMeetingsBook readBack = jsonMeetingsBookStorage.readMeetingsBook(filePath).get();
        assertEquals(original, new MeetingsBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMeeting(CS2030_PE);
        original.removeMeeting(CS3230_MEETING);
        jsonMeetingsBookStorage.saveMeetingsBook(original, filePath);
        readBack = jsonMeetingsBookStorage.readMeetingsBook(filePath).get();
        assertEquals(original, new MeetingsBook(readBack));

        // Save and read without specifying file path
        original.addMeeting(CS2103_FINAL);
        jsonMeetingsBookStorage.saveMeetingsBook(original); // file path not specified
        readBack = jsonMeetingsBookStorage.readMeetingsBook().get(); // file path not specified
        assertEquals(original, new MeetingsBook(readBack));

    }

    @Test
    public void saveMeetingsBook_nullMeetingsBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMeetingsBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code meetingsBook} at the specified {@code filePath}.
     */
    private void saveMeetingsBook(ReadOnlyMeetingsBook meetingsBook, String filePath) {
        try {
            new JsonMeetingsBookStorage(Paths.get(filePath))
                    .saveMeetingsBook(meetingsBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMeetingsBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveMeetingsBook(new MeetingsBook(), null));
    }
}
