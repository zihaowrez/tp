package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyMeetingsBook;

/**
 * A class to access MeetingsBook data stored as a json file on the hard disk.
 */
public class JsonMeetingsBookStorage implements MeetingsBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMeetingsBookStorage.class);

    private Path filePath;

    public JsonMeetingsBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMeetingsBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMeetingsBook> readMeetingsBook() throws DataConversionException {
        return readMeetingsBook(filePath);
    }

    /**
     * Similar to {@link #readMeetingsBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMeetingsBook> readMeetingsBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableMeetingsBook> jsonMeetingsBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableMeetingsBook.class);
        if (!jsonMeetingsBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMeetingsBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMeetingsBook(ReadOnlyMeetingsBook meetingsBook) throws IOException {
        saveMeetingsBook(meetingsBook, filePath);
    }

    /**
     * Similar to {@link #saveMeetingsBook(ReadOnlyMeetingsBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMeetingsBook(ReadOnlyMeetingsBook meetingsBook, Path filePath) throws IOException {
        requireNonNull(meetingsBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMeetingsBook(meetingsBook), filePath);
    }

}
