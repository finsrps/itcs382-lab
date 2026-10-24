
package bikeshare;

public enum MemberType {
    STUDENT(2),
    STAFF(3);

    private final int feePerMinute;

    MemberType(int feePerMinute) {
        this.feePerMinute = feePerMinute;
    }

    public int getFeePerMinute() {
        return feePerMinute;
    }

    public static MemberType fromInput(String input) {
        try {
            return MemberType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Member type must be student or staff.");
        }
    }
}
