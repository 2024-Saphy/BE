package saphy.saphy.purchase.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.dto.response.ItemResponse;
import saphy.saphy.purchase.domain.PaymentType;
import saphy.saphy.purchase.domain.Purchase;
import saphy.saphy.purchase.domain.PurchaseStatus;

import java.time.LocalDateTime;

@Getter
public class PurchaseResponse {
    private LocalDateTime orderDate;

    private String status;

    private Long totalPrice;

    private String paymentType;

    private ItemResponse item;

    private PurchaseResponse(Purchase purchase) {
        this.orderDate = purchase.getOrderDate();
        this.status = purchase.getStatus().name();
        this.totalPrice = purchase.getTotalPrice();
        this.paymentType = purchase.getPaymentType().name();
        this.item = ItemResponse.from(purchase.getItem());
    }

    public static PurchaseResponse toDto(Purchase purchase) {
        return new PurchaseResponse(purchase);
    }

}
