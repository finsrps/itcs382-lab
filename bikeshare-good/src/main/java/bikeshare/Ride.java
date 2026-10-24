
package bikeshare;

public class Ride {
    private final String memberId;
    private final MemberType memberType;
    private final String bikeId;
    private final Station startStation;
    private final Station returnStation;
    private final int minutes;
    private final int fee;

    public Ride(String memberId, MemberType memberType, String bikeId,
                Station startStation, Station returnStation, int minutes) {
        this.memberId = memberId;
        this.memberType = memberType;
        this.bikeId = bikeId;
        this.startStation = startStation;
        this.returnStation = returnStation;
        this.minutes = minutes;
        this.fee = minutes * memberType.getFeePerMinute();
    }

    public String getMemberId() {
        return memberId;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public String getBikeId() {
        return bikeId;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getReturnStation() {
        return returnStation;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getFee() {
        return fee;
    }
}
