package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_STRING_60;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_ZOOM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_CS2103;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMeetings.CS2103_MEETING;
import static seedu.address.testutil.TypicalMeetings.PROJECT_MEETING;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.meetingcommands.ListMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingsBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonMeetingsBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.MeetingBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.ui.CommandBox;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonMeetingsBookStorage meetingsBookStorage =
                new JsonMeetingsBookStorage(temporaryFolder.resolve("meetingsBook.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, meetingsBookStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void clickTag_nullTag_throwsAssertionError() {
        Tag tag = null;
        Person personWithTag = new PersonBuilder(AMY).withTags("friend").build();
        model.addPerson(personWithTag);
        model.addMeeting(CS2103_MEETING);
        assertThrows(AssertionError.class, () -> logic.clickTag(null));
    }

    @Test
    public void clickTag_filterContactListAndMeetingList() {
        Tag tag = new Tag("friend");

        // contacts
        Person personWithTag = new PersonBuilder(AMY).withTags("friend").build();
        model.addPerson(personWithTag);
        model.addMeeting(CS2103_MEETING);
        CommandResult[] actualCommandResults = logic.clickTag(tag);
        CommandResult[] expectedCommandResults = {
            new CommandResult("1" + Logic.getClickTagFeedbackToContacts(tag)),
            new CommandResult("0" + Logic.getClickTagFeedbackToMeetings(tag))
        };
        assertEquals(expectedCommandResults[0], actualCommandResults[0]);
        assertEquals(expectedCommandResults[1], actualCommandResults[1]);

        // meetings
        Meeting meetingWithTag = new MeetingBuilder(PROJECT_MEETING).withTags("friend").build();
        model.addMeeting(meetingWithTag);
        actualCommandResults = logic.clickTag(tag);
        expectedCommandResults[1] = new CommandResult("1" + Logic.getClickTagFeedbackToMeetings(tag));
        assertEquals(expectedCommandResults[0], actualCommandResults[0]);
        assertEquals(expectedCommandResults[1], actualCommandResults[1]);
    }

    @Test
    public void execute_contactsCommandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        CommandBox commandBox = null;
        assertContactsCommandException(deleteCommand, commandBox, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_meetingsCommandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        CommandBox commandBox = null;
        assertMeetingsCommandException(deleteCommand, commandBox, MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validContactsCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        CommandBox commandBox = null;
        int size = model.getSortedAndFilteredPersonList().size();
        assertContactsCommandSuccess(listCommand, commandBox, size + ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_validMeetingsCommand_success() throws Exception {
        String listMeetingCommand = ListMeetingCommand.COMMAND_WORD;
        CommandBox commandBox = null;
        int size = model.getSortedAndFilteredMeetingList().size();
        assertMeetingsCommandSuccess(listMeetingCommand, commandBox,
                size + ListMeetingCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_contactsStorageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonMeetingsBookStorage meetingsBookStorage =
                new JsonMeetingsBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionMeetingsTab.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, meetingsBookStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).withTags().withSocials().build();
        ModelManager expectedModel = new ModelManager();
        CommandBox commandBox = null;
        expectedModel.addPerson(expectedPerson);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertContactsCommandFailure(addCommand, commandBox, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void execute_meetingsStorageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonMeetingsBookStorage meetingsBookStorage =
                new JsonMeetingsBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionMeetingsTab.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, meetingsBookStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + " "
                + PREFIX_TITLE + VALID_TITLE_CS2103 + " "
                + PREFIX_LINK + VALID_LINK_ZOOM + " "
                + PREFIX_STARTTIME + VALID_STARTTIME_CS2103 + " "
                + PREFIX_DURATION + VALID_DURATION_STRING_60;
        Meeting expectedMeeting = new MeetingBuilder(CS2103_MEETING).withTags().build();
        ModelManager expectedModel = new ModelManager();
        CommandBox commandBox = null;
        expectedModel.addMeeting(expectedMeeting);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertMeetingsCommandFailure(addCommand, commandBox, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getSortedAndFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getSortedAndFilteredPersonList().remove(0));
    }

    @Test
    public void getSortedAndFilteredMeetingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                logic.getSortedAndFilteredMeetingList().remove(0));
    }

    /**
     * Executes the command for contacts and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertContactsCommandFailure(String, CommandBox, Class, String, Model)
     */
    private void assertContactsCommandSuccess(String inputCommand, CommandBox commandBox, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.executeForContacts(inputCommand, commandBox);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command for meetings and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertContactsCommandFailure(String, CommandBox, Class, String, Model)
     */
    private void assertMeetingsCommandSuccess(String inputCommand, CommandBox commandBox, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.executeForMeetings(inputCommand, commandBox);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command for contacts,
     * confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertContactsCommandFailure(String, CommandBox, Class, String, Model)
     */
    private void assertContactsCommandException(String inputCommand, CommandBox commandBox, String expectedMessage) {
        assertContactsCommandFailure(inputCommand, commandBox, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command for contacts,
     * confirms that the exception is thrown and that the result message is correct.
     * @see #assertContactsCommandFailure(String, CommandBox, Class, String, Model)
     */
    private void assertContactsCommandFailure(String inputCommand, CommandBox commandBox,
                                              Class<? extends Throwable> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getMeetingsBook(), new UserPrefs());
        assertContactsCommandFailure(inputCommand, commandBox, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command for contacts and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertContactsCommandSuccess(String, CommandBox, String, Model)
     */
    private void assertContactsCommandFailure(String inputCommand, CommandBox commandBox,
                                              Class<? extends Throwable> expectedException, String expectedMessage,
                                              Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.executeForContacts(inputCommand, commandBox));
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command for meetings,
     * confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertMeetingsCommandFailure(String, CommandBox, Class, String, Model)
     */
    private void assertMeetingsCommandException(String inputCommand, CommandBox commandBox, String expectedMessage) {
        assertMeetingsCommandFailure(inputCommand, commandBox, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command for meetings,
     * confirms that the exception is thrown and that the result message is correct.
     * @see #assertContactsCommandFailure(String, CommandBox, Class, String, Model)
     */
    private void assertMeetingsCommandFailure(String inputCommand, CommandBox commandBox,
                                              Class<? extends Throwable> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getMeetingsBook(), new UserPrefs());
        assertMeetingsCommandFailure(inputCommand, commandBox, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command for meetings and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertMeetingsCommandSuccess(String, CommandBox, String, Model)
     */
    private void assertMeetingsCommandFailure(String inputCommand, CommandBox commandBox,
                                              Class<? extends Throwable> expectedException, String expectedMessage,
                                              Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.executeForMeetings(inputCommand, commandBox));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonAddressBookIoExceptionThrowingStub extends JsonAddressBookStorage {
        private JsonAddressBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    private static class JsonMeetingsBookIoExceptionThrowingStub extends JsonMeetingsBookStorage {
        private JsonMeetingsBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveMeetingsBook(ReadOnlyMeetingsBook meetingsBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
