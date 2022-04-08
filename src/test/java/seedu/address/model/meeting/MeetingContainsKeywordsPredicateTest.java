package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MeetingBuilder;

public class MeetingContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MeetingContainsKeywordsPredicate firstPredicate =
                new MeetingContainsKeywordsPredicate(firstPredicateKeywordList);
        MeetingContainsKeywordsPredicate secondPredicate =
                new MeetingContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MeetingContainsKeywordsPredicate firstPredicateCopy =
                new MeetingContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different meeting -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_keywordContainsTitle_returnsTrue() {
        // Full
        MeetingContainsKeywordsPredicate predicate =
                new MeetingContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").build()));

        // Partial
        predicate = new MeetingContainsKeywordsPredicate(Collections.singletonList("lic"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").build()));

        // Only one matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("alex", "Bob"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new MeetingContainsKeywordsPredicate(Collections.singletonList("LiC"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").build()));
    }

    @Test
    public void test_keywordContainsLink_returnsTrue() {
        // Full
        MeetingContainsKeywordsPredicate predicate =
                new MeetingContainsKeywordsPredicate(Collections.singletonList("https://www.zoom.sg"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").withLink("https://www.zoom.sg").build()));

        // Partial
        predicate = new MeetingContainsKeywordsPredicate(Collections.singletonList("zoom"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").withLink("https://www.zoom.sg").build()));

        // Only one matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("alex", "zoom"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").withLink("https://www.zoom.sg").build()));
    }

    @Test
    public void test_keywordContainsStartTime_returnsTrue() {
        // Full
        MeetingContainsKeywordsPredicate predicate =
                new MeetingContainsKeywordsPredicate(Collections.singletonList("2022-05-07 14:00"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").withStartTime("2022-5-7 1400").build()));

        // Partial
        predicate = new MeetingContainsKeywordsPredicate(Collections.singletonList("05-07"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").withStartTime("2022-5-7 1400").build()));

        // Only one matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("alex", "05-07"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").withStartTime("2022-5-7 1400").build()));
    }

    @Test
    public void test_keywordContainsDuration_returnsTrue() {
        MeetingContainsKeywordsPredicate predicate =
                new MeetingContainsKeywordsPredicate(Collections.singletonList("60"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").withDuration(60).build()));
    }

    @Test
    public void test_keywordContainsTag_returnsTrue() {
        // Full
        MeetingContainsKeywordsPredicate predicate =
                new MeetingContainsKeywordsPredicate(Collections.singletonList("cs2103"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").withTags("cs2103", "cs1231").build()));

        // Partial
        predicate = new MeetingContainsKeywordsPredicate(Collections.singletonList("cs"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").withTags("cs2103", "cs1231").build()));

        // Only one matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("alex", "cs1231"));
        assertTrue(predicate.test(new MeetingBuilder().withTitle("Alice Bob").withTags("cs2103", "cs1231").build()));
    }

    @Test
    public void test_meetingDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MeetingContainsKeywordsPredicate predicate =
                new MeetingContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Alice").withLink("https://www.zoom.sg")
                .withStartTime("2022-5-7 1400").withDuration(60).withTags("cs2103", "cs1231").build()));

        // Non-matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Collections.singletonList("java"));
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Alice").withLink("https://www.zoom.sg")
                .withStartTime("2022-5-7 1400").withDuration(60).withTags("cs2103", "cs1231").build()));

        // Non-matching keywords
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("Alice's", "mother", "likes", "coffee"));
        assertFalse(predicate.test(new MeetingBuilder().withTitle("Alice").withLink("https://www.zoom.sg")
                .withStartTime("2022-5-7 1400").withDuration(60).withTags("cs2103", "cs1231").build()));

    }
}
