package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }

    @Test
    public void isValidTitle() {
        // null
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid titles
        assertFalse(Title.isValidTitle(""));
        assertFalse(Title.isValidTitle(" "));
        assertFalse(Title.isValidTitle(" cs2103")); // leading space

        // valid titles
        assertTrue(Title.isValidTitle("cs2103")); // exactly 3 numbers
        assertTrue(Title.isValidTitle("c"));
        assertTrue(Title.isValidTitle("CS2103 project meeting group W16-2 every monday @ RVRC")); // long title
    }
}
