# uMessage (v1.2) User Guide

***uMessage*** is a desktop app that helps university students manage contacts, academics and CCAs that communicate and hold meetings on various platforms. It is optimized for keyboard users.

--------------------------------------------------------------------------------------------------------------------

# Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `uMessage.jar` from

1. Copy the file to the folder you want to use as the _home folder_ for *uMessage*.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`**`n/Betsy Crowe d/Phone, 99900099 d/Email, e0123456@gmail.com`: Adds a contact named `Betsy Crowe` to the Address Book.

   * `alex` or **`find`**`alex` : Finds the name "Alex" in the list

   * **`delete`**`alex` : Deletes Alex if the person exists.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

# Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

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

</div>

## 1. Viewing help: `help`

Displays a new window showing `UserGuide.md` as the help page.  

**Format:** `help` or click "Help" on the menu bar.


## 2. Adding: `add`

Adds either:
  1. a person, or
  2. a new tag to an existing person.

### 2.1 Adding a person

**Format:** `add n/NAME [d/{SOCIAL MEDIA PLATFORM}, {ID}]… [t/TAG]…`

A person can have one or more details and zero or more tags.  
The newly added person will be placed at the top of the contact list.

**Examples:**

> `add n/John Doe d/Telegram, johndoe`
> `add n/Betsy Crowe d/Phone, 99900099 d/Email, e0123456@gmail.com`
> `add n/Potter d/Phone, 49502583 t/cs2030`

### 2.2 Adding new tag to a person
**Format:** `add NAME t/NEWTAG` or `add INDEX t/NEWTAG`

_Tip: Specify the person that you want to add the tag to by using their full name or their index in the contact list_

**Examples:**

> `add John Doe t/friend`
> `add 2 t/friend`

## 3. Copying the details of a person: `copy`

**Format:** `copy NAME`

Copies the details of the person with the specified NAME.
Copy by name is case sensitive and must match the full name.

**Examples:**

> `copy David Li`
> `copy Bernice Yu`

## 4. Listing all persons: `list`

Shows a list of all persons in the address book.

**Format:** `list`

## 5. Find by keyword: `[find]`

Finds persons with the given keywords.

**Format:** `[find] KEYWORD [MORE_KEYWORDS]`

If the first keyword is a command word, it will still be recognised as a command and will not be treated as keywords. The whole list will be displayed when a command word is entered.

The keywords are compared to all fields of each person.  
Persons matching at least one keyword will be returned (i.e. OR search). e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.  
The search is case-insensitive. e.g `hans` will match `Hans`.  
The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.  

**Examples:**

> `find a` will return `Alex Yeoh`, `Bernice` with the tag `colleagues`, `Lin` with the email `lin@example.com` (if these contacts exist).  
> `a` does the same as above.  
> `find alex yeoh` will return both `Alex` and `Alex Yeoh` (if both contacts exist).  

## 6. Deleting: `delete`

Deletes either:
  1. a person, or
  2. a tag from an existing person.

### 6.1 Deleting Person

**Format:** `delete NAME` or `delete INDEX`

Deletes the person with the specified `NAME` or `INDEX`.  
Deletion by name is case sensitive and must match the full name.  
INDEX must be a positive integer.

**Examples:**
> `delete 2` deletes the contact at index 2  
> `delete Alex Yeoh` deletes the contact `Alex Yeoh`  
> `delete Alex` or `delete alex yeoh` will not delete `Alex Yeoh`  

### 6.2 Deleting Tag from a Person 
**Format:** `delete NAME t/TAG` `delete INDEX t/TAG`

Deletes tag `TAG` from a person specified using `NAME` or `INDEX`.  

**Examples:**
> `delete kaaviya t/friend`
> `delete 1 t/colleague`

## 7. Viewing a person: `view`

**Format:** `view INDEX`

Views the contact details of the person with the selected index in the RHS window.

**Examples:**

> `view 2`

## 8. Clearing all entries: `clear`

Clears all entries from the address book.

## 9. Exiting the program: `exit`

Exits the program.

## 10. Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

## 11. Editing the data file

AddressBook data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action     | Format, Examples                                                                                                                      |
|------------|---------------------------------------------------------------------------------------------------------------------------------------|
| **Add Person**    | `add n/NAME [d/{SOCIAL MEDIA PLATFORM}, {ID}]… [t/TAG]…​`<br> e.g., `add n/Betsy Crowe d/Phone, 99900099 d/Email, e0123456@gmail.com` |
| **Add Tag**    | `add NAME t/NEWTAG` or `add INDEX t/NEWTAG` <br> e.g., `add Betsy t/Friend`, `add 2 t/Friend` |
| **Clear**  | `clear` 
| **Copy Details of a Person** | `copy FULL_NAME`<br> e.g., `copy Alex Yeoh`  |
| **Delete Person** | `delete FULL_NAME` or `delete INDEX`<br> e.g., `delete Alex Yeoh`, `delete 2`                                                         |
| **Find**   | `[find] KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`, `James Jake`                                                            |
| **List**   | `list`                                                                                                                                |
| **Help**   | `help`                                                                                                                                |
| **View**   | `view INDEX`<br> e.g. `view 1`                                                                                                           |
