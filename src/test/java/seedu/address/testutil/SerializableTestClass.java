package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.meeting.StartTime;


/**
 * A class used to test serialization and deserialization
 */
public class SerializableTestClass {
    public static final String JSON_STRING_REPRESENTATION = String.format("{%n"
            + "  \"name\" : \"This is a test class\",%n"
            + "  \"listOfStartTimes\" : "
            + "[ \"-999999999-01-01T00:00:00\", \"+999999999-12-31T23:59:59.999999999\", "
            + "\"0001-01-01T01:01:00\" ],%n"
            + "  \"mapOfIntegerToString\" : {%n"
            + "    \"1\" : \"One\",%n"
            + "    \"2\" : \"Two\",%n"
            + "    \"3\" : \"Three\"%n"
            + "  }%n"
            + "}");

    private static final String NAME_TEST_VALUE = "This is a test class";

    private String name;

    private List<StartTime> listOfStartTimes;
    private HashMap<Integer, String> mapOfIntegerToString;

    public static String getNameTestValue() {
        return NAME_TEST_VALUE;
    }

    public static List<StartTime> getListTestValues() {
        List<StartTime> listOfStartTimes = new ArrayList<>();

        return listOfStartTimes;
    }

    public static HashMap<Integer, String> getHashMapTestValues() {
        HashMap<Integer, String> mapOfIntegerToString = new HashMap<>();

        mapOfIntegerToString.put(1, "One");
        mapOfIntegerToString.put(2, "Two");
        mapOfIntegerToString.put(3, "Three");

        return mapOfIntegerToString;
    }

    public void setTestValues() {
        name = getNameTestValue();
        listOfStartTimes = getListTestValues();
        mapOfIntegerToString = getHashMapTestValues();
    }

    public String getName() {
        return name;
    }

    public List<StartTime> getListOfStartTimes() {
        return listOfStartTimes;
    }

    public HashMap<Integer, String> getMapOfIntegerToString() {
        return mapOfIntegerToString;
    }
}
