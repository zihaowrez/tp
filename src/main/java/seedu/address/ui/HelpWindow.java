package seedu.address.ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.sandec.mdfx.MarkdownView;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;





/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_PATH = Paths.get("docs", "UserGuide.md").toString();

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private ScrollPane content;


    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */

    public HelpWindow(Stage root) {

        super(FXML, root);

        String mdfxTxt;
        try {
            mdfxTxt = IOUtils.toString(new FileInputStream(USERGUIDE_PATH), StandardCharsets.UTF_8);
        } catch (IOException | NullPointerException e) { // could not find path
            logger.info("Invalid path! ");
            mdfxTxt = "This page is empty!";
        }

        MarkdownView mdfx = new MarkdownView(mdfxTxt);

        content = new ScrollPane(mdfx);

        content.setFitToWidth(true);

        Scene scene = new Scene(content, 700, 700);

        root.setScene(scene);





    }


    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }


}
