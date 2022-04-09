package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.MeetingsBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingsBook;
import seedu.address.model.meeting.Duration;
import seedu.address.model.meeting.Link;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.StartTime;
import seedu.address.model.meeting.Title;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                //new Address("Blk 30 Geylang Street 29, #06-40"),
                getSocialMediaSet("Gmail, alexyeoh@gmail.com", "Telegram, alexyeoh98", "Slack, @alexyeoh98"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                //new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getSocialMediaSet("Phone, 99272758", "Email, berniceyu@example.com", "Twitter, @berniceyu"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                //new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getSocialMediaSet("Phone, 93210283", "Email, charlotte@example.com", "Facebook, Charlotte Oliveiro"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                //new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getSocialMediaSet("Phone, 91031282", "WhatsApp, 91031282", "Email, lidavid@example.com"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                //new Address("Blk 47 Tampines Street 20, #17-35"),
                getSocialMediaSet("Phone, 92492021", "Email, irfan@example.com", "Telegram, irfanI",
                        "Twitter, @notIrfanFandi"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                // new Address("Blk 45 Aljunied Street 85, #11-31"),
                getSocialMediaSet("Phone, 92624417", "Gmail, royb@gmail.com", "NUS email, dcsroy@u.nus.edu"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }

        for (Tag tag : getSampleTags()) {
            sampleAb.addTag(tag, "tail");
        }
        return sampleAb;
    }

    public static Meeting[] getSampleMeetings() {
        return new Meeting[] {
            new Meeting(new Title("CS2103 Meeting"), new Link("https://www.zoom.sg"),
                    new StartTime("2022-3-14 1700"),
                    new Duration(120), getTagSet("friends")),
            new Meeting(new Title("CS2106 Project Presentation"), new Link("https://www.zoom.sg"),
                    new StartTime("2022-4-10 1700"),
                    new Duration(120), getTagSet("friends")),
            new Meeting(new Title("CS2102 Demonstration"), new Link("https://www.google.com"),
                    new StartTime("2022-3-29 0900"),
                    new Duration(60), getTagSet("friends")),
        };
    }
    public static ReadOnlyMeetingsBook getSampleMeetingsBook() {
        MeetingsBook sampleMt = new MeetingsBook();
        for (Meeting sampleMeeting : getSampleMeetings()) {
            sampleMt.addMeeting(sampleMeeting);
        }
        return sampleMt;
    }

    public static Tag[] getSampleTags() {
        return new Tag[] {
            new Tag("neighbours"), new Tag("colleagues"), new Tag("friends"),
            new Tag("EmergencyContacts"), new Tag("family")
        };
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a set of social media containing the list of strings given.
     */
    public static List<SocialMedia> getSocialMediaSet(String... strings) {
        return Arrays.stream(strings)
                .map(str -> {
                    try {
                        return ParserUtil.parseSocialMedia(str);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
