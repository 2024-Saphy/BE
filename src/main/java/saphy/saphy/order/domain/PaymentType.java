package saphy.saphy.order.domain;

public enum PaymentType {
    CARD("카드"),
    CASH("현금"),
    BANK("계좌이체");

    private final String description;  // 결제 유형에 대한 설명

    PaymentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
