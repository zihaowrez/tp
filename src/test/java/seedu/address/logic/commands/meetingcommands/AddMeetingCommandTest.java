package seedu.address.logic.commands.meetingcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.MeetingsBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingsBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.MeetingBuilder;


public class AddMeetingCommandTest {

    @Test
    public void constructor_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMeetingCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMeetingAdded modelStub = new ModelStubAcceptingMeetingAdded();
        Meeting validMeeting = new MeetingBuilder().build();
        CommandResult commandResult = new AddMeetingCommand(validMeeting).execute(modelStub);

        assertEquals(String.format(AddMeetingCommand.MESSAGE_SUCCESS, validMeeting), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeeting), modelStub.meetingsAdded);
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() {
        Meeting validMeeting = new MeetingBuilder().build();
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(validMeeting);
        ModelStub modelStub = new ModelStubWithMeeting(validMeeting);

        assertThrows(CommandException.class,
                AddMeetingCommand.MESSAGE_DUPLICATE_MEETING, () -> addMeetingCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Meeting cs2103Meeting = new MeetingBuilder().withTitle("CS2103 Meeting").build();
        Meeting cs3230Meeting = new MeetingBuilder().withTitle("CS3230 Meeting").build();
        AddMeetingCommand addCs2103 = new AddMeetingCommand(cs2103Meeting);
        AddMeetingCommand addCs3230 = new AddMeetingCommand(cs3230Meeting);

        // same object -> returns true
        assertTrue(cs2103Meeting.equals(cs2103Meeting));

        // same values -> returns true
        AddMeetingCommand addCS2103Copy = new AddMeetingCommand(cs2103Meeting);
        assertTrue(addCs2103.equals(addCS2103Copy));

        // different types -> returns false
        assertFalse(addCs2103.equals(1));

        // null -> returns false
        assertFalse(addCs2103.equals(null));

        // different person -> returns false
        assertFalse(addCs2103.equals(addCs3230));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void copyPerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getSortedAndFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredPersonList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSelectedPerson(Person newPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableObjectValue<Person> getCurrentlySelectedPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableIntegerValue getSelectedIndex() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSelectedIndex(Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getMeetingsBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMeetingsBookFilePath(Path path) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMeetingsBook(ReadOnlyMeetingsBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void copyTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTag(Tag target, Tag editedTag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ReadOnlyMeetingsBook getMeetingsBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void deleteMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void copyMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMeeting(Meeting meeting) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMeeting(Meeting target, Meeting editedMeeting) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void sortFilteredMeetingList(Comparator<Meeting> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meeting> getSortedAndFilteredMeetingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMeetingList(Predicate<Meeting> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredUpcomingMeetingList(Predicate<Meeting> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredUpcomingMeetingList(Comparator<Meeting> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meeting> getUpcomingMeetingList() {
            throw new AssertionError("This method should not be called.");
        }



    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithMeeting extends ModelStub {
        private final Meeting meeting;

        ModelStubWithMeeting(Meeting meeting) {
            requireNonNull(meeting);
            this.meeting = meeting;
        }

        @Override
        public boolean hasMeeting(Meeting meeting) {
            requireNonNull(meeting);
            return this.meeting.isSameMeeting(meeting);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingMeetingAdded extends ModelStub {
        final ArrayList<Meeting> meetingsAdded = new ArrayList<>();

        @Override
        public boolean hasMeeting(Meeting meeting) {
            requireNonNull(meeting);
            return meetingsAdded.stream().anyMatch(meeting::isSameMeeting);
        }

        @Override
        public void addMeeting(Meeting meeting) {
            requireNonNull(meeting);
            meetingsAdded.add(meeting);
        }

        @Override
        public ReadOnlyMeetingsBook getMeetingsBook() {
            return new MeetingsBook();
        }
    }

}
