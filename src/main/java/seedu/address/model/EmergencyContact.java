package seedu.address.model;

import static seedu.address.model.util.SampleDataUtil.getSocialMediaSet;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class EmergencyContact extends Person {
    public EmergencyContact(Name name, Phone phone, Email email) {
        super(name, phone, email, getSocialMediaSet("NIL"), getTagSet("EmergencyContacts"));
    }
}
