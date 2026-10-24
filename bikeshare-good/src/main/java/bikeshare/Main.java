
package bikeshare;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BikeShareService service =
                new BikeShareService(new CsvRideRepository("rides.csv"));

        System.out.println("BikeShare: rent <id> <student/staff> <station>");
        System.out.println("Return: return <id> <station> <minutes>");
        System.out.println("Type quit to exit.");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String line = scanner.nextLine().trim();

                if (line.equalsIgnoreCase("quit")) {
                    break;
                }

                try {
                    String[] parts = line.split("\\s+");

                    if (parts.length == 4 &&
                            parts[0].equalsIgnoreCase("rent")) {
                        Bike bike = service.rent(
                                parts[1],
                                MemberType.fromInput(parts[2]),
                                Station.fromInput(parts[3]));

                        System.out.println(parts[1] + " rented bike "
                                + bike.getId() + " at "
                                + Station.fromInput(parts[3]).getLabel());

                    } else if (parts.length == 4 &&
                            parts[0].equalsIgnoreCase("return")) {
                        Ride ride = service.returnBike(
                                parts[1],
                                Station.fromInput(parts[2]),
                                Integer.parseInt(parts[3]));

                        System.out.println("Receipt: Bike "
                                + ride.getBikeId()
                                + ", Minutes " + ride.getMinutes()
                                + ", Fee " + ride.getFee() + " baht");
                    } else {
                        System.out.println("Invalid command. Try again.");
                    }
                } catch (IllegalArgumentException | IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        System.out.println("Goodbye!");
    }
}
