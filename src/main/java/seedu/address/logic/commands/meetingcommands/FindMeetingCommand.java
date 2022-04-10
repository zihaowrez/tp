package seedu.address.logic.commands.meetingcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.meeting.MeetingContainsKeywordsPredicate;
import seedu.address.model.meeting.MeetingKeywordMatchnessComparator;
import seedu.address.model.meeting.MeetingStartTimeSorter;

/**
 * Finds and lists all meetings in meetings book whose title contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindMeetingCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all meetings whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " cs2103 lecture";

    private final MeetingContainsKeywordsPredicate predicate;
    private final MeetingKeywordMatchnessComparator comparator;

    /**
     * Constructor for the FindMeetingCommand
     * @param predicate
     * @param comparator
     */
    public FindMeetingCommand(MeetingContainsKeywordsPredicate predicate,
                              MeetingKeywordMatchnessComparator comparator) {
        this.predicate = predicate;
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMeetingList(predicate);
        model.sortFilteredMeetingList(comparator);
        model.sortFilteredMeetingList(new MeetingStartTimeSorter());
        return new CommandResult(
                String.format(Messages.MESSAGE_MEETINGS_LISTED_OVERVIEW,
                        model.getSortedAndFilteredMeetingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMeetingCommand // instanceof handles nulls
                && predicate.equals(((FindMeetingCommand) other).predicate)); // state check
    }
}
