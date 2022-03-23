package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingsTab;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface MeetingsTabStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMeetingsTabFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMeetingsTab> readMeetingsTab() throws DataConversionException, IOException;

    /**
     * @see #getMeetingsTabFilePath()
     */
    Optional<ReadOnlyMeetingsTab> readMeetingsTab(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMeetingsTab} to the storage.
     * @param meetingsTab cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMeetingsTab(ReadOnlyMeetingsTab meetingsTab) throws IOException;

    /**
     * @see #saveMeetingsTab(ReadOnlyMeetingsTab)
     */
    void saveMeetingsTab(ReadOnlyMeetingsTab meetingsTab, Path filePath) throws IOException;

}
