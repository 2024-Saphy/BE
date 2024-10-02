package saphy.saphy.pay.dto.response;

import lombok.Getter;
import saphy.saphy.pay.domain.PayStatus;

@Getter
public class PayCompleteResponse {
    private PayStatus status;


    public PayCompleteResponse(PayStatus status) {
        this.status = status;
    }
}