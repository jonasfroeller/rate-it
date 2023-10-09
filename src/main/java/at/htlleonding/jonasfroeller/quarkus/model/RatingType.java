package at.htlleonding.jonasfroeller.quarkus.model;

public enum RatingType {
    WHO_USES_THAT(1),
    BAD(2),
    OK(3),
    GOOD(4),
    EXCELLENT(5);

    private final int value;

    RatingType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
