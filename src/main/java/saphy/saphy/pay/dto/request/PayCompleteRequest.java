package saphy.saphy.pay.dto.request;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class PayCompleteRequest {
    private String merchantUid;
    private Long itemId;
    private String impUid;
    private BigDecimal amount;
}