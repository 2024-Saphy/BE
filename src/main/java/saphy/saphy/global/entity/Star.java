package saphy.saphy.global.entity;

public enum Star {
    ONE(1),   // 1 별
    TWO(2),   // 2 별
    THREE(3), // 3 별
    FOUR(4),  // 4 별
    FIVE(5);   // 5 별

    private final int value; // 별점에 대응하는 값

    Star(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
