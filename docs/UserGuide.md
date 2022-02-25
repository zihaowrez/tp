# uMessage User Guide

*uMessage* is an app that helps university students manage contacts, academics and CCAs that communicate and hold meetings on various platforms. It is optimized for keyboard users.

## Feature List (v1.2)

### Add Contact: `add`

Add communication channels under contact (e.g. telegram, slack)

Adding a person: add

**Format:** `add n/NAME \[d/{Social Media Platform} {ID} d/{Social Media Platform} {ID}\] \[t/cs2103\]...`

Tip: A person can have one or more details
Tip: A person can have zero or more tags

**Examples:**

`add n/John Doe d/Telegram kaaviya`  
`add n/Betsy Crowe d/Phone 99900099 d/Email e0123456@gmail.com`  
`add n/Potter d/Phone 49502583 t/cs2012`  

### Delete Contact: `delete`
Delete communication channels/information under contact
Deletes the specified person from the address book.

**Format:**  
`delete NAME`			(in event of single occurrence of NAME)  
`delete NAME` {index}	(otherwise)

Deletes the person with the specified NAME.  
NAME is insensitive (like find)  
In the case of multiple occurrences, the user is prompted to specify which occurrence of the name is to be deleted with the index as shown in the displayed person list when the name is searched.  
The index must be a positive integer 1, 2, 3, ...

**Examples:**

Scenario 1 (only one occurrence of the  name): `delete kaaviya`  
Scenario 2 (multiple occurrences):   
`delete kaaviya`   (ERROR: multiple occurrences detected)  
`delete kaaviya 2`  

### View: `home`

Returns the user back to the original contact list

Returns to the main list of all contacts displayed in alphabetical order by default.

### Find Contact: `find` OR `[KEYWORD]`

Finds persons whose name contain any of the given keywords.

**Format:**  `find` KEYWORD \[MORE_KEYWORDS\]

    KEYWORD \[MORE_KEYWORDS\]

The search is case-insensitive. e.g `hans` will match `Hans`  
The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`  
Only the name is searched.  
Only full words will be matched e.g. `Han` will not match `Hans`  
Persons matching at least one keyword will be returned (i.e. OR search). e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`  
The search will attempt to search all fields and tags to return a match.   

**Examples:**

`find kaaviya` (finds the name kaaviya)  
`Kaaviya` (does the same as above)

