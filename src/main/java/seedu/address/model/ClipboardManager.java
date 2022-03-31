package seedu.address.model;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class ClipboardManager {

    /**
     * Initializes a ClipboardManager
     */
    public ClipboardManager(){

    }

    /**
     * Copies the person to the system clipboard.
     * @param toCopy
     */
    public void copy(Person toCopy) {

        String str = toCopy.toString();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);

    }

    /**
     * Copies the meeting to the system clipboard.
     * @param toCopy
     */
    public void copy(Meeting toCopy) {
        String str = toCopy.toString();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);
    }

    /**
     * Copies the string value to the system clipboard.
     * @param toCopy
     */
    public void copy(String toCopy) {
        String str = toCopy.toString();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);
    }

    /**
     * Copies the tag to the system clipboard.
     * @param toCopy
     */
    public void copy(Tag toCopy) {
        String str = toCopy.toString();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);
    }
}
