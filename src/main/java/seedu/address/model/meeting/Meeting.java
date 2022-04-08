package seedu.address.model.meeting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;

import seedu.address.model.tag.Tag;

public class Meeting {

    private final Title title;
    private final Link link;
    private final StartTime startTime;
    private final Duration duration;
    private final Set<Tag> tags;

    /**
     * Constructor for the Meeting class
     * @param title Name of the meeting
     * @param link Link of the meeting
     * @param startTime starting time of the meeting
     * @param duration duration of the meeting
     * @param tags
     */
    public Meeting(Title title, Link link, StartTime startTime, Duration duration, Set<Tag> tags) {
        this.title = title;
        this.link = link;
        this.startTime = startTime;
        this.duration = duration;
        this.tags = tags;
    }

    public Title getTitle() {
        return this.title;
    }

    public Link getLink() {
        return this.link;
    }

    public StartTime getStartTime() {
        return this.startTime;
    }

    public Duration getDuration() {
        return this.duration;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both meetings have the same title and the same start time.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameMeeting(Meeting otherMeeting) {
        if (otherMeeting == this) {
            return true;
        }

        return otherMeeting != null
                && otherMeeting.getTitle().equals(this.title) && otherMeeting.getStartTime().equals(this.startTime);
    }

    /**
     * Returns the string representation of the meeting slot for display.
     */
    public String getTimeString() {
        String endTime;
        LocalDateTime endDateTime = startTime.startTime.plusMinutes(duration.mins);
        if (!endDateTime.toLocalDate().isEqual(startTime.startTime.toLocalDate())) {
            endTime = " to " + endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            endTime = "-" + endDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return startTime + endTime;
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

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (!(object instanceof Meeting)) {
            return false;
        }

        Meeting meeting = (Meeting) object;
        return meeting.getTitle().equals(this.title)
                && meeting.getLink().equals(this.link)
                && meeting.getStartTime().equals(this.startTime)
                && meeting.getDuration().equals(this.duration)
                && meeting.getTags().equals(this.tags);
    }

}
