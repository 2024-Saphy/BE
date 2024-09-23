package saphy.saphy.pay.domain;

public enum PayMethod {
    CREDIT_CARD("카드결제"), BANK_TRANSFER("계좌이체"), MOBILE_PAYMENT("모바일결제");

    private final String information;

    PayMethod(String info) {
        this.information = info;
    }
}