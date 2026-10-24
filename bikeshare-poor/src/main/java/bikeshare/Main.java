
package bikeshare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Main {
    public static Map<String, List<String>> bikes = new LinkedHashMap<>();
    public static Map<String, Ride> activeRides = new HashMap<>();

    static class Ride {
        String bike;
        int type;

        Ride(String bike, int type) {
            this.bike = bike;
            this.type = type;
        }
    }

    public static void main(String[] args) {
        bikes.put("Library", new ArrayList<>(List.of("L1", "L2")));
        bikes.put("Canteen", new ArrayList<>(List.of("C1", "C2")));
        bikes.put("Dorm", new ArrayList<>(List.of("D1", "D2")));

        Scanner sc = new Scanner(System.in);
        System.out.println("BikeShare: rent <id> <student/staff> <station>");
        System.out.println("Return: return <id> <station> <minutes>");
        System.out.println("Type quit to exit.");

        while (true) {
            System.out.print("> ");
            String[] input = sc.nextLine().trim().split("\\s+");

            if (input[0].equalsIgnoreCase("quit")) {
                break;
            }

            try {
                if (input[0].equalsIgnoreCase("rent") && input.length == 4) {
                    int type = input[2].equalsIgnoreCase("student") ? 1
                            : input[2].equalsIgnoreCase("staff") ? 2 : 0;
                    if (type == 0) {
                        System.out.println("Invalid member type.");
                        continue;
                    }
                    handle(true, input[1], type, input[3], 0);
                } else if (input[0].equalsIgnoreCase("return") && input.length == 4) {
                    handle(false, input[1], 0, input[2],
                            Integer.parseInt(input[3]));
                } else {
                    System.out.println("Invalid command.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Minutes must be a number.");
            }
        }

        sc.close();
    }

    public static void handle(boolean renting, String memberId,
                              int type, String station, int minutes) {
        if (!bikes.containsKey(station)) {
            System.out.println("Invalid station.");
            return;
        }

        if (renting) {
            if (activeRides.containsKey(memberId)) {
                System.out.println("Member already has a bike.");
                return;
            }
            if (bikes.get(station).isEmpty()) {
                System.out.println("No bikes available.");
                return;
            }

            String bike = bikes.get(station).remove(0);
            activeRides.put(memberId, new Ride(bike, type));
            System.out.println(memberId + " rented bike " + bike
                    + " at " + station);
        } else {
            Ride ride = activeRides.remove(memberId);
            if (ride == null) {
                System.out.println("No active ride found.");
                return;
            }

            bikes.get(station).add(ride.bike);
            int fee = minutes * (ride.type == 1 ? 2 : 3);

            System.out.println("Receipt: Bike " + ride.bike
                    + ", Minutes " + minutes + ", Fee " + fee + " baht");

            saveRide(memberId, ride.bike, minutes, fee, station);
        }
    }

    public static void saveRide(String memberId, String bike,
                                int minutes, int fee, String station) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Path.of("rides.csv"),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write(memberId + "," + bike + "," + minutes + ","
                    + fee + "," + station);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Could not save ride: " + e.getMessage());
        }
    }
}
