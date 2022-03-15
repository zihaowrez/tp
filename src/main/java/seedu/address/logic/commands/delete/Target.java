package seedu.address.logic.commands.delete;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Encapsulates a targeted Person on a given list of persons, identified 
 * either by {@code Name} or {@code Index}.
 */
public abstract class Target {

    private static final String NO_TARGET_LIST = "Cannot identify target on a list when no list is specified!";

    protected List<Person> persons;

    private Target(List<Person> persons) {
        this.persons = persons;
    }

    /**
     * Factory method for Target. Returns the correct subtype of target given a {@code target}.
     */
    public static Target of(Name target, List<Person> persons) {
        return new NamedTarget(target, persons);
    }

    /**
     * Factory method for Target. Returns the correct subtype of target given a {@code target}.
     */
    public static Target of(Index target, List<Person> persons) {
        return new IndexedTarget(target, persons);
    }

    /**
     * Sets the list of persons that this target points to.
     * @param persons The list of persons this target points to.
     */
    public void setTargetList(List<Person> persons) {
        this.persons = persons;
    }

    /** Returns the {@code Person} that is target */
    public abstract Person targetPerson() throws CommandException;

    @Override
    public abstract boolean equals(Object obj);
    /*----------------------Start of private classes---------------------------------------*/

    private static class NamedTarget extends Target {
        public static final String MESSAGE_PERSON_NOT_EXIST = "The person with full name %1$s does not exist!";
        Name targetName;

        protected NamedTarget(Name targetName) {
            super(null);
            this.targetName = targetName;
        }

        protected NamedTarget(Name targetName, List<Person> persons) {
            super(persons);
            this.targetName = targetName;
        }

        @Override
        public Person targetPerson() throws CommandException {
            if (persons == null) {
                throw new CommandException(NO_TARGET_LIST);
            }

            Optional<Person> targetPersonOptional = persons
                .stream()
                .filter(person -> person.getName().equals(targetName))
                .findFirst();

            if (targetPersonOptional.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_PERSON_NOT_EXIST, targetName));
            }

            Person targettedPerson = targetPersonOptional.get();

            return targettedPerson;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof NamedTarget) {
                NamedTarget other = (NamedTarget) obj;
                Optional<List<Person>> personsOptional = Optional.ofNullable(persons);
                return this.targetName.equals(other.targetName)
                    && personsOptional.equals(Optional.ofNullable(other.persons));
            }
            
            return false;
            
        }
    }

    private static class IndexedTarget extends Target {
        Index targetIndex;

        protected IndexedTarget(Index targetIndex) {
            super(null);
            this.targetIndex = targetIndex;
        }

        protected IndexedTarget(Index targetIndex, List<Person> persons) {
            super(persons);
            this.targetIndex = targetIndex;
        }

        @Override
        public Person targetPerson() throws CommandException {
            if (persons == null) {
                throw new CommandException(NO_TARGET_LIST);
            }

            if (targetIndex.getZeroBased() >= persons.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person targettedPerson = persons.get(targetIndex.getZeroBased());
            return targettedPerson;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof IndexedTarget) {
                IndexedTarget other = (IndexedTarget) obj;
                Optional<List<Person>> personsOptional = Optional.ofNullable(persons);
                return this.targetIndex.equals(other.targetIndex)
                    && personsOptional.equals(Optional.ofNullable(other.persons));
            }
            
            return false;
            
        }
    }
}
