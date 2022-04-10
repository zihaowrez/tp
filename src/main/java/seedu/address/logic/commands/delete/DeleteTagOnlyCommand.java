package seedu.address.logic.commands.delete;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.util.EmergencyContactsDataUtil.getEmergencyContactTag;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Deletes (@code Tag} and removes {@code Tag} from every {@code Person} that has that particular {@code Tag}.
 */
public class DeleteTagOnlyCommand extends DeleteCommand {
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag %s not found in tag list!";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    private Tag tagToDelete;

    /**
     * @param tagToDelete the tag to delete
     */
    public DeleteTagOnlyCommand(Tag tagToDelete) {

        this.tagToDelete = tagToDelete;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);

        if (tagToDelete.equals(getEmergencyContactTag())) {
            throw new CommandException("Emergency Contact tag cannot be deleted");
        }

        List<Person> lastShownList = model.getSortedAndFilteredPersonList();
        List<Tag> tagList = model.getFilteredTagList();
        List<Meeting> meetingList = model.getSortedAndFilteredMeetingList();

        if (!tagList.contains(tagToDelete)) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagToDelete));
        }

        model.deleteTag(tagToDelete);

        for (Person targetPerson: lastShownList) {
            if (targetPerson.getTags().contains(tagToDelete)) {
                Set<Tag> personsTags = targetPerson.getTags();
                Set<Tag> updatedTags = new HashSet<>(personsTags);
                updatedTags.remove(tagToDelete);

                Person updatedPerson = createUpdatedPerson(targetPerson, updatedTags);

                model.setPerson(targetPerson, updatedPerson);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            }
        }

        for (Meeting targetMeeting: meetingList) {
            if (targetMeeting.getTags().contains(tagToDelete)) {
                Set<Tag> meetingTags = targetMeeting.getTags();
                Set<Tag> updatedTags = new HashSet<>(meetingTags);
                updatedTags.remove(tagToDelete);

                Meeting updatedMeeting = createUpdatedMeeting(targetMeeting, updatedTags);

                model.setMeeting(targetMeeting, updatedMeeting);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            }
        }


        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagOnlyCommand // instanceof handles nulls
                && tagToDelete.equals(((DeleteTagOnlyCommand) other).tagToDelete));
    }

    private Person createUpdatedPerson(Person personToEdit, Set<Tag> updatedTags) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        List<SocialMedia> socialMedias = personToEdit.getSocialMedias();

        return new Person(name, phone, email, socialMedias, updatedTags);
    }

    private Meeting createUpdatedMeeting(Meeting meetingToEdit, Set<Tag> updatedTags) {
        assert meetingToEdit != null;

        Title title = meetingToEdit.getTitle();
        Duration duration = meetingToEdit.getDuration();
        Link link = meetingToEdit.getLink();
        StartTime startTime = meetingToEdit.getStartTime();

        return new Meeting(title, link, startTime, duration, updatedTags);
    }
}
