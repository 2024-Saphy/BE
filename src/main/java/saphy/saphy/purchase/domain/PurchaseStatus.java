package saphy.saphy.purchase.domain;

import saphy.saphy.global.exception.ErrorCode;
import saphy.saphy.global.exception.SaphyException;

import java.util.Arrays;

public enum PurchaseStatus {
    PENDING("처리 대기 중"),
    PROCESSING("처리 중"),
    START("배송 시작"),
    SHIPPED("배송 중"),
    DELIVERED("배송 완료"),
    CANCELLED("취소됨");

    private final String description; // 상태에 대한 설명

    PurchaseStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PurchaseStatus findByName(String name) {
        try {
            return PurchaseStatus.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw SaphyException.from(ErrorCode.PURCHASE_STATUS_NOT_FOUND);
        }
    }
}
