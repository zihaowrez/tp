package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container

    private PersonListPanel personListPanel;
    private ContactDetailPanel contactDetailPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    private MeetingListPanel meetingListPanel;
    private UpcomingMeetingListPanel upcomingMeetingListPanel;
    private ResultDisplay meetingsResultDisplay;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab contactsTab;

    @FXML
    private Tab meetingsTab;

    @FXML
    private Tab helpTab;

    @FXML
    private Menu helpMenu;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane contactDetailPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane meetingsCommandBoxPlaceholder;

    @FXML
    private StackPane meetingListPanelPlaceholder;

    @FXML
    private StackPane upcomingMeetingListPanelPlaceholder;

    @FXML
    private StackPane meetingsResultDisplayPlaceholder;

    @FXML
    private StackPane meetingsStatusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenu, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });

    }

    /**
     * Sets the accelerator of a Menu.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(Menu menu, KeyCombination keyCombination) {
        menu.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menu.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });

    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        personListPanel = new PersonListPanel(logic.getSortedAndFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        contactDetailPanel = new ContactDetailPanel(logic.getContactDetails());
        contactDetailPanelPlaceholder.getChildren().add(contactDetailPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommandForContacts);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        meetingListPanel = new MeetingListPanel(logic.getSortedAndFilteredMeetingList());
        meetingListPanelPlaceholder.getChildren().add(meetingListPanel.getRoot());

        upcomingMeetingListPanel = new UpcomingMeetingListPanel(logic.getUpcomingMeetingList());
        upcomingMeetingListPanelPlaceholder.getChildren().add(upcomingMeetingListPanel.getRoot());

        meetingsResultDisplay = new ResultDisplay();
        meetingsResultDisplayPlaceholder.getChildren().add(meetingsResultDisplay.getRoot());

        StatusBarFooter meetingsStatusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        meetingsStatusbarPlaceholder.getChildren().add(meetingsStatusBarFooter.getRoot());

        CommandBox meetingCommandBox = new CommandBox(this::executeCommandForMeetings);
        meetingsCommandBoxPlaceholder.getChildren().add(meetingCommandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
            helpWindow.focus();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#executeForContacts(String, CommandBox) (String, CommandBox)
     */
    private CommandResult executeCommandForContacts(String commandText, CommandBox commandBox)
            throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.executeForContacts(commandText, commandBox);
            if (commandBox.isDynamic()) {
                logger.info("Result: " + commandResult.getFeedbackToUser());
            }
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;

        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#executeForContacts(String, CommandBox) (String, CommandBox)
     */
    private CommandResult executeCommandForMeetings(String commandText, CommandBox commandBox)
            throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.executeForMeetings(commandText, commandBox);
            if (commandBox.isDynamic()) {
                logger.info("Result: " + commandResult.getFeedbackToUser());
            }
            meetingsResultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;

        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            meetingsResultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

}
