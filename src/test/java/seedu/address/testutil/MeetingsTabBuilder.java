package seedu.address.testutil;

import seedu.address.model.MeetingsBook;
import seedu.address.model.meeting.Meeting;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class MeetingsTabBuilder {

    private MeetingsBook meetingsBook;

    public MeetingsTabBuilder() {
        meetingsBook = new MeetingsBook();
    }

    public MeetingsTabBuilder(MeetingsBook meetingsBook) {
        this.meetingsBook = meetingsBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public MeetingsTabBuilder withMeeting(Meeting meeting) {
        meetingsBook.addMeeting(meeting);
        return this;
    }

    public MeetingsBook build() {
        return meetingsBook;
    }
}
