# Prompt A — AI Review of the Starter Code

- **Date:** 2026-10-24
- **AI tool and exact model:** ChatGPT, GPT-6
- **Files read:** `Main.java`, `Parcel.java`, `Locker.java`, `ParcelService.java`
- **How the AI read the files:** The project ZIP was attached to the chat and the Java source files were read from it.
- **Commit reviewed:** `196382e` (`Starter code`), as shown in the ZIP's Git history.

## Findings

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---:|---|---|---|---|---|
| 1 | Check 1 — Code quality | `Main.java:8` | The `Scanner` wrapping `System.in` is never closed. | Should fix | Close the scanner when command processing ends, taking care to do so only after input is no longer needed. |
| 2 | Check 1 — Code quality | `Main.java:11-17` | The command parser does not validate that a command is non-empty or has the required number of arguments, so malformed input can be ignored or cause an exception. | Must fix | Validate the command and its argument count before accessing array elements or parsing numbers. |
| 3 | Check 1 — Code quality | `Main.java:9,13`; `ParcelService.java:56-61` | The stated parcel size range of 1–3 is not validated; an invalid size can be accepted, and `fee` treats any size other than 1 or 2 as the large size. | Must fix | Reject sizes outside 1–3 before creating or storing a parcel. |
| 4 | Check 1 — Code quality | `ParcelService.java:6` | The import of `java.util.Date` is unused. | Note | Remove the unused import. |
| 5 | Check 1 — Code quality | `ParcelService.java:12` | The private field `maxParcels` is never used, so it does not enforce a parcel limit. | Should fix | Remove the field or use it to enforce a clearly defined parcel limit. |
| 6 | Check 1 — Code quality | `ParcelService.java:42` | The parcel code is a `String` but is compared with `==`, which compares object references rather than string contents and can cause a pickup not to find a matching code. | Must fix | Compare the codes with `equals()` after handling any possible `null` value. |
| 7 | Check 1 — Code quality | `ParcelService.java:76-79` | The `FileWriter` is not closed, so buffered data may not be flushed reliably and the resource can remain open. | Must fix | Use try-with-resources for the `FileWriter`. |
| 8 | Check 1 — Code quality | `ParcelService.java:80-81` | The `IOException` catch block is empty, hiding file-saving failures from the user or caller. | Must fix | Handle the exception meaningfully, such as by reporting the failure or propagating it to the caller. |
| 9 | Check 2 — Cohesion | `Main.java:6-26` | `main` has logical cohesion: it groups different command actions and selects among them according to the command entered. | Should fix | Move each command's handling into a separate method or command handler. |
| 10 | Check 2 — Cohesion | `ParcelService.java:24-37` | `storeParcel` has procedural cohesion: it performs an ordered workflow of assigning a locker, changing status, printing a notification, and saving the file. | Should fix | Keep locker assignment and parcel-state changes in the service, and move notification and persistence responsibilities behind separate collaborators. |
| 11 | Check 2 — Cohesion | `ParcelService.java:39-50` | `pickUp` has procedural cohesion: it searches for a parcel, changes its state, saves data, and calculates the fee in sequence. | Should fix | Separate parcel retrieval/state changes from fee calculation and persistence. |
| 12 | Check 2 — Cohesion | `ParcelService.java:64-72` | `printParcels` has logical cohesion: the `forStaff` flag selects between two different output behaviours. | Should fix | Split staff and non-staff printing into separate methods, or pass a dedicated output policy. |
| 13 | Check 2 — Class cohesion | `ParcelService.java:9-82` | `ParcelService` handles several responsibilities: locker allocation, parcel state changes, fee calculation, console/SMS-style output, and CSV persistence, indicating weak class cohesion. | Should fix | Separate persistence and output responsibilities from parcel and locker business logic. |
| 14 | Check 2 — Coupling | `Main.java:19,21`; `ParcelService.java:64-72` | `Main` uses the boolean argument to control which behaviour `printParcels` performs; this is control coupling. | Should fix | Replace the boolean-controlled behaviour with separate methods or an explicit strategy. |
| 15 | Check 2 — Coupling | `Parcel.java:7`; `ParcelService.java:28,44,67-68` | `ParcelService` reads and writes `Parcel.status` directly, coupling it to the parcel's exposed representation; under the requested categories, this is content-style coupling. | Should fix | Make `status` private and expose state-changing/query methods on `Parcel`. |
| 16 | Check 2 — Coupling | `Main.java:22`; `ParcelService.java:20-22` | `Main` reaches through `service.getLockers()` to operate on returned `Locker` objects, creating a message chain and stronger coupling between `Main`, `ParcelService`, and `Locker`. | Should fix | Add a focused method to `ParcelService` that returns the free-locker count. |
| 17 | Check 2 — Encapsulation | `Parcel.java:7` | The `status` field is public rather than private. | Should fix | Make the field private and expose only the operations needed to read or change its state. |
| 18 | Check 2 — Encapsulation | `ParcelService.java:20-22` | `getLockers()` returns the internal mutable list, allowing callers to change the service's locker collection. | Must fix | Return an unmodifiable view or a defensive copy, or expose only the locker information callers need. |
| 19 | Check 2 — Connascence | `Parcel.java:13`; `ParcelService.java:28,44,68` | Parcel state is represented by string conventions such as `"NEW"`, `"STORED"`, and `"PICKED_UP"` across classes; a spelling change in one place can break behaviour. This is connascence of convention. | Should fix | Represent parcel states with an enum shared by the relevant classes. |
| 20 | Check 2 — Connascence | `Parcel.java:6`; `Main.java:9,13`; `ParcelService.java:56-61` | The meanings of numeric size values 1, 2, and 3 are shared by comments, input handling, and fee logic. This is connascence of convention. | Should fix | Define named constants or a `ParcelSize` enum and use it consistently. |

## Number of findings for each check

- **Check 1 — Code quality:** 8 findings.
- **Check 2 — Cohesion, coupling and connascence:** 12 findings.
- **Methods too long or too complex to read easily:** not found in the four files reviewed.

## Review notes

- These are AI review findings, not SonarQube findings. Compare each one against the actual SonarQube results before filling in Table 2.
- The cohesion classifications are design judgements based on the code and should be checked against the course definitions.
- No code was changed as part of this review.
