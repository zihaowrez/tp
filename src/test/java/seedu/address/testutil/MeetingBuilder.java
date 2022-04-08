package seedu.address.testutil;


import java.util.HashSet;
import java.util.Set;

import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.meeting.Title;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {

    public static final String DEFAULT_TITLE = "RVRC Project Meeting";
    public static final String DEFAULT_LINK = "https://zoom.sg";
    public static final String DEFAULT_START_TIME = "2024-10-10 1800";
    private static final int DEFAULT_DURATION = 60;

    private Title title;
    private Link link;
    private StartTime startTime;
    private Duration duration;
    private Set<Tag> tags;

    /**
     * Creates a {@code MeetingBuilder} with the default details.
     */
    public MeetingBuilder() {
        title = new Title(DEFAULT_TITLE);
        link = new Link(DEFAULT_LINK);
        startTime = new StartTime(DEFAULT_START_TIME);
        duration = new Duration(DEFAULT_DURATION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MeetingBuilder with the data of {@code meetingToCopy}.
     */
    public MeetingBuilder(Meeting meetingToCopy) {
        title = meetingToCopy.getTitle();
        link = meetingToCopy.getLink();
        startTime = meetingToCopy.getStartTime();
        duration = meetingToCopy.getDuration();
        tags = new HashSet<>(meetingToCopy.getTags());
    }

    /**
     * Sets the {@code MeetingName} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Meeting} that we are building.
     */
    public MeetingBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Link} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withLink(String link) {
        this.link = new Link(link);
        return this;
    }

    /**
     * Sets the {@code dateTime} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withStartTime(String startTime) {
        this.startTime = new StartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code duration} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withDuration(int duration) {
        this.duration = new Duration(duration);
        return this;
    }


    public Meeting build() {
        return new Meeting(title, link, startTime, duration, tags);
    }

}
