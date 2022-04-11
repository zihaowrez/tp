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


# Managing Contacts

The contacts are listed alphabetically. The contacts list is navigable using the arrow keys.

There cannot be duplicated contacts. Two contacts are the same if both have the same name (case-sensitive).

## 1. Adding: `add`

### 1.1 Adding a person

**Format:** `add n/NAME [p/PHONE] [e/EMAIL] [sm/{SOCIAL MEDIA PLATFORM}, {ID}]… [t/TAG]…`

**Examples:**

> `add n/John Doe p/91020111 e/JohnDoe@outlook.com sm/Telegram, @johnnydoe t/friend`</br>
> `add n/Betsy Crowe e/e0123456@gmail.com`</br>
> `add n/Potter`

### 1.2 Adding new tag to a person

**Format:** `add NAME t/NEWTAG` or `add INDEX t/NEWTAG`

INDEX will be matched if it is valid. </br>
NAME is case sensitive and must match the full name. </br>
INDEX must be a positive integer. </br>

Note that tags cannot be added to pre-installed emergency contacts.

**Examples:**

> `add John Doe t/friend`</br>
> `add 2 t/friend`

### 1.3 Adding new social media to a person

**Format:** `add INDEX sm/{SOCIAL MEDIA PLATFORM}, {ID}` or `add NAME sm/{SOCIAL MEDIA PLATFORM}, {ID}`

INDEX will be matched if it is valid. </br>
NAME is case sensitive and must match the full name. </br>
INDEX must be a positive integer. </br>

Note that social media cannot be added to pre-installed emergency contacts.

**Examples:**

> `add John Doe sm/telegram, @johndoe23`</br>
> `add 2 sm/telegram, @johndoe23`



## 2. Searching persons by keywords: `[find]`

**Format:** `[find] KEYWORD [MORE_KEYWORDS]`

Note that the command word is optional, i.e., uMessage searches for whatever entered by default, unless a command is detected.

Results are listed according to how much they match the keywords. Names are matched first, then tags, then other fields.

If the first keyword is a command word, it will still be recognised as a command and will not be treated as keywords. The whole list will be displayed when a command word is entered.

The keywords are compared to all fields of each person.</br>
Persons matching at least one keyword will be returned (i.e. OR search). e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.</br>
The search is case-insensitive. e.g `hans` will match `Hans`.</br>
The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.

**Examples:**

> `find a` will return `Alex Yeoh`, `Bernice` with the tag `colleagues`, `Lin` with the email `lin@example.com` (if these contacts exist).</br>
> `a` does the same as above.</br>
> `find alex yeoh` will return both `Alex` and `Alex Yeoh` (if both contacts exist), with `Alex Yeoh` displayed at the top.



## 3. Viewing details of a person

**Format:** `view INDEX` or click the Contact/Meeting under the list.

**Examples:**

> `view 2` </br>
>  clicking on the second contact does the same as above.



## 4. Editing details of a person: `edit`

### 4.1 Editing a person

**Format:** `edit NAME [n/NAME] [p/PHONE] [e/EMAIL] [sm/{SOCIAL MEDIA PLATFORM}, {ID}]… [t/TAG]…` or `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [sm/{SOCIAL MEDIA PLATFORM}, {ID}]… [t/TAG]…`

Note that pre-installed emergency contacts cannot be edited.

**Examples:**

> `edit 1 n/Aaron sm/Telegram, @aaron`</br>
> `edit Alex n/Aaron p/52873618`

### 4.2 Editing a person's social media

Edits a social media found in a person's list of social media.

**Format:**  `edit NAME i/SOCIAL_MEDIA_INDEX [f/] sm/NEW_DETAILS` or `edit INDEX i/SOCIAL_MEDIA_INDEX [f/] sm/NEW_DETAILS`

NAME is case sensitive and must match the full name.
INDEX refers to the index of the person you want to edit, and must be a positive integer.
SOCIAL_MEDIA_INDEX refers to the index of the social media in the person's list of social media, and must be a positive integer.
`f/` is an optional parameter, include it to edit the name of the social media instead of the social media description.

**Examples:**

> `edit 1 i/2 f/ sm/Telegram`
> `edit Alex Yeoh i/1 sm/@alexxy`


## 5. Copying: `copy`

### 5.1 Copying all details of a person

**Format:** `copy NAME`

NAME is case sensitive and must match the full name.

**Examples:**

> `copy David Li`</br>
> `copy Bernice Yu`

### 5.2 Copying one detail under the Contacts

**Format:**

Click on the relevant label values to copy the value to the system clipboard.</br>
Note that clicking the email and telegram fields will trigger a unique response.

**Examples:**

**Copying Phone:** Clicking the phone, and the phone number will be copied.

**For Email and Telegram:**

**Copying email:** Clicking the email will trigger the local Mail application to create a new email to the aforementioned address. </br>
**Copying a telegram handle:** Clicking the telegram handle will open the Telegram link to contact the person at the aforementioned handle name.



## 6. Deleting: `delete`

### 6.1 Deleting a person

**Format:** `delete NAME` or `delete INDEX`

INDEX will be matched if it is valid. </br>
NAME is case sensitive and must match the full name. </br>
INDEX must be a positive integer. </br>

Note that pre-installed emergency contacts cannot be deleted.


**Examples:**

