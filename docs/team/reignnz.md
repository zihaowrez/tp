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
- Added the `Meeting` class which contains a Meeting Name, Link, StartTime and Duration
- The above properties are encapsulated by the `Title`, `Link`, `StartTime` and `Duration` classes respectively
- Updated Storage to include Meetings by adding: `JsonAdaptedMeeting`, `JsonMeetingsBookStorage` and `JsonSerializableMeetingsBook`

2. Implemented adding of meetings
- Added `AddMeetingCommand` and `AddMeetingCommandParser`

3. Implemented deleting of meetings
- Added `DeleteMeetingCommand` and `DeleteMeetingCommandParser`

4. Implemented editing of meetings
- Added `EditMeetingCommand` and `EditMeetingCommandParser`

5. Implemented finding of meetings
- Added `FindMeetingCommand` and `FindMeetingCommandParser`


**Enhancements implemented**:
1. Implemented functionality for Dynamic User Input
- Added a listener into the `CommandBox` class
- Refactored `AddressBookParser` to detect dynamically inputted commands vs 
user entered commands


**Contributions to the UG**:
1. Fixed errors related to the user guide found after PE-D

**Contributions to the DG**:
1. Added a PUML Diagram for Dynamic User Input 

**Review/mentoring contributions**:
*{Coming Soon}*

**Contributions beyond the project team**
*{Coming Soon}*
