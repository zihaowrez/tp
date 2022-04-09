package seedu.address.logic.commands.meetingcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.MeetingContainsKeywordsPredicate;
import seedu.address.model.meeting.MeetingKeywordMatchnessComparator;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindMeetingCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalMeetingsBook(), new UserPrefs());

    @Test
    public void equals() {
        MeetingContainsKeywordsPredicate firstPredicate =
                new MeetingContainsKeywordsPredicate(Collections.singletonList("first"));
        MeetingContainsKeywordsPredicate secondPredicate =
                new MeetingContainsKeywordsPredicate(Collections.singletonList("second"));

        MeetingKeywordMatchnessComparator firstComparator =
                new MeetingKeywordMatchnessComparator(Collections.singletonList("first"));
        MeetingKeywordMatchnessComparator secondComparator =
                new MeetingKeywordMatchnessComparator(Collections.singletonList("second"));


        FindMeetingCommand findFirstMeetingCommand = new FindMeetingCommand(firstPredicate, firstComparator);
        FindMeetingCommand findSecondMeetingCommand = new FindMeetingCommand(secondPredicate, secondComparator);

        // same object -> returns true
        assertTrue(findFirstMeetingCommand.equals(findFirstMeetingCommand));

        // same values -> returns true
        FindMeetingCommand findFirstCommandCopy = new FindMeetingCommand(firstPredicate, firstComparator);
        assertTrue(findFirstMeetingCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstMeetingCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstMeetingCommand.equals(findSecondMeetingCommand));
    }

    /*
    @Test
    public void execute_zeroKeywords_noMeetingFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        MeetingContainsKeywordsPredicate predicate = preparePredicate(" ");
        MeetingKeywordMatchnessComparator comparator  = prepareComparator(" ");
        FindMeetingCommand command = new FindMeetingCommand(predicate, comparator);
        expectedModel.updateFilteredMeetingList(predicate);
        expectedModel.sortFilteredMeetingList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getSortedAndFilteredMeetingList());
    }

    @Test
    public void execute_multipleKeywords_multipleMeetingsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        MeetingContainsKeywordsPredicate predicate = preparePredicate("CS2103 CS3230");
        MeetingKeywordMatchnessComparator comparator  = prepareComparator(" ");
        FindMeetingCommand command = new FindMeetingCommand(predicate, comparator);
        expectedModel.updateFilteredMeetingList(predicate);
        expectedModel.sortFilteredMeetingList(comparator);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CS2103_MEETING, CS3230_PE), model.getSortedAndFilteredMeetingList());
    }
     */

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private MeetingContainsKeywordsPredicate preparePredicate(String userInput) {
        return new MeetingContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    private MeetingKeywordMatchnessComparator prepareComparator(String userInput) {
        return new MeetingKeywordMatchnessComparator(Arrays.asList(userInput.split("\\s+")));
    }

}
