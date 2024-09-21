package saphy.saphy.pay.dto.request;

import lombok.Getter;

@Getter
public class PayRequest {
    private Long itemId;
    private String impUid;

//    public Pay toEntity(){
//        return Pay.builder()
//            .item(item)
//            .amount(iamPortPayment.getAmount())
//            .paymentMethod(mapPaymentMethod(iamPortPayment.getPayMethod()))
//            .status(PayStatus.PAID)
//            .transactionId(iamPortPayment.getImpUid())
//            .paymentDate(LocalDateTime.now())
//            .build();
//
//    }
}