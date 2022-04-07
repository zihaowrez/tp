package seedu.address.model.meeting;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.MeetingBuilder;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeetingStartTimeSorterTest {

    @Test
    public void test_firstTitleMatchesMoreThanSecondTitle_firstMatchesMore() {

        Meeting m1 = new MeetingBuilder().withTitle("Meeting 1").withStartTime("").build();
        Meeting m2 = new MeetingBuilder().withTitle("Meeting 2").build();
        Meeting m3 = new MeetingBuilder().withTitle("Meeting 3").build();
        Meeting m4 = new MeetingBuilder().withTitle("Meeting 4").build();
        Meeting m5 = new MeetingBuilder().withTitle("Meeting 5").build();
        Meeting m6 = new MeetingBuilder().withTitle("Meeting 6").build();

        MeetingKeywordMatchnessComparator comparator =
                new MeetingKeywordMatchnessComparator(Collections.singletonList("alex"));
        assertTrue(comparator.compare(m1, m2) < 0); // Full match has the highest priority
        assertTrue(comparator.compare(m1, m3) < 0); // Full match has the highest priority
        assertTrue(comparator.compare(m3, m2) < 0); // Full word matches more than partial word
        assertTrue(comparator.compare(m3, m4) < 0); // The second name does not match

        comparator = new MeetingKeywordMatchnessComparator(Collections.singletonList("alex yeoh"));
        assertTrue(comparator.compare(m3, m1) < 0); // Full match has the highest priority

    }

    @Test
    public void test_firstTagMatchesMoreThanSecondTagWhileTitlesNotMatched_firstMatchesMore() {

        Meeting m1 = new MeetingBuilder().withTitle("Alex").withTags("cs").build();
        Meeting m2 = new MeetingBuilder().withTitle("Bob").withTags("cs2100").build();
        Meeting m3 = new MeetingBuilder().withTitle("Cathy").withTags("cs2102").build();

        MeetingKeywordMatchnessComparator comparator =
                new MeetingKeywordMatchnessComparator(Collections.singletonList("cs"));
        assertTrue(comparator.compare(m1, m2) < 0); // Full word matches more than partial word
        comparator = new MeetingKeywordMatchnessComparator(Collections.singletonList("cs2100"));
        assertTrue(comparator.compare(m2, m1) < 0); // Full match has the highest priority
        assertTrue(comparator.compare(m2, m3) < 0); // Full match has the highest priority

    }

    @Test
    public void test_firstMatchesTitleButSecondDoesNot_firstMatchesMore() {

        Meeting m1 = new MeetingBuilder().withTitle("Alex").build();
        Meeting m2 = new MeetingBuilder().withTitle("Bob").withTags("alex").build();

        MeetingKeywordMatchnessComparator comparator =
                new MeetingKeywordMatchnessComparator(Collections.singletonList("alex"));
        assertTrue(comparator.compare(m1, m2) < 0);

    }

    @Test
    public void test_firstMatchesTagButSecondDoesNotMatchTitleOrTag_firstMatchesMore() {

        Meeting m1 = new MeetingBuilder().withTitle("Alex").withTags("friend").build();
        Meeting m2 = new MeetingBuilder().withTitle("Bob").withTags("alex").withStartTime("2022-5-7 1400").build();

        MeetingKeywordMatchnessComparator comparator =
                new MeetingKeywordMatchnessComparator(Collections.singletonList("friend"));
        assertTrue(comparator.compare(m1, m2) < 0);

    }

}
