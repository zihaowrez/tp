package seedu.address.model.meeting;

import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.Set;

public class Meeting {

    private final Title title;
    private final Link link;
    private final StartTime startTime;
    private final int duration;
    private final Set<Tag> tags;

    public Meeting(Title title, Link link, StartTime startTime, int duration, Set<Tag> tags) {
        this.title = title;
        this.link = link;
        this.startTime = startTime;
        this.duration = duration;
        this.tags = tags;
    }

    public Title getTitle() { return this.title; }

    public Link getLink() { return this.link; }

    public StartTime getStartTime() { return this.startTime; }

    public int getDuration() { return this.duration; }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean isSameMeeting(Meeting meeting) {
        return meeting.getTitle().equals(this.title) && meeting.getStartTime().equals(this.startTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append("; Link: ")
                .append(getLink())
                .append("; Start time: ")
                .append(getStartTime())
                .append("; Duration: ")
                .append(getDuration())
                .append(" mins");

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
