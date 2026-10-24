
package bikeshare;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CsvRideRepository implements RideRepository {
    private final Path file;

    public CsvRideRepository(String filename) {
        this.file = Path.of(filename);
    }

    @Override
    public void save(Ride ride) throws IOException {
        boolean needsHeader =
                !Files.exists(file) || Files.size(file) == 0;

        try (var writer = Files.newBufferedWriter(
                file,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            if (needsHeader) {
                writer.write("memberId,memberType,bikeId,startStation,"
                        + "returnStation,minutes,fee");
                writer.newLine();
            }

            writer.write(ride.getMemberId() + ","
                    + ride.getMemberType() + ","
                    + ride.getBikeId() + ","
                    + ride.getStartStation().getLabel() + ","
                    + ride.getReturnStation().getLabel() + ","
                    + ride.getMinutes() + ","
                    + ride.getFee());
            writer.newLine();
        }
    }
}
