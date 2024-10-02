package saphy.saphy.pay.dto.response;

import lombok.Getter;
import saphy.saphy.pay.domain.PayStatus;

@Getter
public class PayResponse {
    private Long payId;
    private String transactionId;
    private PayStatus status;

    public PayResponse(Long paymentId, String transactionId, PayStatus status) {
        this.payId = paymentId;
        this.transactionId = transactionId;
        this.status = status;
    }
}