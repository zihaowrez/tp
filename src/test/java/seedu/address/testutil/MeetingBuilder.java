package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {

    public static final String DEFAULT_NAME = "Project Meeting";
    public static final String DEFAULT_LINK = "https://zoom.sg";
    public static final LocalDateTime DEFAULT_START_DATETIME = LocalDateTime.of(2018, 10, 10, 18, 0);
    public static final LocalDateTime DEFAULT_END_DATETIME = LocalDateTime.of(2018, 10, 10, 20, 0);

    private MeetingName name;
    private Link link;
    private DateTime dateTime;
    private Set<Tag> tags;

    /**
     * Creates a {@code MeetingBuilder} with the default details.
     */
    public MeetingBuilder() {
        name = new MeetingName(DEFAULT_NAME);
        link = new Link(DEFAULT_LINK);
        dateTime = new DateTime(DEFAULT_START_DATETIME, DEFAULT_END_DATETIME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MeetingBuilder with the data of {@code meetingToCopy}.
     */
    public MeetingBuilder(Meeting meetingToCopy) {
        name = meetingToCopy.getName();
        link = meetingToCopy.getLink();
        dateTime = meetingToCopy.getDateTime();
        tags = new HashSet<>(meetingToCopy.getTags());
    }

    /**
     * Sets the {@code MeetingName} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withName(String name) {
        this.name = new MeetingName(name);
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
    public MeetingBuilder withDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.dateTime = new DateTime(startDateTime, endDateTime);
        return this;
    }


    public Meeting build() {
        return new Meeting(name, link, dateTime, tags);
    }

}
