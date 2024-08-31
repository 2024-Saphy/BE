package saphy.saphy.order.domain;

public enum OrderStatus {
    PENDING("처리 대기 중"),
    PROCESSING("처리 중"),
    START("배송 시작"),
    SHIPPED("배송 중"),
    DELIVERED("배송 완료"),
    CANCELLED("취소됨");

    private final String description; // 상태에 대한 설명

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
