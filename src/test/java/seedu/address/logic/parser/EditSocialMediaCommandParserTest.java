package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.edit.EditSocialMediaCommand;
import seedu.address.model.person.Name;


public class EditSocialMediaCommandParserTest {

    private static final String MESSAGE_INVALID_EDIT_SOCIAL_MEDIA_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditSocialMediaCommand.MESSAGE_USAGE);

    private EditSocialMediaCommandParser parser = new EditSocialMediaCommandParser();

    @Test
    void parse_missingParts_failure() {
        // social media index not provided
        assertParseFailure(parser, "1 i/", MESSAGE_INVALID_EDIT_SOCIAL_MEDIA_COMMAND_FORMAT);
        assertParseFailure(parser, "TOM i/", MESSAGE_INVALID_EDIT_SOCIAL_MEDIA_COMMAND_FORMAT);

        // social media details not provided
        assertParseFailure(parser, "1 i/1", MESSAGE_INVALID_EDIT_SOCIAL_MEDIA_COMMAND_FORMAT);
        assertParseFailure(parser, "TOM i/1", MESSAGE_INVALID_EDIT_SOCIAL_MEDIA_COMMAND_FORMAT);

        // index prefix not provided
        assertParseFailure(parser, "1 f/", MESSAGE_INVALID_EDIT_SOCIAL_MEDIA_COMMAND_FORMAT);
        assertParseFailure(parser, "TOM f/", MESSAGE_INVALID_EDIT_SOCIAL_MEDIA_COMMAND_FORMAT);
    }

    @Test
    void parse_invalidIndex_failure() {

        //negative index
        assertParseFailure(parser, "1 i/-1 f/ sm/hello", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "1 i/-1 sm/hello", ParserUtil.MESSAGE_INVALID_INDEX);

        //zero based index
        assertParseFailure(parser, "1 i/0 f/ sm/hello", ParserUtil.MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, "1 i/0 sm/hello", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    void parse_indexTarget_success() {

        EditSocialMediaCommand expectedCommand = new EditSocialMediaCommand(new Target(Index.fromOneBased(1)),
            Index.fromOneBased(1), "Telegram", true);

        //target person by index
        assertParseSuccess(parser, "1 i/1 f/ sm/Telegram", expectedCommand);

        //re order arguments
        assertParseSuccess(parser, "1 f/ i/1 sm/Telegram", expectedCommand);
        assertParseSuccess(parser, "1 f/ sm/Telegram i/1", expectedCommand);
        assertParseSuccess(parser, "1 sm/Telegram f/ i/1", expectedCommand);
        assertParseSuccess(parser, "1 sm/Telegram i/1 f/", expectedCommand);
        assertParseSuccess(parser, "1 i/1 sm/Telegram f/", expectedCommand);

    }

    @Test
    void parse_nameTarget_success() {
        EditSocialMediaCommand expectedCommand = new EditSocialMediaCommand(new Target(new Name("Tom")),
            Index.fromOneBased(1), "Telegram", true);

        //target person by name
        assertParseSuccess(parser, "Tom i/1 f/ sm/Telegram", expectedCommand);
    }
}
