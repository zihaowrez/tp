package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Encapsulates a indexed or named target on a given list of persons, identified
 * either by their {@code Name} or {@code Index}.
 */
public class Target {
    public static final String MESSAGE_PERSON_NOT_EXIST = "The person with full name %1$s does not exist!";

    private String target;

    /**
     * @param target the string to be treated as a target.
     * @throws ParseException if the string provided is not a valid index or name.
     */
    public Target(String target) throws ParseException {

        Optional<ParseException> indexParseExceptionOptional = tryParseIndex(target);
        Optional<ParseException> nameParseExceptionOptional = tryParseName(target);

        if (indexParseExceptionOptional.isPresent() && nameParseExceptionOptional.isPresent()
            && StringUtil.isInt(target)) {
            throw indexParseExceptionOptional.get();
        } else if (indexParseExceptionOptional.isPresent() && nameParseExceptionOptional.isPresent()) {
            throw nameParseExceptionOptional.get();
        }

        this.target = target;
    }

    /**
     * Overloaded constructor for Target
     * @param target the Index to be treated as a target.
     */
    public Target(Index target) {
        this.target = String.valueOf(target.getOneBased());
    }

    /**
     * Overloaded constructor for Target
     * @param target the Name to be treated as a target.
     */
    public Target(Name target) {
        this.target = target.toString();
    }

    /** Returns the {@code Person} that is targetted */
    public Person targetPerson(List<Person> targetList) throws CommandException {
        assert targetList != null;

        Name targetName = parseName(target); //try name
        Optional<Person> targetByNameOptional = targetList //find target person with said name
            .stream()
            .filter(person -> person.getName().equals(targetName))
            .findFirst();

        if (StringUtil.isInt(target) && targetByNameOptional.isPresent()) { //check for target type confusion
            Optional<Person> targetByIndexOptional = getPersonWithIndex(targetList, target);
            return targetByIndexOptional.orElse(targetByNameOptional.get());

        } else if (StringUtil.isInt(target)) { //failed to target by name
            Index targetIndex = parseIndex(target);
            if (targetIndex.getZeroBased() >= targetList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person targettedPerson = targetList.get(targetIndex.getZeroBased());
            return targettedPerson;

        } else {
            return targetByNameOptional.orElseThrow(() ->
                    new CommandException(String.format(MESSAGE_PERSON_NOT_EXIST, targetName)));
        }
    }

    private Optional<Person> getPersonWithIndex(List<Person> targetList, String idxString) {
        Index targetIndex;
        try {
            targetIndex = ParserUtil.parseIndex(target);
        } catch (ParseException e) {
            return Optional.empty();
        }

        if (targetIndex.getZeroBased() >= targetList.size()) {
            return Optional.empty();
        }

        Person targettedPerson = targetList.get(targetIndex.getZeroBased());
        return Optional.of(targettedPerson);
    }

    private Name parseName(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        return new Name(trimmedName);
    }

    private Index parseIndex(String oneBasedIndex) {
        String trimmedIndex = oneBasedIndex.trim();
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    private Optional<ParseException> tryParseIndex(String target) {
        try {
            ParserUtil.parseIndex(target);
            return Optional.empty();
        } catch (ParseException p) {
            return Optional.of(p);
        }
    }

    private Optional<ParseException> tryParseName(String target) {
        try {
            ParserUtil.parseName(target);
            return Optional.empty();
        } catch (ParseException p) {
            return Optional.of(p);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Target) {
            Target otherTarget = (Target) obj;
            return this.target.equals(otherTarget.target);
        }

        return false;
    }
}
