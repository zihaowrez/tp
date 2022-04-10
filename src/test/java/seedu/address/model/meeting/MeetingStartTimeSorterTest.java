package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MeetingBuilder;

public class MeetingStartTimeSorterTest {

    @Test
    public void test_intendedSequence() {

        DateTimeFormatter formatter = StartTime.FORMATTER;

        // Intended sequence: future meetings ascending, followed by past meetings descending
        Meeting m1 = new MeetingBuilder().withTitle("Meeting 1")
                .withStartTime(LocalDateTime.now().plusMinutes(9).format(formatter)).build();
        Meeting m2 = new MeetingBuilder().withTitle("Meeting 2")
                .withStartTime(LocalDateTime.now().plusDays(1).format(formatter)).build();
        Meeting m3 = new MeetingBuilder().withTitle("Meeting 3")
                .withStartTime(LocalDateTime.now().plusYears(1).format(formatter)).build();
        Meeting m4 = new MeetingBuilder().withTitle("Meeting 4")
                .withStartTime(LocalDateTime.now().minusMinutes(1).format(formatter)).build();
        Meeting m5 = new MeetingBuilder().withTitle("Meeting 5")
                .withStartTime(LocalDateTime.now().minusHours(1).format(formatter)).build();
        Meeting m6 = new MeetingBuilder().withTitle("Meeting 6")
                .withStartTime(LocalDateTime.now().minusYears(1).format(formatter)).build();

        MeetingStartTimeSorter comparator = new MeetingStartTimeSorter();
        assertTrue(comparator.compare(m1, m2) < 0);
        assertTrue(comparator.compare(m2, m3) < 0);
        assertTrue(comparator.compare(m3, m4) < 0);
        assertTrue(comparator.compare(m4, m5) < 0);
        assertTrue(comparator.compare(m5, m6) < 0);

    }

}
