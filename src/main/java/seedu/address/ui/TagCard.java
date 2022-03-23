package seedu.address.ui;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays simple information of a {@code Person}.
 */
public class TagCard extends UiPart<Region> {

    private static final String FXML = "TagCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final List<Tag> tagList;

    @FXML
    private FlowPane tags;

    /**
     @FXML
     private Label phone;
     @FXML
     private Label address;
     @FXML
     private Label email;
     */


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public TagCard(List<Tag> tagList) {
        super(FXML);
        this.tagList = tagList;

        if (tagList.size() != 0) {
            tagList.stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        tags.getChildren().add(new Label(tag.tagName));
                    });
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        TagCard card = (TagCard) other;
        return tagList.equals(card.tagList);
    }
}
