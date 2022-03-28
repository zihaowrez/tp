package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.meeting.Meeting;

import java.util.logging.Logger;

/**
 * Panel containing {@code UpcomingMeetingList} of a {@Meeting}.
 */
public class UpcomingMeetingListPanel extends UiPart<Region> {
    private static final String FXML = "UpcomingMeetingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MeetingListPanel.class);

    @FXML
    private ListView<Meeting> upcomingMeetingListView;

    /**
     * Creates a {@code UpcomingMeetingListPanel} with the given {@code ObservableList}.
     */
    public UpcomingMeetingListPanel(ObservableList<Meeting> upcomingMeetingListList) {
        super(FXML);
        upcomingMeetingListView.setItems(upcomingMeetingListList);
        upcomingMeetingListView.setCellFactory(listView -> new UpcomingMeetingListListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the {@code UpcomingMeetingListCard} of a {@code Meeting} .
     */
    class UpcomingMeetingListListViewCell extends ListCell<Meeting> { // update Object to relevant class
        @Override
        protected void updateItem(Meeting meeting, boolean empty) { // change class of parameters
            super.updateItem(meeting, empty);

            if (empty || meeting == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new UpcomingMeetingCard(meeting).getRoot()); // create new class for ContactCard
            }
        }
    }

}
