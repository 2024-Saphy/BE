package saphy.saphy.pay.dto.request;

import com.siot.IamportRestClient.response.Payment;
import java.time.LocalDateTime;
import lombok.Getter;
import saphy.saphy.item.domain.Item;
import saphy.saphy.pay.domain.Pay;
import saphy.saphy.pay.domain.PayMethod;
import saphy.saphy.pay.domain.PayStatus;

@Getter
public class PayRequest {
    private Long itemId;
    private String impUid;

    public Pay toEntity(Item item, Payment iamPortPayment){
        return Pay.builder()
            .item(item)
            .amount(iamPortPayment.getAmount())
            .payMethod(mapPayMethod(iamPortPayment.getPayMethod()))
            .status(PayStatus.PAID)
            .transactionId(iamPortPayment.getImpUid())
            .payDate(LocalDateTime.now())
            .build();
    }

    private PayMethod mapPayMethod(String portOnePayMethod) {
        // PortOne의 결제 방식을 우리 시스템의 PaymentMethod enum으로 매핑
        // 실제 구현시 모든 케이스를 처리해야 합니다.
        // 테스트하면서 수정 필요
        switch (portOnePayMethod) {
            case "card":
                return PayMethod.CREDIT_CARD;
            case "trans":
                return PayMethod.BANK_TRANSFER;
            default:
                return PayMethod.MOBILE_PAYMENT;
        }
    }
}