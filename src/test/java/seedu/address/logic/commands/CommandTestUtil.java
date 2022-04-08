package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.meetingcommands.AddMeetingCommand;
import seedu.address.logic.commands.meetingcommands.AddTagToMeetingCommand;
import seedu.address.logic.commands.meetingcommands.DeleteMeetingCommand;
import seedu.address.logic.commands.meetingcommands.DeleteMeetingsTagCommand;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand;
import seedu.address.logic.commands.meetingcommands.FindMeetingCommand;
import seedu.address.logic.commands.meetingcommands.ListMeetingCommand;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.AddressBook;
import seedu.address.model.MeetingsBook;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.testutil.EditMeetingDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_SOCIAL_TELEGRAM = "Telegram, amybakingcompany";
    public static final String VALID_SOCIAL_GMAIL = "Gmail, bobbybobbeebob@gmail.com";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + CliSyntax.PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + CliSyntax.PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + CliSyntax.PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + CliSyntax.PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + CliSyntax.PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + CliSyntax.PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String SOCIAL_DESC_TELEGRAM = " " + CliSyntax.PREFIX_SOCIAL_MEDIA + VALID_SOCIAL_TELEGRAM;
    public static final String SOCIAL_DESC_GMAIL = " " + CliSyntax.PREFIX_SOCIAL_MEDIA + VALID_SOCIAL_GMAIL;
    public static final String TAG_DESC_FRIEND = " " + CliSyntax.PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + CliSyntax.PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + CliSyntax.PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + CliSyntax.PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + CliSyntax.PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + CliSyntax.PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    //TODO add invalid tests for social media

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final String VALID_TITLE_CS2103 = "CS2103 Meeting";
    public static final String VALID_LINK_ZOOM = "https://zoom.sg";
    public static final String VALID_STARTTIME_CS2103 = "2022-10-10 1800";
    public static final int VALID_DURATION_INT_60 = 60;
    public static final String VALID_DURATION_STRING_60 = "60";
    public static final String VALID_TITLE_CS3230 = "CS3230 Meeting";
    public static final String VALID_STARTTIME_CS3230 = "2022-1-10 1800";
    public static final String VALID_TITLE_PROJECT = "Project Meeting";
    public static final String VALID_LINK_TEAMS = "https://teams.sg";
    public static final String VALID_STARTTIME_PROJECT = "2024-10-10 1800";
    public static final int VALID_DURATION_INT_50 = 50;
    public static final String VALID_DURATION_STRING_50 = "50";
    public static final String VALID_TAG_PROJECT = "project";

    public static final String INVALID_LINK = "https://zoom.sg 123456";
    public static final String INVALID_STARTTIME = "2020-10-10 180";
    public static final String INVALID_DURATION_STRING = "abc";

    public static final EditMeetingCommand.EditMeetingDescriptor DESC_CS2103;
    public static final EditMeetingCommand.EditMeetingDescriptor DESC_CS3230;
    public static final EditMeetingCommand.EditMeetingDescriptor DESC_PROJECT;


    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                //TODO Handle descriptor with socials and no phones/emails/addresses
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                //TODO Handle descriptor with socials and no phones/emails/addresses
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_CS2103 = new EditMeetingDescriptorBuilder().withTitle(VALID_TITLE_CS2103)
                .withLink(VALID_LINK_ZOOM).withStartTime(VALID_STARTTIME_CS2103)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_CS3230 = new EditMeetingDescriptorBuilder().withTitle(VALID_TITLE_CS3230)
                .withLink(VALID_LINK_ZOOM).withStartTime(VALID_STARTTIME_CS3230)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_PROJECT = new EditMeetingDescriptorBuilder().withTitle(VALID_TITLE_PROJECT)
                .withLink(VALID_LINK_TEAMS).withStartTime(VALID_STARTTIME_PROJECT)
                .withTags(VALID_TAG_PROJECT).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getSortedAndFilteredPersonList());

        MeetingsBook expectedMeetingsTab = new MeetingsBook(actualModel.getMeetingsBook());
        List<Meeting> expectedFilteredMeetingList = new ArrayList<>(actualModel.getSortedAndFilteredMeetingList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));

        if (isMeetingCommand(command)) {
            assertEquals(expectedMeetingsTab, actualModel.getMeetingsBook());
            assertEquals(expectedFilteredMeetingList, actualModel.getSortedAndFilteredMeetingList());
            return;
        }

        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getSortedAndFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getSortedAndFilteredPersonList().size());

        Person person = model.getSortedAndFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new PersonContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getSortedAndFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s meetingsTab.
     */
    public static void showMeetingAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getSortedAndFilteredPersonList().size());

        Meeting meeting = model.getSortedAndFilteredMeetingList().get(targetIndex.getZeroBased());
        final String[] splitTitle = meeting.getTitle().title.split("\\s+");
        model.updateFilteredMeetingList(new MeetingContainsKeywordsPredicate(Arrays.asList(splitTitle[0])));

        assertEquals(1, model.getSortedAndFilteredMeetingList().size());
    }

    /**
     * Checks if the command is a Meeting Command
     */
    public static boolean isMeetingCommand(Command command) {
        return (command instanceof FindMeetingCommand || command instanceof AddMeetingCommand
                || command instanceof AddTagToMeetingCommand || command instanceof DeleteMeetingCommand
                || command instanceof DeleteMeetingCommand || command instanceof DeleteMeetingsTagCommand
                || command instanceof ListMeetingCommand || command instanceof EditMeetingCommand);
    }

}
