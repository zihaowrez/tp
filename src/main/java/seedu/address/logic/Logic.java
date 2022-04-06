package seedu.address.logic;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingsBook;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
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
     * Returns the result of clicking the tag.
     * @param tag The tag clicked.
     * @return the result of clicking the tag.
     */
    CommandResult[] clickTag(Tag tag);

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the sorted and filtered list of persons */
    ObservableList<Person> getSortedAndFilteredPersonList();

    void updateFilteredPersonList(Predicate<Person> predicate);

    void updateFilteredMeetingList(Predicate<Meeting> predicate);

    /** Returns a view of the currently selected person */
    ObservableObjectValue<Person> getCurrentlySelectedPerson();

    /** Set the currently selected Person */
    void setCurrentlySelectedPerson(Person newPerson);

    ObservableIntegerValue getObservableIndex();

    void setObservableIndex(Index index);

    /** Returns an unmodifiable view of the filtered list of tags */
    public ObservableList<Tag> getFilteredTagList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the MeetingsBook.
     *
     * @see seedu.address.model.Model#getMeetingsBook()
     */
    ReadOnlyMeetingsBook getMeetingsBook();

    /** Returns an unmodifiable view of the sorted and filtered list of meetings */
    ObservableList<Meeting> getSortedAndFilteredMeetingList();

    /** Returns an unmodifiable view of the sorted and filtered list of meetings */
    ObservableList<Meeting> getUpcomingMeetingList();

    Path getMeetingsBookFilePath();


    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
