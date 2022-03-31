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
import seedu.address.logic.Logic;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of tags.
 */
public class TagPanel extends UiPart<Region> {
    private static final String FXML = "TagPanel.fxml";
    public final List<Tag> tagList;
    private final Logger logger = LogsCenter.getLogger(TagPanel.class);



    @FXML
    private HBox tagListPlaceholder;

    @FXML
    private Pane panel;

    /**
     * Creates a {@code TagListPanel} with the given {@code ObservableList}.
     */
    public TagPanel(ObservableList<Tag> tagList, Logic logic) {

        super(FXML);
        this.tagList = tagList;

        setPanel(new TagCard(tagList, logic).getRoot());

    }

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
