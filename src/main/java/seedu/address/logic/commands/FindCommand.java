package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.PersonKeywordMatchnessComparator;

/**
 * Finds and lists all persons in the contacts' list who matches any of the argument keywords.
 * Results are sorted according to the {@code comparator}.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of \n"
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie \n \n"
            + "Note that you may use the find command without the " + COMMAND_WORD + "command word"
            + "Example: alice bob charlie";

    private final PersonContainsKeywordsPredicate predicate;
    private final PersonKeywordMatchnessComparator comparator;

    /**
     * Constructor for the {@code FindCommand} object.
     */
    public FindCommand(PersonContainsKeywordsPredicate predicate, PersonKeywordMatchnessComparator comparator) {
        this.predicate = predicate;
        this.comparator = comparator;
    }

    /**
     * Finds and lists all persons in the contacts' list who matches any of the argument keywords.
     * Results are sorted according to the {@code comparator}.
     * @param model {@code Model} which the command should operate on.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        model.sortFilteredPersonList(comparator);
        return new CommandResult(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, predicate.toString(),
                model.getSortedAndFilteredPersonList().size()));
    }

    /**
     * Checks whether a {@code FindCommand} is the same as another {@code FindCommand}.
     * They must have the same {@code predicate} and the same {@code comparator} in order to be the same.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)
                && comparator.equals(((FindCommand) other).comparator)); // state check
    }
}
