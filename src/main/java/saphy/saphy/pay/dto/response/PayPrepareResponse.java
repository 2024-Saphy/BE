package saphy.saphy.pay.dto.response;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class PayPrepareResponse {
    private String merchantUid;
    private BigDecimal amount;

    public PayPrepareResponse(String merchantUid, BigDecimal amount) {
        this.merchantUid = merchantUid;
        this.amount = amount;
    }
}