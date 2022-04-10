---
layout: page
title: zihaowrez's Project Portfolio Page
---

### Project: *uMessage*

*uMessage* - A desktop app that helps university students manage contacts, academics and CCAs that communicate and hold meetings on various platforms. It is optimized for keyboard users.

Given below are my contributions to the project.

* **New Feature: Search optimisation**
  * What it does: When searching, the person / meeting that matches the most number of keywords will be placed first. Names / titles are matched first, followed by tags and other fields.
  * Justification: This feature improves the product by listing the most relevant information to the user when searching.
  * Highlights: The feature is implemented using a Comparator. This enhancement required an in-depth analysis of design alternatives, as the displayed list needs to be sorted as well. This is achieved by wrapping the filtered list into a sorted list which takes the comparator.

* **New Feature: Display contacts in alphabetical order**
  * Justification: This feature helps the user locate the index of contacts easily, which makes edition and deletion faster.
  * Highlights: The implementation makes use of the sorted list defined in the previous feature. A comparator for alphabetical list is put to the sorted list when executing a `ListCommand`.

* **New Feature: Use tabs to replace the menu bar**
  * What it does: Arrange the contents of the app into three Windows: Contacts, Meetings, and Help.
  * Justification: This feature enables the user to navigate different contents in the app easily (by clicking on the tab).
  * Highlights: This enhancement required substantial changes in the GUI. In order for different windows to work independently, they need to be implemented separately, with different sets of commands.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=zihaowrez&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=zihaowrez&tabRepo=AY2122S2-CS2103-W16-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Proposed how to evolve AB3 and came up with a work division for the team
  * Handled communication with the tutor / grader about project deliverables

* **Enhancements to existing features**:
  * Improved the display of GUI (PR [\#237](https://github.com/AY2122S2-CS2103-W16-2/tp/pull/237))
  * Wrote tests for `model.meeting` package and search optimisation comparators (PR [\#222](https://github.com/AY2122S2-CS2103-W16-2/tp/pull/222))

* **Documentation**:
  * User Guide:
    * Updated documentation for all features in v1.3 (PR [\#144](https://github.com/AY2122S2-CS2103-W16-2/tp/pull/144))
  * Developer Guide:
    * Added implementation details of the GUI (PR [\#243](https://github.com/AY2122S2-CS2103-W16-2/tp/pull/243)).
    * Added implementation details about updating the `ResultDisplay` when contacts or tags are clicked (PR [\#247](https://github.com/AY2122S2-CS2103-W16-2/tp/pull/247)).
    * Updated use cases for `add`, `delete`, and `find` (PR [\#124](https://github.com/AY2122S2-CS2103-W16-2/tp/pull/124))

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#232](https://github.com/AY2122S2-CS2103-W16-2/tp/pull/232), [\#223](https://github.com/AY2122S2-CS2103-W16-2/tp/pull/223), [\#125](https://github.com/AY2122S2-CS2103-W16-2/tp/pull/125), [\#77](https://github.com/AY2122S2-CS2103-W16-2/tp/pull/77)
  * Contributed to forum discussions: [256](https://github.com/nus-cs2103-AY2122S2/forum/issues/256)
  * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2122S2-CS2103T-T09-1/tp/issues/238), [2](https://github.com/AY2122S2-CS2103T-T09-1/tp/issues/221))


