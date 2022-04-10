package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
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
    private ScrollPane panel;

    /**
     * Creates a {@code TagListPanel} with the given {@code ObservableList}.
     */
    public TagPanel(ObservableList<Tag> tagList, Logic logic, MainWindow mainWindow) {

        super(FXML);
        this.tagList = tagList;

        setPanel(new TagCard(tagList, logic, mainWindow).getRoot());
        panel.setStyle("-fx-background: #ededed; -fx-border-color: #ededed;");

    }

    public void setPanel(Node childPanel) {
        panel.setStyle("-fx-background: #ededed; -fx-border-color: #ededed;");
        if (childPanel != null) {
            logger.info(String.format("Setting tag panel to %s", childPanel));
            panel.setContent(childPanel);
        } else {
            logger.info("Clearing tag panel");
            panel.setContent(null);
        }
    }


}
