package seedu.address.ui;

import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.meeting.Meeting;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An UI component that displays {@code ContactDetail} of a {@code Meeting}.
 */

public class UpcomingMeetingCard extends UiPart<Region> {
    private static final String FXML = "UpcomingMeetingCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Meeting meeting;

    @FXML
    private HBox upcomingMeetingCardPane;

    @FXML
    private Label title;

    @FXML
    private Label time;

    @FXML
    private Hyperlink link;

    @FXML
    private FlowPane tags;


    /**
     * Creates a {@code ContactDetailCard} of the given {@code Meeting} to be displayed on the RHS Window.
     */
    public UpcomingMeetingCard(Meeting meeting) {
        super(FXML);
        this.meeting = meeting;

        title.setText(meeting.getTitle().title);
        time.setText(meeting.getStartTime().toString() + "-" +
                meeting.getStartTime().startTime.plusMinutes(meeting.getDuration()).format(
                        DateTimeFormatter.ofPattern("HHmm")
                ));
        link.setText(meeting.getLink().link);
        link.setOnAction(event -> {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        try {
                            Desktop.getDesktop().browse(new URI(meeting.getLink().link));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });


        AtomicInteger index = new AtomicInteger(1);


        if (meeting.getTags().size() == 0) {
            tags.getChildren().add(new Label("-"));
        } else {
            meeting.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        tags.getChildren().add(new Label(index + ". " + tag.tagName));
                        index.addAndGet(1);
                    });
            index.set(1);
        }

        /*
        if (meeting.getPersons().size() == 0) {
            persons.getChildren().add(new Label("-"));
        } else {
            meeting.getPersons().stream()
                    .sorted(Comparator.comparing(sm -> sm.platformName.getValue()))
                    .forEach(sm -> {
                        persons.getChildren().add(new Label(sm.getPlatformName()
                                + ": " + sm.getPlatformDescription()));
                    });
            index.set(1);
        } */

    };



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpcomingMeetingCard)) {
            return false;
        }

        // state check
        UpcomingMeetingCard card = (UpcomingMeetingCard) other;
        return meeting.equals(card.meeting);
    }

}
