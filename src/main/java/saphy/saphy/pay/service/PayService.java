package saphy.saphy.pay.service;

import static saphy.saphy.global.exception.ErrorCode.ITEM_OUT_OF_STOCK;
import static saphy.saphy.global.exception.ErrorCode.PAY_FAILURE;
import static saphy.saphy.global.exception.ErrorCode.PAY_PRICE_MISMATCH;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import saphy.saphy.auth.domain.CustomUserDetails;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.repository.ItemRepository;
import saphy.saphy.pay.domain.Pay;
import saphy.saphy.pay.domain.PayStatus;
import saphy.saphy.pay.domain.repository.PayRepository;
import saphy.saphy.pay.dto.request.PayCompleteRequest;
import saphy.saphy.pay.dto.request.PayPrepareRequest;
import saphy.saphy.pay.dto.response.PayCompleteResponse;
import saphy.saphy.pay.dto.response.PayPrepareResponse;
import saphy.saphy.purchase.domain.Purchase;
import saphy.saphy.purchase.domain.repository.PurchaseRepository;
import saphy.saphy.purchase.service.PurchaseService;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayService {
    private final PayRepository payRepository;
    private final ItemRepository itemRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseService purchaseService;
    private final IamportClient iamportClient;


    @Transactional
    public PayPrepareResponse preparePay(PayPrepareRequest request, CustomUserDetails customUserDetails) {
        // Item을 상속받는 엔티티들이 존재하기에, Optional로 받아옴
        Optional<Item> optionalItem = itemRepository.findById(request.getItemId());

        Item item = optionalItem.get();

        if (item.getStock() < request.getQuantity()) {
            throw SaphyException.from(ITEM_OUT_OF_STOCK);
        }

        BigDecimal calculatedAmount = item.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        if (!calculatedAmount.equals(request.getAmount())) {
            throw SaphyException.from(PAY_PRICE_MISMATCH);
        }

        String merchantUid = generateMerchantUid();
        Pay pay = request.toEntity(item, merchantUid, calculatedAmount, request.getPayMethod(), customUserDetails.getMember());
        payRepository.save(pay);

        item.decreaseStock(request.getQuantity());
        itemRepository.save(item);

        return new PayPrepareResponse(merchantUid, calculatedAmount);
    }

    @Transactional
    public PayCompleteResponse completePay(PayCompleteRequest request) throws IamportResponseException, IOException {
        IamportResponse<Payment> payResponse = iamportClient.paymentByImpUid(request.getImpUid());

        Pay pay = payRepository.findByMerchantUid(request.getMerchantUid());

        if (payResponse.getResponse().getAmount().equals(pay.getAmount())) {
            pay.setStatus(PayStatus.PAID);
            pay.setImpUid(request.getImpUid());

            Purchase purchase = purchaseService.toEntity(pay.getAmount(), pay.getPayMethod(), pay.getMember(), pay.getItem());
            purchaseRepository.save(purchase);
            payRepository.save(pay);

            return new PayCompleteResponse(PayStatus.PAID);
        } else {
            iamportClient.cancelPaymentByImpUid(new CancelData(request.getImpUid(), true));
            pay.setStatus(PayStatus.FAILED);
            payRepository.save(pay);

            throw SaphyException.from(PAY_FAILURE);
        }
    }

    private String generateMerchantUid() {
        return "ORD-" + UUID.randomUUID();
    }
}