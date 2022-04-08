package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LinkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Link(null));
    }

    @Test
    public void constructor_invalidLink_throwsIllegalArgumentException() {
        String invalidLink = "   ";
        assertThrows(IllegalArgumentException.class, () -> new Link(invalidLink));
    }

    @Test
    public void isValidLink() {
        // null
        assertThrows(NullPointerException.class, () -> Link.isValidLink(null));

        // blank
        assertFalse(Link.isValidLink(""));
        assertFalse(Link.isValidLink(" "));

        // not a url
        assertFalse(Link.isValidLink("123"));
        assertFalse(Link.isValidLink("abcde"));
        assertFalse(Link.isValidLink("12.34.56"));
        assertFalse(Link.isValidLink("abc://123.456"));

        // incomplete url
        assertFalse(Link.isValidLink("https://"));
        assertFalse(Link.isValidLink("https://www"));

        // The following tests are taken from https://stackoverflow.com/questions/9715606/bad-url-test-cases
        // invalid urls
        assertFalse(Link.isValidLink("https://www.goo gle.com")); // white space
        assertFalse(Link.isValidLink("https:/www.google.com")); // missing slash

        // valid urls
        assertTrue(Link.isValidLink("https://www.google.com")); // real url 1
        assertTrue(Link.isValidLink(
                "https://luminus.nus.edu.sg/modules/f04838c8-fef3-4b8e-89a2-67af01b3de83/conferencing")); // real 2
        assertTrue(Link.isValidLink("https://www.google.com/")); // trailing slash
        assertTrue(Link.isValidLink("https://www.example.com:8800")); // port
        assertTrue(Link.isValidLink("https://www.example.com/a/b/c/d/e/f/g/h/i.html")); // deep path
        assertTrue(Link.isValidLink("https://www.example.com?pageid=123&testid=1524")); // parameters
        assertTrue(Link.isValidLink("https://www.test.com/do.html#A")); // anchor tag
    }
}
