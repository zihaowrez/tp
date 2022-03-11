package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class ContactDetailPanel extends UiPart<Region> {
    private static final String FXML = "ContactDetailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> contactDetailView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ContactDetailPanel(ObservableList<Person> contactDetailList) {
        super(FXML);
        contactDetailView.setItems(contactDetailList);
        contactDetailView.setCellFactory(listView -> new ContactDetailListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ContactDetailListViewCell extends ListCell<Person> { // update Object to relevant class
        @Override
        protected void updateItem(Person person, boolean empty) { // change class of parameters
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot()); // create new class for ContactCard
            }
        }
    }

}
