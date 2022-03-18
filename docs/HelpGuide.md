# HelpGuide


*uMessage* is a desktop app that helps university students manage contacts, academics and CCAs that communicate and hold meetings on various platforms. It is optimized for keyboard users.

## Features

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME t/TAG`, `t/TAG n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `n/John n/Joe`, only `n/Joe` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.


### Viewing help : `help`

Displays a new window showing `HelpGuide.md` as the help page.


Format: `help`


### Adding: `add`

Adds either:
1. a person, or
2. a new tag to an existing person.

**Format:**
**1. Adding a person:**
- `add n/NAME [d/{SOCIAL MEDIA PLATFORM}, {ID}]… [t/TAG]…`

_Tip: A person can have one or more details_
_Tip: A person can have zero or more tags_

The newly added person will be placed at the top of the contact list.

**Examples:**

> `add n/John Doe d/Telegram, johndoe`
> `add n/Betsy Crowe d/Phone, 99900099 d/Email, e0123456@gmail.com`
> `add n/Potter d/Phone, 49502583 t/cs2030`

**2. Adding new tag to a person:**
- `add NAME t/NEWTAG`
- `add INDEX t/NEWTAG`

_Tip: Specify the person that you want to add the tag to by using their full name or their index in the contact list_

**Examples:**

> `add John Doe t/friend`
> `add 2 t/friend`

### Copying a person : `copy`

**Format:** `copy NAME`

Deletes the person with the specified NAME.
NAME is insensitive (like find)

**Examples:**

> `copy David Li`
> `copy Bernice Yu`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Locating persons by name : `[find]`

Finds persons with the given keywords.

**Format:**  `[find] KEYWORD [MORE_KEYWORDS]`

If the first keyword is a command word, it will still be recognised as a command and will not be treated as keywords. The whole list will be displayed when a command word is entered.

The keywords are compared to all fields of each person.
Persons matching at least one keyword will be returned (i.e. OR search). e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.
The search is case-insensitive. e.g `hans` will match `Hans`.
The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.

**Examples:**

> `find a` will return `Alex Yeoh`, `Bernice` with the tag `colleagues`, `Lin` with the email `lin@example.com` (if these contacts exist).
> `a` does the same as above.
> `find alex yeoh` will return both `Alex` and `Alex Yeoh` (if both contacts exist).
When the user types `add`, the whole list is displayed.

### Deletion : `delete`

Deletes either:
1. a person, or
2. a tag from an existing person.

**Format:**

**1. Deleting Person:** Deletes the person with the specified `NAME` or `INDEX`.
- `delete NAME`
- `delete INDEX`

_Tip: Deletion by name is case sensitive and must match the full name._
_Tip: The index must be a positive integer 1, 2, 3, ..._

**Examples:**
> `delete 2` deletes the contact at index 2
> `delete Alex Yeoh` deletes the contact `Alex Yeoh`
> `delete Alex` or `delete alex yeoh` will not delete `Alex Yeoh`

**2. Deleting Tag from a Person:** Deletes tag `TAG` from a person specified using `NAME` or `INDEX`.
- `delete NAME t/TAG`
- `delete INDEX t/TAG`

**Examples:**
> `delete kaaviya t/friend`
> `delete 1 t/colleague`


### Viewing a person : `view`

**Format:**
`view INDEX`

Views the contact details of the person with the selected index in the RHS window.

**Examples:**

`view 2`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.


