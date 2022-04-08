package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.meetingcommands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.meeting.Title;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditMeetingDescriptor objects.
 */
public class EditMeetingDescriptorBuilder {

    private EditMeetingDescriptor descriptor;

    public EditMeetingDescriptorBuilder() {
        descriptor = new EditMeetingDescriptor();
    }

    public EditMeetingDescriptorBuilder(EditMeetingDescriptor descriptor) {
        this.descriptor = new EditMeetingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMeetingDescriptor} with fields containing {@code person}'s details
     */
    public EditMeetingDescriptorBuilder(Meeting meeting) {
        descriptor = new EditMeetingDescriptor();
        descriptor.setTitle(meeting.getTitle());
        descriptor.setLink(meeting.getLink());
        descriptor.setStartTime(meeting.getStartTime());
        descriptor.setDuration(meeting.getDuration());
        descriptor.setTags(meeting.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Link} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withLink(String link) {
        descriptor.setLink(new Link(link));
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new StartTime(startTime));
        return this;
    }

    /**
     * Sets the {@code Duration} of the {@code EditMeetingDescriptor} that we are building.
     */
    public EditMeetingDescriptorBuilder withDuration(int duration) {
        descriptor.setDuration(new Duration(duration));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditMeetingDescriptor}
     * that we are building.
     */
    public EditMeetingDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditMeetingDescriptor build() {
        return descriptor;
    }
}
