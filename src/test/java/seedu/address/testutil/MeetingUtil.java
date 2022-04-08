package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.format.DateTimeFormatter;
import java.util.Set;

import seedu.address.logic.commands.meetingcommands.AddMeetingCommand;
import seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Meeting.
 */
public class MeetingUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddMeetingCommand(Meeting meeting) {
        return AddMeetingCommand.COMMAND_WORD + " " + getMeetingDetails(meeting);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getMeetingDetails(Meeting meeting) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");
        sb.append(PREFIX_NAME + meeting.getTitle().title + " ");
        sb.append(PREFIX_LINK + meeting.getLink().link + " ");
        sb.append(PREFIX_STARTTIME + meeting.getStartTime().jsonStartTime() + " ");
        sb.append(PREFIX_DURATION + meeting.getDuration().toString() + " ");

        meeting.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditMeetingDescriptorDetails(EditMeetingDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(name -> sb.append(PREFIX_NAME).append(name.title).append(" "));
        descriptor.getLink().ifPresent(link -> sb.append(PREFIX_LINK).append(link.link).append(" "));
        descriptor.getStartTime().ifPresent(startTime
            -> sb.append(PREFIX_STARTTIME).append(startTime.jsonStartTime()).append(" "));
        descriptor.getDuration().ifPresent(duration -> sb.append(PREFIX_DURATION).append(duration).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG).append(" ");
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }

        return sb.toString();
    }
}
