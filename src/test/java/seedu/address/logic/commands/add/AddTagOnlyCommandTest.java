package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTags.ENEMIES;
import static seedu.address.testutil.TypicalTags.FRIENDS;

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
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingsBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

class AddTagOnlyCommandTest {

    @Test
    public void execute_tagAcceptedByModel_addSuccessful() throws Exception {
        AddTagOnlyCommandTest.ModelStubAcceptingTagAdded modelStub =
                new AddTagOnlyCommandTest.ModelStubAcceptingTagAdded();

        CommandResult commandResult = new AddTagOnlyCommand(ENEMIES).execute(modelStub);

        assertEquals(String.format(AddTagOnlyCommand.MESSAGE_ADD_NEW_TAG_SUCCESS, ENEMIES),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(ENEMIES), modelStub.tagsAdded);
    }

    @Test
    public void execute_duplicateTag_throwsCommandException() {
        AddCommand addCommand = new AddTagOnlyCommand(ENEMIES);
        AddTagOnlyCommandTest.ModelStub modelStub = new AddTagOnlyCommandTest.ModelStubWithTag(ENEMIES);

        assertThrows(CommandException.class,
            String.format(AddTagOnlyCommand.MESSAGE_TAG_ALREADY_EXISTS,
                ENEMIES), () -> addCommand.execute(modelStub));
    }

    @Test
    void testEquals() {
        AddCommand addFriendsCommand = new AddTagOnlyCommand(FRIENDS);
        AddCommand addEnemiesCommand = new AddTagOnlyCommand(ENEMIES);

        // same object -> returns true
        assertTrue(addFriendsCommand.equals(addFriendsCommand));

        // same values -> returns true
        AddCommand addFriendsCommandCopy = new AddTagOnlyCommand(FRIENDS);
        assertTrue(addFriendsCommand.equals(addFriendsCommandCopy));

        // different types -> returns false
        assertFalse(addFriendsCommand.equals(1));

        // null -> returns false
        assertFalse(addFriendsCommand.equals(null));

        // different tag -> returns false
        assertFalse(addFriendsCommand.equals(addEnemiesCommand));
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
            return null;
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getSortedAndFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
        public void updateSelectedPerson(Person newPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableObjectValue<Person> getCurrentlySelectedPerson() {
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
        public ObservableIntegerValue getSelectedIndex() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMeeting(Meeting target, Meeting editedMeeting) {
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
        public void updateSelectedIndex(Index newIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredPersonList(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredMeetingList(Comparator<Meeting> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meeting> getUpcomingMeetingList() {
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
    }


    /**
     * A Model stub that contains a single tag.
     */
    private class ModelStubWithTag extends AddTagOnlyCommandTest.ModelStub {
        private final Tag tag;

        ModelStubWithTag(Tag tag) {
            requireNonNull(tag);
            this.tag = tag;
        }

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return this.tag.isSameTag(tag);
        }
    }

    /**
     * A Model stub that always accept the tag being added.
     */
    private class ModelStubAcceptingTagAdded extends AddTagOnlyCommandTest.ModelStub {
        final ArrayList<Tag> tagsAdded = new ArrayList<>();

        @Override
        public boolean hasTag(Tag tag) {
            requireNonNull(tag);
            return tagsAdded.stream().anyMatch(tag::isSameTag);
        }

        @Override
        public void addTag(Tag tag) {
            requireNonNull(tag);
            tagsAdded.add(tag);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
