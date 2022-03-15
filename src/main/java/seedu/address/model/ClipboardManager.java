package seedu.address.model;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import seedu.address.model.person.Person;

public class ClipboardManager {

    public ClipboardManager(){

    }

    public void copy(Person toCopy) {

        String str = toCopy.toString();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);

    }

}
