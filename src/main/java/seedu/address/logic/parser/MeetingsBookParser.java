package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.meetingcommands.FindMeetingCommand;
import seedu.address.logic.commands.meetingcommands.AddMeetingCommand;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand;
import seedu.address.logic.commands.meetingcommands.ListMeetingCommand;
import seedu.address.logic.commands.meetingcommands.DeleteMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.meetingcommands.AddMeetingCommandParser;
import seedu.address.logic.parser.meetingcommands.DeleteMeetingCommandParser;
import seedu.address.logic.parser.meetingcommands.EditMeetingCommandParser;
import seedu.address.logic.parser.meetingcommands.FindMeetingCommandParser;
import seedu.address.ui.CommandBox;


/**
 * Parses user input.
 */
public class MeetingsBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Pattern DYNAMIC_COMMAND_FORMAT = Pattern.compile("(?<inputType>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, CommandBox commandBox) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        final Matcher dynamicMatcher = DYNAMIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (dynamicMatcher.matches() && dynamicMatcher.group("inputType").equals("dynamic")) {
            final String arguments = dynamicMatcher.group("arguments").trim();
            String[] splitArgs = arguments.split(" ");

            if (isCommand(arguments, splitArgs)) {
                commandBox.setDynamicAsFalse();
                return new ListMeetingCommand();
            } else {
                commandBox.setDynamicAsTrue();
                return new FindMeetingCommandParser().parse(arguments);
            }
        } else {
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }

            final String commandWord = matcher.group("commandWord");
            final String arguments = matcher.group("arguments");

            switch (commandWord) {

                case AddMeetingCommand.COMMAND_WORD:
                    return new AddMeetingCommandParser().parse(arguments);

                case EditMeetingCommand.COMMAND_WORD:
                    return new EditMeetingCommandParser().parse(arguments);

                case FindMeetingCommand.COMMAND_WORD:
                    return new FindMeetingCommandParser().parse(arguments);

                case DeleteMeetingCommand.COMMAND_WORD:
                    return new DeleteMeetingCommandParser().parse(arguments);

                case ListMeetingCommand.COMMAND_WORD:
                    return new ListMeetingCommand();

                default:
                    return new FindCommandParser().parse(commandWord);
            }
        }
    }

    /**
     * Checks if the user input matches a valid command
     *
     * @param command Raw user inputted command
     * @param splitCommand Array of user command separated by space
     * @return true if command is a valid command and false otherwise
     */
    public boolean isCommand(String command, String[] splitCommand) {
        return (command.equals("") || command.equals("add")
                || splitCommand[0].equals("add") || command.equals("find") || splitCommand[0].equals("find")
                || command.equals("edit") || splitCommand[0].equals("edit")) || command.equals("delete")
                || splitCommand[0].equals("delete") || command.equals("view") || splitCommand[0].equals("view")
                || command.equals("copy") || splitCommand[0].equals("copy");
    }
}
