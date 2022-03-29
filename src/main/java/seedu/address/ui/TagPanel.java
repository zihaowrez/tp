package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of tags.
 */
public class TagPanel extends UiPart<Region> {
    private static final String FXML = "TagPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TagPanel.class);

    public final List<Tag> tagList;

    @FXML
    private HBox tagListPlaceholder;

    @FXML
    private Pane panel;

    /**
     * Creates a {@code TagListPanel} with the given {@code ObservableList}.
     */
    public TagPanel(ObservableList<Tag> tagList) {

        super(FXML);
        this.tagList = tagList;

        setPanel(new TagCard(tagList).getRoot());

    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Tag} using a {@code TagCard}.
     */


    public void setPanel(Node childPanel) {
        if (childPanel != null) {
            logger.info(String.format("Setting tag panel to %s", childPanel));
            panel.getChildren().setAll(childPanel);
        } else {
            logger.info("Clearing tag panel");
            panel.getChildren().clear();
        }
    }


}
