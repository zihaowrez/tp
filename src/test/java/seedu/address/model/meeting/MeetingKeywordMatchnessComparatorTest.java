package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MeetingBuilder;

public class MeetingKeywordMatchnessComparatorTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MeetingKeywordMatchnessComparator firstPredicate =
                new MeetingKeywordMatchnessComparator(firstPredicateKeywordList);
        MeetingKeywordMatchnessComparator secondPredicate =
                new MeetingKeywordMatchnessComparator(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MeetingKeywordMatchnessComparator firstPredicateCopy =
                new MeetingKeywordMatchnessComparator(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different meeting -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_firstTitleMatchesMoreThanSecondTitle_firstMatchesMore() {

        Meeting m1 = new MeetingBuilder().withTitle("Alex").build();
        Meeting m2 = new MeetingBuilder().withTitle("Alexandra").build();
        Meeting m3 = new MeetingBuilder().withTitle("Alex Yeoh").build();
        Meeting m4 = new MeetingBuilder().withTitle("Bob").build();

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
