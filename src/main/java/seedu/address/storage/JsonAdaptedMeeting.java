package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Meeting}.
 */
class JsonAdaptedMeeting {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meeting's %s field is missing!";

    private final String name;
    private final String link;
    private final String startDateTime;
    private final String endDateTime;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given meeting details.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("name") String name, @JsonProperty("link") String link,
                             @JsonProperty("startDateTime") String startDateTime,
                             @JsonProperty("endDateTime") String endDateTime,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.link = link;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        name = source.getName().name;
        link = source.getLink().link;
        startDateTime = source.getDateTime().jsonStartDateTime();
        endDateTime = source.getDateTime().jsonEndDateTime();

        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted meeting object into the model's {@code Meeting} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meeting.
     */
    public Meeting toModelType() throws IllegalValueException {
        final List<Tag> meetingTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            meetingTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MeetingName.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(MeetingName.MESSAGE_CONSTRAINTS);
        }
        final MeetingName modelName = new MeetingName(name);

        if (link == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Link.class.getSimpleName()));
        }
        if (!Link.isValidLink(link)) {
            throw new IllegalValueException(Link.MESSAGE_CONSTRAINTS);
        }
        final Link modelLink = new Link(link);

        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(startDateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }

        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(endDateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }

        final DateTime modelDateTime = new DateTime(LocalDateTime.parse(startDateTime), LocalDateTime.parse(endDateTime));

        final Set<Tag> modelTags = new HashSet<>(meetingTags);
        return new Meeting(modelName, modelLink, modelDateTime, modelTags);
    }
}
