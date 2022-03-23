package seedu.address.model.meeting;

import seedu.address.model.tag.Tag;

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

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean isSameMeeting(Meeting meeting) {
        return meeting.getName().equals(this.name) && meeting.getDateTime().equals(this.dateTime);
    }

}
