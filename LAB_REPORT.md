# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/finsrps/itcs382-lab.git

## Tools

| Item | Version or name |
| --- | --- |
| JDK | OpenJDK 21.0.13 |
| Maven | Apache Maven 3.10.0 |
| IDE | Visual Studio Code |
| SonarQube for IDE | SonarQube for IDE 6.2.0 |
| AI tool and exact model | |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change:

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S2143 | `ParcelService.java:1` | Use the "java.time" API for date and time. |
| 2 | java:S1128 | `ParcelService.java:6` | Remove this unused import 'java.util.Date'. |
| 3 | java:S1068 | `ParcelService.java:12` | Remove this unused "maxParcels" private field. |
| 4 | java:S106 | `ParcelService.java:30` | Replace this use of System.out by a logger. |
| 5 | java:S4973 | `ParcelService.java:42` | Strings and Boxed types should be compared using "equals()". |
| 6 | java:S106 | `ParcelService.java:67` | Replace this use of System.out by a logger. |
| 7 | java:S106 | `ParcelService.java:69` | Replace this use of System.out by a logger. |
| 8 | java:S2095 | `ParcelService.java:76` | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 9 | java:S108 | `ParcelService.java:80` | Remove this block of code, fill it in, or add a comment explaining why it is empty. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 2026-10-24 |
| AI tool and exact model | ChatGPT, GPT-6 |
| Commit reviewed | 196382e (Starter code) |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | The project ZIP was attached to the chat and the Java source files were read from it. |
| Output file in the reviews folder | reviews/prompt-a-starter-review.md |

Table 2. All findings on the starter code


| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | ParcelService.java:6 | Unused import `java.util.Date`. | Both | java:S1128 | Yes — the import is unused. |
| 2 | ParcelService.java:12 | Unused field `maxParcels`. | Both | java:S1068 | Yes — the field is not used. |
| 3 | ParcelService.java:30,67,69 | Use of `System.out` instead of a logger. | SonarQube | java:S106 | Yes — output is printed directly. |
| 4 | ParcelService.java:42 | String codes compared using `==`. | Both | java:S4973 | Yes — `==` compares references. |
| 5 | ParcelService.java:76-79 | `FileWriter` is not closed. | Both | java:S2095 | Yes — the resource should be closed. |
| 6 | ParcelService.java:80-81 | Empty exception-handling block. | Both | java:S108 | Yes — the error is silently ignored. |
| 7 | Main.java:8 | Scanner is not explicitly closed. | AI | Resource management | Yes — no explicit close is shown. |
| 8 | Main.java:11-17 | Command arguments are not validated. | AI | Input validation | Yes — malformed input may cause errors. |
| 9 | Main.java:9,13; ParcelService.java:56-61 | Parcel size range is not validated. | AI | Input validation | Yes — invalid sizes may be accepted. |
| 10 | ParcelService.java:9-82 | Class has several responsibilities. | AI | Class cohesion | Yes — it handles business logic, output and file saving. |
| 11 | ParcelService.java:64-72 | Boolean flag selects output behaviour. | AI | Logical cohesion; control coupling | Yes — the flag changes the method's behaviour. |
| 12 | ParcelService.java:20-22 | Method returns the internal mutable locker list. | AI | Encapsulation | Yes — callers may modify the collection. |


2.1 Name two problems that only the AI review found.
- The AI found that the command input is not properly validated and that ParcelService has too many responsibilities.
2.2 Name one problem that only SonarQube found, or write none.
- SonarQube reported the use of System.out instead of a logger.
2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.
- Main.java has logical cohesion. This is a design judgement, so it depends on how the course defines cohesion and should be checked against the code.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1


| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Fixed | Removed the legacy Date usage. |
| 2 | Fixed | Removed the unused Date import. |
| 3 | Fixed | Removed the unused maxParcels field. |
| 4 | Kept | System.out is still used for console output. |
| 5 | Fixed | Changed == to equals() when comparing parcel codes. |
| 6 | Kept | System.out is still used for console output. |
| 7 | Kept | System.out is still used for console output. |
| 8 | Fixed | Used try-with-resources to close the FileWriter. |
| 9 | Fixed | Added an error message when saving the file fails. |


Number of SonarQube issues that remain after your fixes:
3

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Resource management | Used try-with-resources to close the FileWriter. | committed |
|String comparison | Changed == to equals() when comparing parcel codes. | committed|

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text
store P1 6501 2
SMS to 6501: parcel P1 is in locker 2
Locker 2
pickup P1 5
Fee: 30.0
staff
P1 6501 PICKED_UP
Free lockers: 3
quit

Content of parcels.csv:

P1,PICKED_UP

```
## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 2026-10-25 | 2026-10-25 |
| AI tool and exact model | ChatGPT, GPT-6 | ChatGPT, GPT-6 |
| Prompt (B or C as given, or your changed prompt in full) | Prompt B, as given in prompts.md | Prompt C, as given in prompts.md |
| Builds with mvn package (yes or no) | Yes | Yes |
| Number of classes, interfaces, enums and records | 2 | 8 |
| SonarQube issues, total | 19 | 9 |
| SonarQube issues, by rule | See SonarQube findings | java:S106: 9 |
| AI review findings in Check 1 | See reviews/bikeshare-poor-review.md | See reviews/bikeshare-good-review.md |
| AI review findings in Check 2 | See reviews/bikeshare-poor-review.md | See reviews/bikeshare-good-review.md |
| Review output file in the reviews folder | reviews/bikeshare-poor-review.md | reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.
- No. I could not identify a cohesion or coupling issue in the SonarQube findings.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

-Yes. The AI review distinguished the two versions. The poor design uses public static shared data and combines multiple responsibilities in one class, while the good design separates responsibilities into different classes.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

-I would rather extend bikeshare-good because its classes have separate responsibilities, making it easier to add a new member type and its fee rule without changing unrelated parts of the program.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.
- SonarQube checks code issues such as high complexity and System.out usage, but it cannot fully judge design quality.

5.2 When should you not trust the AI review? Give an example from this lab.
- AI reviews can be wrong or vague, so I should verify findings against the actual code and line numbers.
5.3 What will you do differently in Project Phase 2 after this lab?
- I will design classes with high cohesion and low coupling, and verify AI and SonarQube findings before making changes.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| ChatGPT, GPT-6 | Review code quality and design quality (Task 2) | AI review of the parcelpoint project | Checked findings against the source code |
| ChatGPT, GPT-6 | Generate and compare poor and good designs (Task 4) | bikeshare-poor and bikeshare-good projects | Ran mvn package and tested both programs |
| Gemini, 3.1 Pro | Review the two BikeShare designs (Task 4) | Review output files in the reviews folder | Compared findings with the code and SonarQube results |