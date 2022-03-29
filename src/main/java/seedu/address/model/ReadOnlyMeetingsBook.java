package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.meeting.Meeting;

public interface ReadOnlyMeetingsBook {

    ObservableList<Meeting> getMeetingList();
}
