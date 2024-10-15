package saphy.saphy.purchase.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import saphy.saphy.item.dto.response.ItemResponse;
import saphy.saphy.purchase.domain.Purchase;

import java.time.LocalDateTime;

@Getter
public class PurchaseResponse {
    private LocalDateTime orderDate;

    private String status;

    private BigDecimal amount;

    private String paymentType;

    private ItemResponse item;

    private PurchaseResponse(Purchase purchase) {
        this.orderDate = purchase.getOrderDate();
        this.status = purchase.getStatus().name();
        this.amount = purchase.getAmount();
        this.paymentType = purchase.getPayMethod().name();
        this.item = ItemResponse.from(purchase.getItem());
    }

    public static PurchaseResponse toDto(Purchase purchase) {
        return new PurchaseResponse(purchase);
    }

}
