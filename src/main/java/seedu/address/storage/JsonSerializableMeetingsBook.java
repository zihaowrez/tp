package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.MeetingsBook;
import seedu.address.model.ReadOnlyMeetingsBook;
import seedu.address.model.meeting.Meeting;

/**
 * An Immutable MeetingsBook that is serializable to JSON format.
 */
@JsonRootName(value = "meetingsBook")
public class JsonSerializableMeetingsBook {

    public static final String MESSAGE_DUPLICATE_MEETINGS = "Meetings list contains duplicate person(s).";

    private final List<JsonAdaptedMeeting> meetings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMeetingsBook} with the given meetings.
     */
    @JsonCreator
    public JsonSerializableMeetingsBook(@JsonProperty("meetings") List<JsonAdaptedMeeting> meetings) {
        this.meetings.addAll(meetings);
    }

    /**
     * Converts a given {@code ReadOnlyMeetingsBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMeetingsBook}.
     */
    public JsonSerializableMeetingsBook(ReadOnlyMeetingsBook source) {
        meetings.addAll(source.getMeetingList().stream().map(JsonAdaptedMeeting::new).collect(Collectors.toList()));
    }

    /**
     * Converts this meetings tab into the model's {@code MeetingsBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MeetingsBook toModelType() throws IllegalValueException {
        MeetingsBook meetingsBook = new MeetingsBook();
        for (JsonAdaptedMeeting jsonAdaptedMeeting : meetings) {
            Meeting meeting = jsonAdaptedMeeting.toModelType();
            if (meetingsBook.hasMeeting(meeting)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEETINGS);
            }
            meetingsBook.addMeeting(meeting);
        }
        return meetingsBook;
    }

}
