package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DurationTest {

    @Test
    public void constructor_invalidDuration_throwsIllegalArgumentException() {
        int invalidDuration = -1;
        assertThrows(IllegalArgumentException.class, () -> new Duration(invalidDuration));
    }

    @Test
    public void isValidDuration() {
        // invalid Durations
        assertFalse(Duration.isValidDuration(-1));
        assertFalse(Duration.isValidDuration(0));
        assertFalse(Duration.isValidDuration(10001));
        assertFalse(Duration.isValidDuration(20000));

        // valid Durations
        assertTrue(Duration.isValidDuration(1));
        assertTrue(Duration.isValidDuration(60));
    }
}
