package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingsTab;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.ui.CommandBox;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command under the Contacts tab and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult executeForContacts(String commandText, CommandBox commandBox) throws CommandException, ParseException;

    /**
     * Executes the command under the Meetings tab and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult executeForMeetings(String commandText, CommandBox commandBox) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getContactDetails();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    ReadOnlyMeetingsTab getMeetingsTab();

    ObservableList<Meeting> getFilteredMeetingList();

    Path getMeetingsTabFilePath();


    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
