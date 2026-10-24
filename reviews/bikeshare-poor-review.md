1. Files read: `bikeshare-poor/src/main/java/bikeshare/Main.java`.

## Check 1. Code quality

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---:|---|---|---|---|---|
| 1 | Code quality | `Main.java:37` | An empty input line can produce an empty command token, and the code then accesses `input[0]`; malformed or end-of-input input can terminate the program unexpectedly. | Should fix | Check for empty input and handle end-of-input before reading command tokens. |
| 2 | Code quality | `Main.java:52–54` | The return command parses minutes but does not reject zero or negative values, allowing an invalid fee to be calculated. | Should fix | Validate that minutes is a positive integer before processing the return. |
| 3 | Code quality | `Main.java:95` | The fee calculation assumes any stored type other than `1` is staff, so an invalid integer code can silently use the staff rate. | Should fix | Represent member type with an enum or validate the code before calculating the fee. |

## Check 2. Cohesion, coupling and connascence

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
|---:|---|---|---|---|---|
| 4 | Cohesion | `Main.java:66–101` | `handle` has logical cohesion: a boolean flag selects between two different workflows, renting and returning a bike. | Should fix | Split the method into separate `rent` and `returnBike` operations. |
| 5 | Coupling / encapsulation | `Main.java:12–13` | The `bikes` and `activeRides` collections are public static fields, so other code can directly modify shared application state. | Must fix | Make the fields private and expose operations that preserve the state rules. |
| 6 | Class cohesion | `Main.java:11–115` | `Main` handles console interaction, command parsing, rental rules, fee calculation, and CSV persistence, so it represents several responsibilities. | Should fix | Separate the command-line interface, bike-share rules, and ride persistence into dedicated classes. |
| 7 | Connascence | `Main.java:45–46, 95` | The meaning of integer member-type codes is shared by input parsing and fee calculation; both locations must follow the same numeric convention. This is connascence of convention. | Should fix | Replace integer codes with a `MemberType` enum that owns the fee rate. |

Number of findings: Check 1 — 3; Check 2 — 4.
