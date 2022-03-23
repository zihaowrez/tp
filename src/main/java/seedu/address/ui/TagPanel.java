package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of tags.
 */
public class TagPanel extends UiPart<Region> {
    private static final String FXML = "TagPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TagPanel.class);

    public final List<Tag> tagList;

    @FXML
    private ListView<Tag> tagView;
    /**
     * Creates a {@code TagListPanel} with the given {@code ObservableList}.
     */
    public TagPanel(ObservableList<Tag> tagList) {
        super(FXML);
        this.tagList = tagList;
        tagView.setItems(tagList); // tagList is null hence NullPointerException
        tagView.setCellFactory(listView -> new TagListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Tag} using a {@code TagCard}.
     */


    class TagListViewCell extends ListCell<Tag> {
        @Override
        protected void updateItem(Tag tag, boolean empty) {
            super.updateItem(tag, empty);

            if (empty || tag == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TagCard(tagList).getRoot());
            }
        }
    }


}
