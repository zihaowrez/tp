package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private final ObservableIntegerValue selectedIndex;
    private final Logic logic;

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     *
     * @param rhsPanel The right-hand side panel instance to update on selection change
     */
    public PersonListPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        selectedIndex = logic.getObservableIndex();
        selectedIndex.addListener(this::handleModelIndexChange);

        personListView.setItems(logic.getSortedAndFilteredPersonList());
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.getSelectionModel().selectedItemProperty().addListener(this::handleUserSelectionChange);
        personListView.getSelectionModel().selectedIndexProperty().addListener(this::handleIndexSelectionChange);
    }

    private void handleUserSelectionChange(ObservableValue<? extends Person> ov, Person oldPerson, Person newPerson) {
        logic.setCurrentlySelectedPerson(newPerson);

    }

    private void handleModelIndexChange(ObservableValue<? extends Number> ov, Number oldInt, Number newInt) {
        if ((Integer) newInt < 0) {
            personListView.getSelectionModel().clearSelection();
        } else {
            personListView.getSelectionModel().clearAndSelect((Integer) newInt);
        }
    }

    private void handleIndexSelectionChange(ObservableValue<? extends Number> ov, Number oldIdx, Number newIdx) {
        if ((Integer) newIdx == -1) {
            logic.setObservableIndex(null);
        } else {
            Index idx = Index.fromZeroBased((Integer) newIdx);
            logic.setObservableIndex(idx);
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
