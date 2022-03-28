package seedu.address.model.meeting;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class Meeting {

    private final MeetingName name;
    private final Link link;
    private final DateTime dateTime;

    private final Set<Tag> tags;

    public Meeting(MeetingName name, Link link, DateTime dateTime, Set<Tag> tags) {
        this.name = name;
        this.link = link;
        this.dateTime = dateTime;
        this.tags = tags;
    }

    public MeetingName getName() { return this.name; }

    public Link getLink() { return this.link; }

    public DateTime getDateTime() { return this.dateTime; }

    public LocalDateTime getStartDateTime() { return this.dateTime.startDateTime; }

    public LocalDateTime getEndDateTime() { return this.dateTime.endDateTime; }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean isSameMeeting(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Meeting)) {
            return false;
        }

        Meeting meeting = (Meeting) other;
        return meeting.getName().equals(this.name)
                && meeting.getLink().equals(this.link)
                && meeting.getDateTime().equals(this.dateTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Link: ")
                .append(getLink())
                .append("; Date and time: ")
                .append(getDateTime());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
