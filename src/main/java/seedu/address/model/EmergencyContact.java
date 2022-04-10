package seedu.address.model;

import static seedu.address.model.util.SampleDataUtil.getSocialMediaSet;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class EmergencyContact extends Person {

    public static final String CANNOT_ADD_EMERGENCY_CONTACT_TAG = "Emergency Contact Tag cannot be added";

    public EmergencyContact(Name name, Phone phone, Email email) {
        super(name, phone, email, getSocialMediaSet("NIL"), getTagSet("EmergencyContacts"));
    }
}