> `delete 2` deletes the contact at index 2 </br>
> `delete Alex Yeoh` deletes the contact `Alex Yeoh` </br>
> `delete Alex` or `delete alex yeoh` will not delete `Alex Yeoh`

### 6.2 Deleting tag from a Person

**Format:** `delete NAME t/TAG` `delete INDEX t/TAG`

INDEX will be matched if it is valid. </br>
NAME is case sensitive and must match the full name. </br>
INDEX must be a positive integer. </br>

Note that tags of pre-installed emergency contacts cannot be deleted.

**Examples:**

> `delete kaaviya t/friend` </br>
> `delete 1 t/colleague`

### 6.3 Deleting social media from a Person

**Format:** `delete NAME sm/{SOCIAL MEDIA PLATFORM}, {ID}` or `delete INDEX sm/{SOCIAL MEDIA PLATFORM}, {ID}`

INDEX will be matched if it is valid. </br>
NAME is case sensitive and must match the full name.</br>
INDEX must be a positive integer. </br>

Note that social media of pre-installed emergency contacts cannot be deleted.

**Examples:**
> `delete kaaviya sm/Telegram, @uNivUS`</br>
> `delete 1 sm/Telegram, @uNivUS`


--------------------------------------------------------------------------------------------------------------------
# Managing Meetings

The meetings in the future are listed first with ascending starting time, followed by past meetings with descending starting time.

There cannot be duplicated meetings. Two meetings are the same if both have the same title (case-sensitive) and same start time.

## 1. Adding: `add`

### 1.1 Adding a meeting

**Format:** `add n/TITLE l/LINK s/START_TIME d/DURATION [t/TAG]…`

START_TIME must follow the format `yyyy-M-d HHmm` (e.g. `2022-3-20 0900`) and must not be in the past.</br>
DURATION is in minutes and must be an integer between 1 and 10000.

**Examples:**

> `add n/CS2040 PE l/https://www.google.com s/2022-3-20 0900 d/120 t/Alex`</br>
> `add n/CS2103 Tutorial l/https://www.google.com s/2022-3-20 1200 d/60`

### 1.2 Adding new tag to a meeting

**Format:** `add INDEX t/NEWTAG`

INDEX must be a positive integer.

**Examples:**

> `add 1 t/urgent`


## 2. Searching meetings by keyword: `[find]`

Finds meetings with the given keywords.

**Format:** `[find] KEYWORD [MORE_KEYWORDS]`

Results are listed according to how much they match the keywords. Titles are matched first, then tags, then other fields.</br>

If the first keyword is a command word, it will still be recognised as a command and will not be treated as keywords. The whole list will be displayed when a command word is entered.</br>

The keywords are compared to all fields of each meeting.</br>
Meetings matching at least one keyword will be returned (i.e. OR search). e.g. `CS` will return `CS2040 PE`, `CS2103 Tutorial`.</br>
The search is case-insensitive. e.g `cs` will match `CS2030`.</br>
The order of the keywords does not matter. e.g. `2040 CS` will match `CS 2040`.

**Examples:**

> `cs` will return `CS2040 PE`, `CS2103 Tutorial`, `Group meeting` with the tag `cs2103` (if these meetings exist).</br>
> `find cs` does the same as above.



## 3. Editing details of a meeting: `edit`

**Format:** `edit INDEX [n/TITLE] [l/LINK] [s/START_TIME] [d/DURATION] [t/TAG]…`

START_TIME must follow the format `yyyy-M-d HHmm` (e.g. `2022-3-20 0900`) and must not be in the past.</br>
DURATION is in minutes and must be an integer between 1 and 10000 (inclusive).

**Examples:**

> `edit 1 n/CS2030 PE` </br>
> `edit 2 s/2022-4-1 1600`



## 4. Deleting: `delete`

### 4.1 Deleting a meeting

**Format:** `delete INDEX`
INDEX must be a positive integer.

**Example:**
> `delete 2` deletes the meeting at index 2

### 4.2 Deleting tag from a meeting

**Format:** `delete INDEX t/TAG`

Deletes tag `TAG` from a meeting specified using `TITLE` or `INDEX`.

**Example:**
> `delete 1 t/urgent`

--------------------------------------------------------------------------------------------------------------------

# Managing Global Tags

Global tags helps you organise relevant contacts and meetings together.

## 1. Adding a global tag

**Format:** `add t/TAG`

**Example:**
> `add t/CS2100`

## 2. Deleting a global tag

**Format:** `delete t/TAG`

**Example:**
> `delete t/CS2100`

## 3. Filter by tag

**Format:** click on a tag in the tag panel. This filters both contacts and meetings with the tag.



--------------------------------------------------------------------------------------------------------------------
# Universal Features

## 1. Viewing help: `help`

Displays the `Help` tab.

## 2. Listing all persons/meetings: `list`

Shows a list of all persons/meetings in the book.

## 3. Clearing all entries: `clear`

Clears all non-Emergency Contacts entries and meetings from the address book.

## 4. Exiting the program: `exit`

Exits the program.

## 5. Saving the data

uMessage's data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

## 6. Editing the data file

uMessage's data are saved as two JSON files: `[JAR file location]/data/addressbook.json` and `[JAR file location]/data/meetingsbook.json`. Advanced users are welcome to update data directly by editing those data files.




--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous uMessage home folder.
