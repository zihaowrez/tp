package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Comparator<Person> COMPARATOR_ALPHABETICAL_ORDER = (p1, p2) ->
            p1.getName().fullName.compareToIgnoreCase(p2.getName().fullName);

    Predicate<Tag> PREDICATE_SHOW_ALL_TAGS = unused -> true;

    Predicate<Meeting> PREDICATE_SHOW_ALL_MEETINGS = unused -> true;


    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    //-----------------Address Book ----------------------------------------------------//

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Copies the given person.
     * The person must exist in the address book.
     */
    void copyPerson(Person target);


    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns true if a tag with the same identity as {@code tag} exists in the address book.
     */
    boolean hasTag(Tag tag);

    /**
     * Deletes the given tag.
     * The tag must exist in the address book.
     */
    void deleteTag(Tag tag);

    /**
     * Copies the given tag.
     * The tag must exist in the address book.
     */
    void copyTag(Tag tag);

    /**
     * Adds the given tag.
     * {@code tag} must not already exist in the address book.
     */
    void addTag(Tag tag);

    /**
     * Replaces the given tag {@code target} with {@code editedTag}.
     * {@code target} must exist in the address book.
     * The tag identity of {@code editedTag} must not be the same as another existing tag in the address book.
     */
    void setTag(Tag target, Tag editedTag);

    /** Returns an unmodifiable view of the filtered tag list */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getSortedAndFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Sorts the filter of the filtered person list to filter by the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortFilteredPersonList(Comparator<Person> comparator);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateSelectedPerson(Person newPerson);

    /**
     * Returns an observable value of the currently selected person.
     */
    ObservableObjectValue<Person> getCurrentlySelectedPerson();

    ObservableIntegerValue getSelectedIndex();


    //-----------------Meetings Book ----------------------------------------------------//

    Path getMeetingsBookFilePath();

    void setMeetingsBookFilePath(Path meetingsBookFilePath);

    void setMeetingsBook(ReadOnlyMeetingsBook meetingsBook);

    ReadOnlyMeetingsBook getMeetingsBook();

    void deleteMeeting(Meeting meeting);

    void copyMeeting(Meeting meeting);

    boolean hasMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);

    void setMeeting(Meeting target, Meeting editedMeeting);

    ObservableList<Meeting> getSortedAndFilteredMeetingList();

    void updateFilteredMeetingList(Predicate<Meeting> predicate);

    void sortFilteredMeetingList(Comparator<Meeting> comparator);

    ObservableList<Meeting> getUpcomingMeetingList();

    void updateFilteredUpcomingMeetingList(Predicate<Meeting> predicate);

    void sortFilteredUpcomingMeetingList(Comparator<Meeting> comparator);

    /**
     * Updates the selected index to with a new {@code Index}.
     * @param newIndex The new index to be selected.
     */
    void updateSelectedIndex(Index newIndex);
}
