package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingStartTimeSorter;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;

    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Tag> filteredTags;
    private FilteredList<Person> contactDetails;
    private final SimpleObjectProperty<Person> currentlySelectedPersonProperty;
    private final SimpleIntegerProperty selectionIndex;
    private final SortedList<Person> sortedAndFilteredPersons;

    private final MeetingsBook meetingsBook;
    private final FilteredList<Meeting> filteredMeetings;
    private final SortedList<Meeting> sortedAndFilteredMeetings;
    private final FilteredList<Meeting> filteredUpcomingMeetings;
    private final SortedList<Meeting> sortedAndFilteredUpcomingMeetings;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyMeetingsBook meetingsBook,
                        ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.meetingsBook = new MeetingsBook(meetingsBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.currentlySelectedPersonProperty = new SimpleObjectProperty<Person>();
        selectionIndex = new SimpleIntegerProperty();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        contactDetails = new FilteredList<>(this.addressBook.getPersonList());
        filteredMeetings = new FilteredList<>(this.meetingsBook.getMeetingList());
        filteredUpcomingMeetings = new FilteredList<>(this.meetingsBook.getMeetingList());
        resetContactDetails();

        filteredTags = new FilteredList<>(this.addressBook.getTagList());
        sortedAndFilteredPersons = filteredPersons.sorted();
        sortedAndFilteredMeetings = filteredMeetings.sorted();
        sortedAndFilteredUpcomingMeetings = filteredUpcomingMeetings.sorted();

    }

    public ModelManager() {
        this(new AddressBook(), new MeetingsBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void copyPerson(Person target) {
        addressBook.copyPerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        sortFilteredPersonList(COMPARATOR_ALPHABETICAL_ORDER);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
        sortFilteredPersonList(COMPARATOR_ALPHABETICAL_ORDER);
    }

    //=========== Meetings tab =================================================================================
    @Override
    public Path getMeetingsBookFilePath() {
        return this.userPrefs.getMeetingsBookFilePath();
    }

    @Override
    public void setMeetingsBookFilePath(Path meetingsBookFilePath) {
        requireNonNull(meetingsBookFilePath);
        userPrefs.setMeetingsBookFilePath(meetingsBookFilePath);
    }


    @Override
    public void setMeetingsBook(ReadOnlyMeetingsBook meetingsBook) {
        this.meetingsBook.resetData(meetingsBook);
    }

    @Override
    public ReadOnlyMeetingsBook getMeetingsBook() {
        return meetingsBook;
    }

    @Override
    public boolean hasMeeting(Meeting meeting) {
        requireNonNull(meeting);
        return meetingsBook.hasMeeting(meeting);
    }

    @Override
    public void deleteMeeting(Meeting target) {
        meetingsBook.removeMeeting(target);
    }

    @Override
    public void copyMeeting(Meeting target) {
        meetingsBook.copyMeeting(target);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetingsBook.addMeeting(meeting);
        updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
    }

    @Override
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        requireAllNonNull(target, editedMeeting);

        meetingsBook.setMeeting(target, editedMeeting);
    }


    @Override
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return addressBook.hasTag(tag);
    }

    @Override
    public void deleteTag(Tag tag) {
        addressBook.removeTag(tag);
    }

    @Override
    public void copyTag(Tag tag) {
        addressBook.copyTag(tag);
    }

    @Override
    public void addTag(Tag tag) {
        addressBook.addTag(tag, "head");
        updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
    }

    @Override
    public void setTag(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);

        addressBook.setTag(target, editedTag);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getSortedAndFilteredPersonList() {
        return sortedAndFilteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Tag List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tag} backed by the internal list of
     * {@code versionedAddressBook}
     */

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return filteredTags;
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

    //============ Currently Selected Person Accessors ===================================================


    @Override
    public void updateSelectedPerson(Person newPerson) {
        currentlySelectedPersonProperty.set(newPerson);
    }

    @Override
    public void sortFilteredPersonList(Comparator<Person> comparator) {
        requireNonNull(comparator);
        sortedAndFilteredPersons.setComparator(comparator);
    }

    @Override
    public ObservableObjectValue<Person> getCurrentlySelectedPerson() {
        return currentlySelectedPersonProperty;
    }

    private void resetContactDetails() {
        contactDetails.setPredicate(p -> false);
    }

    public ObservableIntegerValue getSelectedIndex() {
        return selectionIndex;
    }

    @Override
    public void updateSelectedIndex(Index newIndex) {
        if (newIndex == null) {
            selectionIndex.setValue(-1);
        } else {
            selectionIndex.setValue(newIndex.getZeroBased());
        }
    }

    @Override
    public ObservableList<Meeting> getSortedAndFilteredMeetingList() {
        return sortedAndFilteredMeetings;
    }

    @Override
    public void updateFilteredMeetingList(Predicate<Meeting> predicate) {
        requireNonNull(predicate);
        filteredMeetings.setPredicate(predicate);
    }

    @Override
    public void sortFilteredMeetingList(Comparator<Meeting> comparator) {
        requireNonNull(comparator);
        sortedAndFilteredMeetings.setComparator(comparator);
    }

    @Override
    public ObservableList<Meeting> getUpcomingMeetingList() {
        sortFilteredUpcomingMeetingList(new MeetingStartTimeSorter());
        return sortedAndFilteredUpcomingMeetings;
    }

    @Override
    public void updateFilteredUpcomingMeetingList(Predicate<Meeting> predicate) {
        requireNonNull(predicate);
        filteredUpcomingMeetings.setPredicate(predicate);
    }

    @Override
    public void sortFilteredUpcomingMeetingList(Comparator<Meeting> comparator) {
        requireNonNull(comparator);
        sortedAndFilteredUpcomingMeetings.setComparator(comparator);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }
}
