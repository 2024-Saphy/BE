package saphy.saphy.pay.dto.request;

import java.math.BigDecimal;
import lombok.Getter;
import saphy.saphy.item.domain.Item;
import saphy.saphy.pay.domain.Pay;
import saphy.saphy.pay.domain.PayMethod;
import saphy.saphy.pay.domain.PayStatus;

@Getter
public class PayPrepareRequest {
    private Long itemId;
    private int quantity;
    private BigDecimal amount;
    private PayMethod payMethod;

    public Pay toEntity(Item item, String merchantUid, BigDecimal amount, PayMethod payMethod ){
        return Pay.builder()
            .item(item)
            .merchantUid(merchantUid)
            .amount(amount)
            .payMethod(payMethod)
            .status(PayStatus.PENDING)
            .build();
    }

}