package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonKeywordMatchnessComparatorTest {

    @Test
    public void equals() {
        List<String> firstComparatorKeywordList = Collections.singletonList("first");
        List<String> secondComparatorKeywordList = Arrays.asList("first", "second");

        PersonKeywordMatchnessComparator firstComparator =
                new PersonKeywordMatchnessComparator(firstComparatorKeywordList);
        PersonKeywordMatchnessComparator secondComparator =
                new PersonKeywordMatchnessComparator(secondComparatorKeywordList);

        // same object -> returns true
        assertTrue(firstComparator.equals(firstComparator));

        // same values -> returns true
        PersonKeywordMatchnessComparator firstComparatorCopy =
                new PersonKeywordMatchnessComparator(firstComparatorKeywordList);
        assertTrue(firstComparator.equals(firstComparatorCopy));

        // different types -> returns false
        assertFalse(firstComparator.equals(1));

        // null -> returns false
        assertFalse(firstComparator.equals(null));

        // different person -> returns false
        assertFalse(firstComparator.equals(secondComparator));
    }

    @Test
    public void test_firstNameMatchesMoreThanSecondName_firstMatchesMore() {

        Person p1 = new PersonBuilder().withName("Alex").build();
        Person p2 = new PersonBuilder().withName("Alexandra").build();
        Person p3 = new PersonBuilder().withName("Alex Yeoh").build();
        Person p4 = new PersonBuilder().withName("Bob").build();

        PersonKeywordMatchnessComparator comparator =
                new PersonKeywordMatchnessComparator(Collections.singletonList("alex"));
        assertTrue(comparator.compare(p1, p2) < 0); // Full match has the highest priority
        assertTrue(comparator.compare(p1, p3) < 0); // Full match has the highest priority
        assertTrue(comparator.compare(p3, p2) < 0); // Full word matches more than partial word
        assertTrue(comparator.compare(p3, p4) < 0); // The second name does not match

        comparator = new PersonKeywordMatchnessComparator(Collections.singletonList("alex yeoh"));
        assertTrue(comparator.compare(p3, p1) < 0); // Full match has the highest priority

    }

    @Test
    public void test_firstTagMatchesMoreThanSecondTagWhileNamesNotMatched_firstMatchesMore() {

        Person p1 = new PersonBuilder().withName("Alex").withTags("cs").build();
        Person p2 = new PersonBuilder().withName("Bob").withTags("cs2100").build();
        Person p3 = new PersonBuilder().withName("Cathy").withTags("cs2102").build();

        PersonKeywordMatchnessComparator comparator =
                new PersonKeywordMatchnessComparator(Collections.singletonList("cs"));
        assertTrue(comparator.compare(p1, p2) < 0); // Full word matches more than partial word
        comparator = new PersonKeywordMatchnessComparator(Collections.singletonList("cs2100"));
        assertTrue(comparator.compare(p2, p1) < 0); // Full match has the highest priority
        assertTrue(comparator.compare(p2, p3) < 0); // Full match has the highest priority

    }

    @Test
    public void test_firstMatchesNameButSecondDoesNot_firstMatchesMore() {

        Person p1 = new PersonBuilder().withName("Alex").build();
        Person p2 = new PersonBuilder().withName("Bob").withTags("alex").build();

        PersonKeywordMatchnessComparator comparator =
                new PersonKeywordMatchnessComparator(Collections.singletonList("alex"));
        assertTrue(comparator.compare(p1, p2) < 0);

    }

    @Test
    public void test_firstMatchesTagButSecondDoesNotMatchNameOrTag_firstMatchesMore() {

        Person p1 = new PersonBuilder().withName("Alex").withTags("friend").build();
        Person p2 = new PersonBuilder().withName("Bob").withTags("alex").withEmail("friend@example.com").build();

        PersonKeywordMatchnessComparator comparator =
                new PersonKeywordMatchnessComparator(Collections.singletonList("friend"));
        assertTrue(comparator.compare(p1, p2) < 0);

    }

}
