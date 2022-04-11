package seedu.address.logic.commands.meetingcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMeetingAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListMeetingCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getMeetingsBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        int expectedSize = expectedModel.getSortedAndFilteredMeetingList().size();
        assertCommandSuccess(new ListMeetingCommand(), model,
                expectedSize + ListMeetingCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showMeetingAtIndex(model, INDEX_FIRST_MEETING);
        int expectedSize = expectedModel.getSortedAndFilteredMeetingList().size();
        assertCommandSuccess(new ListMeetingCommand(), model,
                expectedSize + ListMeetingCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
