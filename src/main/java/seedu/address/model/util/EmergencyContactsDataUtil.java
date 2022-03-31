package seedu.address.model.util;

import seedu.address.model.EmergencyContact;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class EmergencyContactsDataUtil {
    public static EmergencyContact[] getEmergencyContacts() {
        return new EmergencyContact[]{
            new EmergencyContact(new Name("Campus Security"), new Phone("68741616"), new Email("ces@nus.edu.sg")),
            new EmergencyContact(new Name("Lifeline NUS"), new Phone("65167777"), new Email("ces@nus.edu.sg")),
            new EmergencyContact(new Name("Office of Safety Health and Environment Emergency Hotline"),
                    new Phone("67786304"), new Email("ces@nus.edu.sg"))
        };
    }

    public static Tag getEmergencyContactTag() {
        return new Tag("EmergencyContacts");
    }
}
