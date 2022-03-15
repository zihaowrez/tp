package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.delete.DeletePersonCommand;
import seedu.address.logic.commands.delete.DeletePersonsTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        String preamble = argMultimap.getPreamble();
        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        Object target = parseTarget(preamble);

        if (!argMultimap.arePrefixesPresent(PREFIX_TAG)) {
            return new DeletePersonCommand(target);
        } else {
            Tag targetTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new DeletePersonsTagCommand(target, targetTag);
        }
    }

    private Object parseTarget(String args) throws ParseException {

        if (StringUtil.isInt(args)) {
            return ParserUtil.parseIndex(args);
        } else {
            return ParserUtil.parseName(args);
        }
    }

}
