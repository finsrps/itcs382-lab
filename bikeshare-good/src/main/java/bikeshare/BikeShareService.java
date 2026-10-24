
package bikeshare;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BikeShareService {
    private final Map<Station, Deque<Bike>> availableBikes =
            new EnumMap<>(Station.class);
    private final Map<String, Rental> activeRentals = new HashMap<>();
    private final RideRepository repository;

    public BikeShareService(RideRepository repository) {
        this.repository = repository;

        for (Station station : Station.values()) {
            Deque<Bike> bikes = new ArrayDeque<>();
            bikes.add(new Bike(station.getPrefix() + "1"));
            bikes.add(new Bike(station.getPrefix() + "2"));
            availableBikes.put(station, bikes);
        }
    }

    public Bike rent(String memberId, MemberType memberType,
                     Station station) {
        if (activeRentals.containsKey(memberId)) {
            throw new IllegalArgumentException(
                    "This member already has a bike.");
        }

        Deque<Bike> bikes = availableBikes.get(station);
        if (bikes.isEmpty()) {
            throw new IllegalArgumentException(
                    "No bikes available at " + station.getLabel());
        }

        Bike bike = bikes.removeFirst();
        activeRentals.put(memberId,
                new Rental(bike, memberType, station));
        return bike;
    }

    public Ride returnBike(String memberId, Station returnStation,
                           int minutes) throws IOException {
        if (minutes <= 0) {
            throw new IllegalArgumentException(
                    "Ride minutes must be greater than zero.");
        }

        Rental rental = activeRentals.get(memberId);
        if (rental == null) {
            throw new IllegalArgumentException(
                    "This member has no active rental.");
        }

        Ride ride = new Ride(
                memberId,
                rental.memberType,
                rental.bike.getId(),
                rental.startStation,
                returnStation,
                minutes);

        repository.save(ride);
        activeRentals.remove(memberId);
        availableBikes.get(returnStation).addLast(rental.bike);

        return ride;
    }

    private static class Rental {
        private final Bike bike;
        private final MemberType memberType;
        private final Station startStation;

        private Rental(Bike bike, MemberType memberType,
                       Station startStation) {
            this.bike = bike;
            this.memberType = memberType;
            this.startStation = startStation;
        }
    }
}
