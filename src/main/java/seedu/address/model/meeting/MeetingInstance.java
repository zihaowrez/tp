package seedu.address.model.meeting;

import seedu.address.model.tag.Tag;

import java.util.Set;

public class MeetingInstance {

    private final Title title;
    private final Link link;
    private final StartTime startTime;
    private final int duration;
    private final Set<Tag> tags;

    public MeetingInstance(Meeting meeting) {
        this.title = meeting.getTitle();
        this.link = meeting.getLink();
        this.startTime = meeting.getStartTime();
        this.duration = meeting.getDuration();
        this.tags = meeting.getTags();
    }



}
