package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingsBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface MeetingsBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMeetingsBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMeetingsBook> readMeetingsBook() throws DataConversionException, IOException;

    /**
     * @see #getMeetingsBookFilePath()
     */
    Optional<ReadOnlyMeetingsBook> readMeetingsBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMeetingsBook} to the storage.
     * @param meetingsBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMeetingsBook(ReadOnlyMeetingsBook meetingsBook) throws IOException;

    /**
     * @see #saveMeetingsBook(ReadOnlyMeetingsBook)
     */
    void saveMeetingsBook(ReadOnlyMeetingsBook meetingsBook, Path filePath) throws IOException;

}
