---
layout: page
title: Kenneth Chow's Project Portfolio Page
---

### Project: uMessage

**Overview**

uMessage - Level 3 is a desktop address book application. The user interacts with it using a CLI,
and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.


**Summary of Contributions**

Given below are my contributions to the project.

**Code Contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=reignnz&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18)

**Features implemented**: 
1. Implemented framework for Meetings 
   - Overview: Added the `Meeting` class which contains a Meeting Name, Link, StartTime and Duration. The above properties are encapsulated by the `Title`, `Link`, `StartTime` and `Duration` classes respectively. Updated Storage to include Meetings by adding: `JsonAdaptedMeeting`, `JsonMeetingsBookStorage` and `JsonSerializableMeetingsBook`

   - What it does: Builds the foundation for the meetings tab. To subsequently support features 2-5 below. 
   - Justification: Necessary so that when a user adds a meeting, the relevant information will be parsed and stored
   correctly
   - Highlights: This feature requires understanding of the flow of logic, starting from when the user inputs the
   command to the creation and storage of meetings. 



2. Implemented adding of meetings 
   - Overview: Added `AddMeetingCommand` and `AddMeetingCommandParser`
   - What it does: Allow the user to add meeting into the MeetingsTab
   - Justification: Necessary feature for MeetingsTab
   - Highlights: This feature requires understanding of the ArgumentTokenizer 
     and the flow of logic between the execution of the parser and command.


3. Implemented deleting of meetings
   - Overview: Added `DeleteMeetingCommand` and `DeleteMeetingCommandParser`
   - What it does: Allow the user to delete meeting into the MeetingsTab
   - Justification: Necessary feature for MeetingsTab
   - Highlights: Same as feature 2
   

4. Implemented editing of meetings
   - Overview: Added `EditMeetingCommand` and `EditMeetingCommandParser`
   - What it does: Allows the user to edit meetings in the MeetingsTab
   - Justification: User can change the details of a meeting in the MeetingsTab
     without the trouble of needing to delete that meeting and adding it again. 
   - Highlights: Same as feature 2
   

5. Implemented finding of meetings
   - Overview: Added `FindMeetingCommand` and `FindMeetingCommandParser`
   - What it does: Allow the user to find meetings in the Meetings Tab
   - Justification: Users can efficiently locate the desired meeting
   - Highlights: Same as feature 2

**Enhancements implemented**:
1. Implemented functionality for Dynamic User Input
   - Overview: Added a listener into the `CommandBox` class. Refactored `AddressBookParser` to detect dynamically inputted commands vs 
     user entered commands
   - What it does: Allow users to search for contacts/meetings as they input a character into the command line
   - Justification: Improves the efficiency of searching for a contact/meeting without the need of typing and entering
     the command `find PERSON/MEETING`
   - Highlights: This enhancement requires the understanding of JavaFx elements and how to add different listeners to 
     the TextField 


**Contributions to the UG**:
1. Fixed errors related to the user guide found after PE-D

**Contributions to the DG**:
1. Added Implementation for Dynamic User Input
- Created a PUML Diagram 
