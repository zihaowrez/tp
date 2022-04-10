package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.MeetingsBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingsBook;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.storage.Storage;
import seedu.address.ui.CommandBox;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final MeetingsBookParser meetingsBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        meetingsBookParser = new MeetingsBookParser();
    }

    @Override
    public CommandResult executeForContacts(String commandText, CommandBox commandBox)
            throws CommandException, ParseException {
        if (commandBox != null && commandBox.isDynamic()) {
            logger.info("----------------[USER COMMAND][" + commandText + "]");
        }
        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText, commandBox);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public CommandResult executeForMeetings(String commandText, CommandBox commandBox)
            throws CommandException, ParseException {
        if (commandBox != null && commandBox.isDynamic()) {
            logger.info("----------------[USER COMMAND][" + commandText + "]");
        }
        CommandResult commandResult;
        Command command = meetingsBookParser.parseCommand(commandText, commandBox);
        commandResult = command.execute(model);

        try {
            storage.saveMeetingsBook(model.getMeetingsBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public CommandResult[] clickTag(Tag tag) {
        assert tag != null;
        updateFilteredPersonList(person -> {
            Set<Tag> tagSet = person.getTags();
            return tagSet.contains(tag);
        });
        updateFilteredMeetingList(meeting -> {
            Set<Tag> meetingTagSet = meeting.getTags();
            return meetingTagSet.contains(tag);
        });
        return new CommandResult[] {
            new CommandResult(getSortedAndFilteredPersonList().size()
                    + " persons with tag " + tag + " listed!"),
            new CommandResult(getSortedAndFilteredMeetingList().size()
                    + " meetings with tag " + tag + " listed!")};
    }

    public ObservableObjectValue<Person> getCurrentlySelectedPerson() {
        return model.getCurrentlySelectedPerson();
    }

    @Override
    public void setCurrentlySelectedPerson(Person newPerson) {
        model.updateSelectedPerson(newPerson);
    }

    @Override
    public ObservableIntegerValue getObservableIndex() {
        return model.getSelectedIndex();
    }

    @Override
    public void setObservableIndex(Index index) {
        model.updateSelectedIndex(index);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getSortedAndFilteredPersonList() {
        return model.getSortedAndFilteredPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        model.updateFilteredPersonList(predicate);
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return model.getFilteredTagList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public ReadOnlyMeetingsBook getMeetingsBook() {
        return model.getMeetingsBook();
    }

    @Override
    public ObservableList<Meeting> getSortedAndFilteredMeetingList() {
        return model.getSortedAndFilteredMeetingList();
    }

    @Override
    public void updateFilteredMeetingList(Predicate<Meeting> predicate) {
        model.updateFilteredMeetingList(predicate);
    }

    @Override
    public ObservableList<Meeting> getUpcomingMeetingList() {
        return model.getUpcomingMeetingList();
    }

    @Override
    public Path getMeetingsBookFilePath() {
        return model.getMeetingsBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
