package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.ClipboardManager;
import seedu.address.model.person.Person;
import seedu.address.model.socialmedia.SocialMedia;

/**
 * An UI component that displays {@code ContactDetail} of a {@code Person}.
 */

public class ContactDetailCard extends UiPart<Region> {
    private static final String FXML = "ContactDetailCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameView;

    @FXML
    private Label phoneLabel;
    @FXML
    private Label phoneView;

    @FXML
    private Label emailLabel;
    @FXML
    private Label emailView;

    @FXML
    private Label socialMediaLabel;
    @FXML
    private VBox socialMedias;

    @FXML
    private Label tagsLabel;
    @FXML
    private FlowPane tags;


    /**
     * Creates a {@code ContactDetailCard} of the given {@code Person} to be displayed on the RHS Window.
     */
    public ContactDetailCard(Person person) {
        super(FXML);
        this.person = person;
        ClipboardManager clipboard = new ClipboardManager();

        nameView.setText(person.getName().fullName);
        nameView.setOnMouseClicked(event ->
                clipboard.copy(person.getName().fullName)
        );
        phoneView.setText(person.getPhone().value);
        phoneView.setOnMouseClicked(event ->
                clipboard.copy(person.getPhone().value)
        );

        emailView.setText(person.getEmail().value);
        emailView.setOnMouseClicked(event -> {
            clipboard.copy(person.getEmail().value);
            try {
                Desktop.getDesktop().mail(new URI("mailto:" + person.getEmail().value + "?subject=Hello"));
            } catch (URISyntaxException | IOException ex) {
                ex.printStackTrace();
            }
        });

        phoneLabel.setText("Phone:");

        emailLabel.setText("Email:");

        tagsLabel.setText("Tags");

        socialMediaLabel.setText("Social Media");

        AtomicInteger index = new AtomicInteger(1);


        if (person.getTags().size() == 0) {
            tags.getChildren().add(new Label("-"));
        } else {
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        Label taglabel = new Label(index + ". " + tag.tagName);
                        taglabel.setOnMouseClicked(event ->
                                clipboard.copy(tag.tagName)
                        );
                        taglabel.setWrapText(true);
                        tags.getChildren().add(taglabel);
                        index.addAndGet(1);
                    });
            index.set(1);
        }

        List<SocialMedia> socialMediasXS = person.getSocialMedias();

        if (person.getSocialMedias().size() == 0) {
            socialMedias.getChildren().add(new HBox(new Label("-")));
        } else {
            int count = 0;
            for (SocialMedia sm : socialMediasXS) {
                count += 1;
                Label label = new Label(count + ". " + sm.getPlatformName() + ": " + sm.getPlatformDescription());
                label.setOnMouseClicked(event -> clipboard.copy(sm.getPlatformDescription().getValue()));
                label.setWrapText(true);

                if (sm.isTelegram()) {
                    label.setOnMouseClicked(event -> {
                        try {
                            String teleHandle = sm.getPlatformDescription().getValue();
                            teleHandle = teleHandle.replace("@", "");
                            Desktop.getDesktop().browse(new URL(SocialMedia.TELEGRAM_URL + teleHandle).toURI());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                }

                socialMedias.getChildren().add(new HBox(label));
            }

            index.set(1);
        }


    };



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactDetailCard)) {
            return false;
        }

        // state check
        ContactDetailCard card = (ContactDetailCard) other;
        return person.equals(card.person);
    }

}
