package seedu.address.logic.parser.meetingcommands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class EditMeetingCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditMeetingCommand
     * and returns an EditMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditMeetingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LINK,
                PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMeetingCommand.MESSAGE_USAGE), pe);
        }

        EditMeetingCommand.EditMeetingDescriptor editMeetingDescriptor = new EditMeetingCommand.EditMeetingDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editMeetingDescriptor.setName(ParserUtil.parseMeetingName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_LINK).isPresent()) {
            editMeetingDescriptor.setLink(ParserUtil.parseLink(argMultimap.getValue(PREFIX_LINK).get()));
        }
        if (argMultimap.getValue(PREFIX_START_DATETIME).isPresent() &&
                argMultimap.getValue(PREFIX_END_DATETIME).isPresent()) {
            editMeetingDescriptor.setDateTime(ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATETIME).get(),
                    argMultimap.getValue(PREFIX_END_DATETIME).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editMeetingDescriptor::setTags);

        if (!editMeetingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMeetingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditMeetingCommand(index, editMeetingDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
