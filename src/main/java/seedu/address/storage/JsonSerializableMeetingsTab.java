package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.MeetingsTab;
import seedu.address.model.ReadOnlyMeetingsTab;
import seedu.address.model.meeting.Meeting;

/**
 * An Immutable MeetingsTab that is serializable to JSON format.
 */
@JsonRootName(value = "meetingsTab")
class JsonSerializableMeetingsTab {

    public static final String MESSAGE_DUPLICATE_MEETINGS = "Meetings list contains duplicate person(s).";

    private final List<JsonAdaptedMeeting> meetings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMeetingsTab} with the given meetings.
     */
    @JsonCreator
    public JsonSerializableMeetingsTab(@JsonProperty("meetings") List<JsonAdaptedMeeting> meetings) {
        this.meetings.addAll(meetings);
    }

    /**
     * Converts a given {@code ReadOnlyMeetingsTab} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMeetingsTab}.
     */
    public JsonSerializableMeetingsTab(ReadOnlyMeetingsTab source) {
        meetings.addAll(source.getMeetingList().stream().map(JsonAdaptedMeeting::new).collect(Collectors.toList()));
    }

    /**
     * Converts this meetings tab into the model's {@code MeetingsTab} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MeetingsTab toModelType() throws IllegalValueException {
        MeetingsTab meetingsTab = new MeetingsTab();
        for (JsonAdaptedMeeting jsonAdaptedMeeting : meetings) {
            Meeting meeting = jsonAdaptedMeeting.toModelType();
            if (meetingsTab.hasMeeting(meeting)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEETINGS);
            }
            meetingsTab.addMeeting(meeting, "tail");
        }
        return meetingsTab;
    }

}
