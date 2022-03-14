package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_keywordContainsName_returnsTrue() {
        // Full
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Partial
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("lic"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("alex", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("LiC"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_keywordContainsPhone_returnsTrue() {
        // Full
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("73063958"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("73063958").build()));

        // Partial
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("7306"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("73063958").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("alex", "73063958"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("73063958").build()));
    }

    @Test
    public void test_keywordContainsEmail_returnsTrue() {
        // Full
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("e091963@u.nus.edu"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withEmail("e091963@u.nus.edu").build()));

        // Partial
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("0919"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withEmail("e091963@u.nus.edu").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("alex", "0919"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withEmail("e091963@u.nus.edu").build()));
    }

    @Test
    public void test_keywordContainsSocialMedia_returnsTrue() {
        // Full platform name
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Telegram"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withSocials("Telegram, @handle").build()));

        // Partial platform name
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("tele"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withSocials("Telegram, @handle").build()));

        // Full platform description
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("@handle"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withSocials("Telegram, @handle").build()));

        // Partial platform description
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("hand"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withSocials("Telegram, @handle").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("alex", "@handle"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withSocials("Telegram, @handle").build()));
    }

    @Test
    public void test_keywordContainsTag_returnsTrue() {
        // Full
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("cs2103"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("cs2103", "cs1231").build()));

        // Partial
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("cs"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("cs2103", "cs1231").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("alex", "cs1231"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("cs2103", "cs1231").build()));
    }

    @Test
    public void test_personDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("73063958")
                .withEmail("e091963@u.nus.edu").withSocials("Telegram, @handle").withTags("cs2103", "cs1231").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("java"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("73063958")
                .withEmail("e091963@u.nus.edu").withSocials("Telegram, @handle").withTags("cs2103", "cs1231").build()));

        // Non-matching keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice's", "mother", "likes", "coffee"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("73063958")
                .withEmail("e091963@u.nus.edu").withSocials("Telegram, @handle").withTags("cs2103", "cs1231").build()));

    }
}
