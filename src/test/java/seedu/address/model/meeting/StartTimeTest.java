package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StartTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StartTime(null));
    }

    @Test
    public void constructor_invalidStartTime_throwsIllegalArgumentException() {
        String invalidStartTime = "20220501 1700";
        assertThrows(IllegalArgumentException.class, () -> new StartTime(invalidStartTime));
    }

    @Test
    public void isValidStartTime() {
        // null
        assertThrows(NullPointerException.class, () -> StartTime.isValidStartTime(null));

        // blank
        assertFalse(StartTime.isValidStartTime(""));
        assertFalse(StartTime.isValidStartTime(" "));

        // not a time
        assertFalse(StartTime.isValidStartTime("12768720"));
        assertFalse(StartTime.isValidStartTime("peter"));

        // wrong format
        assertFalse(StartTime.isValidStartTime("20220501 1700"));
        assertFalse(StartTime.isValidStartTime("2022-5-1 17:00"));
        assertFalse(StartTime.isValidStartTime("2022-5-1 900"));
        assertFalse(StartTime.isValidStartTime("01/05/2022 1700"));
        assertFalse(StartTime.isValidStartTime("2022-5-1"));

        // invalid time
        assertFalse(StartTime.isValidStartTime("2022-4-31 1700"));
        assertFalse(StartTime.isValidStartTime("2022-5-1 1060"));
        assertFalse(StartTime.isValidStartTime("2022-2-29 1700"));
        assertFalse(StartTime.isValidStartTime("2022-6-0 1700"));

        // extra content
        assertFalse(StartTime.isValidStartTime("2022-5-1 1700 "));
        assertFalse(StartTime.isValidStartTime(" 2022-5-1 1700"));
        assertFalse(StartTime.isValidStartTime("2022-5-1 1700 1800"));
        assertFalse(StartTime.isValidStartTime("2022-5-1 1700 peter"));
        assertFalse(StartTime.isValidStartTime("2022-5-1-2 1700"));

        // valid start times
        assertTrue(StartTime.isValidStartTime("2022-5-1 1700"));
        assertTrue(StartTime.isValidStartTime("2022-05-01 1232"));
        assertTrue(StartTime.isValidStartTime("2022-5-1 0000"));
        assertTrue(StartTime.isValidStartTime("3428-1-17 2359"));
        assertTrue(StartTime.isValidStartTime("2020-2-29 0845"));
    }

}
