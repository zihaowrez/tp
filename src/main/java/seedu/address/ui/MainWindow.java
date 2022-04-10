package seedu.address.ui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.sandec.mdfx.MarkdownView;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private String mdfxTxt;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container

    private PersonListPanel personListPanel;
    private ContactDetailPanel rightHandSidePanel;
    private ResultDisplay resultDisplay;
    private MeetingListPanel meetingListPanel;
    private ResultDisplay meetingsResultDisplay;
    private TagPanel tagPanelForContacts;
    private TagPanel tagPanelForMeetings;


    @FXML
    private TabPane tabPane;

    @FXML
    private Tab contactsTab;

    @FXML
    private Tab meetingsTab;

    @FXML
    private Tab helpTab;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane contactDetailPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane tagPanelInContactsPlaceholder;

    @FXML
    private StackPane tagPanelInMeetingsPlaceholder;

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

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private MarkdownView helpView;

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

        try {
            mdfxTxt = IOUtils.toString(MainWindow.class.getResourceAsStream("/assets/HelpGuide.md"),
                    StandardCharsets.UTF_8);
        } catch (IOException | NullPointerException e) { // could not find path
            logger.info("Invalid path! ");
            mdfxTxt = "This page is empty!";
        }

        // Set Markdown in Help tab
        helpView = new MarkdownView(mdfxTxt);
        helpView.setPadding(new Insets(40));

        scrollPane.setContent(helpView);
        scrollPane.setFitToWidth(true);
        helpView.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 3; // * 3 to make the scrolling a bit faster
            double height = helpView.getBoundsInLocal().getHeight();
            double vvalue = scrollPane.getVvalue();
            scrollPane.setVvalue(vvalue - deltaY / height);
            // deltaY / height to make the scrolling equally fast regardless of the total height
        });

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        personListPanel = new PersonListPanel(logic);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        tagPanelForContacts = new TagPanel(logic.getFilteredTagList(), logic, this);
        tagPanelInContactsPlaceholder.getChildren().add(tagPanelForContacts.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        rightHandSidePanel = new ContactDetailPanel(logic.getCurrentlySelectedPerson(), resultDisplay);
        contactDetailPanelPlaceholder.getChildren().add(rightHandSidePanel.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommandForContacts);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        meetingListPanel = new MeetingListPanel(logic.getSortedAndFilteredMeetingList());
        meetingListPanelPlaceholder.getChildren().add(meetingListPanel.getRoot());

        meetingsResultDisplay = new ResultDisplay();
        meetingsResultDisplayPlaceholder.getChildren().add(meetingsResultDisplay.getRoot());

        StatusBarFooter meetingsStatusBarFooter = new StatusBarFooter(logic.getMeetingsBookFilePath());
        meetingsStatusbarPlaceholder.getChildren().add(meetingsStatusBarFooter.getRoot());

        tagPanelForMeetings = new TagPanel(logic.getFilteredTagList(), logic, this);
        tagPanelInMeetingsPlaceholder.getChildren().add(tagPanelForMeetings.getRoot());

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
        tabPane.getSelectionModel().select(helpTab);
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
            tagPanelForContacts.setPanel(new TagCard(logic.getFilteredTagList(), logic, this).getRoot());
            tagPanelForMeetings.setPanel(new TagCard(logic.getFilteredTagList(), logic, this).getRoot());

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
            tagPanelForContacts.setPanel(new TagCard(logic.getFilteredTagList(), logic, this).getRoot());
            tagPanelForMeetings.setPanel(new TagCard(logic.getFilteredTagList(), logic, this).getRoot());

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

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#executeForContacts(String, CommandBox) (String, CommandBox)
     */
    protected CommandResult[] clickTag(Tag tag) {
        assert tag != null;
        CommandResult[] commandResults = logic.clickTag(tag);
        logger.info("Tag " + tag + " clicked");
        resultDisplay.setFeedbackToUser(commandResults[0].getFeedbackToUser());
        meetingsResultDisplay.setFeedbackToUser(commandResults[1].getFeedbackToUser());
        return commandResults;
    }

}
