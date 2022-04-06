---
layout: page
title: zihaowrez's Project Portfolio Page
---

### Project: *uMessage*

*uMessage* - A desktop app that helps university students manage contacts, academics and CCAs that communicate and hold meetings on various platforms. It is optimized for keyboard users.

Given below are my contributions to the project.

* **New Feature: Search optimisation**
  * What it does: When searching, the person/meeting that matches the most number of keywords will be placed first. Names and titles are matched first, followed by tags and other fields
  * Justification: This feature improves the product by listing the most relevant information to the user when searching.
  * Highlights: The feature is implemented using a Comparator. This enhancement required an in-depth analysis of design alternatives, as the displayed list needs to be sorted as well. This is achieved by wrapping the filtered list into a sorted list which takes the comparator.

* **New Feature: Display contacts in alphabetical order**
  * Justification: This feature helps the user locate the index of contacts easily, which makes edition and deletion faster.
  * Highlights: The implementation makes use of the sorted list defined in the previous feature. A comparator for alphabetical list is put to the sorted list when executing `ListCommand`. Therefore, whenever the whole list is displayed, it is sorted alphabetically.

* **New Feature: Use tabs to replace the menu bar**
  * What it does: Arrange the contents of the app into three Windows: Contacts, Meetings, and Help.
  * Justification: This feature enables the user to navigate different contents in the app easily (by clicking on the tab).
  * Highlights: This enhancement required substantial changes in the GUI. In order for different windows to work independently, they need to be implemented separately, with different sets of commands.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2022-02-18&tabOpen=true&tabType=authorship&tabAuthor=zihaowrez&tabRepo=AY2122S2-CS2103-W16-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
  * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
  * User Guide:
    * Added documentation for the features `delete` and `find` [\#72]()
    * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
  * Developer Guide:
    * Added implementation details of the `delete` feature.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
  * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
  * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
  * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
  * Integrated a third party library (Natty) to the project ([\#42]())
  * Integrated a new Github plugin (CircleCI) to the team repo

* _{you can add/remove categories in the list above}_
