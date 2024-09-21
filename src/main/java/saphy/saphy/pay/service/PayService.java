package saphy.saphy.pay.service;

import static saphy.saphy.global.exception.ErrorCode.INVALID_REQUEST;
import static saphy.saphy.global.exception.ErrorCode.PAY_FAILURE;
import static saphy.saphy.global.exception.ErrorCode.PAY_INVALID;
import static saphy.saphy.global.exception.ErrorCode.PAY_PRICE_MISMATCH;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.repository.ItemRepository;
import saphy.saphy.pay.domain.Pay;
import saphy.saphy.pay.domain.PayMethod;
import saphy.saphy.pay.domain.PayStatus;
import saphy.saphy.pay.domain.repository.PayRepository;
import saphy.saphy.pay.dto.request.PayRequest;
import saphy.saphy.pay.dto.response.PayResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayService {
    private final PayRepository payRepository;
    private final ItemRepository itemRepository;
    private final IamportClient iamportClient;

    @Transactional
    public PayResponse processPayment(PayRequest request) {
        Optional<Item> optionalItem = itemRepository.findById(request.getItemId());

        Item item = optionalItem.get();

        log.warn(item.getClass().getName());

        IamportResponse<Payment> iamportResponse;

        try {
            // PortOne 결제 확인
            iamportResponse = iamportClient.paymentByImpUid(request.getImpUid());

        } catch (IOException e) {
            throw SaphyException.from(INVALID_REQUEST);
        } catch (IamportResponseException | IllegalArgumentException e) {
            throw SaphyException.from(PAY_FAILURE);
        }

        if (iamportResponse.getResponse() == null) {
            throw SaphyException.from(PAY_INVALID);
        }

        Payment iamPortPayment = iamportResponse.getResponse();
        /* 위 코드를 아래로 리펙토링할지??
        * Payment iamPortPayment = Optional.ofNullable(iamportResponse.getResponse())
        .orElseThrow(() -> SaphyException.from(PAY_INVALID));
        * */

        // 결제 금액 검증
        if (!iamPortPayment.getAmount().equals(item.getPrice())) {
            throw SaphyException.from(PAY_PRICE_MISMATCH);
        }

        // Payment 엔티티 생성 및 저장 >> 이 부분 toEntity로 바꿔줘야 함.
        Pay payment = Pay.builder()
            .item(item)
            .amount(iamPortPayment.getAmount())
            .payMethod(mapPaymentMethod(iamPortPayment.getPayMethod()))
            .status(PayStatus.PAID)
            .transactionId(iamPortPayment.getImpUid())
            .payDate(LocalDateTime.now())
            .build();

        payRepository.save(payment);

        // 재고 감소
        item.decreaseStock(1);
        itemRepository.save(item);

        return new PayResponse(payment.getId(), payment.getTransactionId(), payment.getStatus());
    }

    private PayMethod mapPaymentMethod(String portOnePayMethod) {
        // PortOne의 결제 방식을 우리 시스템의 PaymentMethod enum으로 매핑
        // 실제 구현시 모든 케이스를 처리해야 합니다.
        switch (portOnePayMethod) {
            case "card":
                return PayMethod.CREDIT_CARD;
            case "trans":
                return PayMethod.BANK_TRANSFER;
            default:
                return PayMethod.MOBILE_PAYMENT;
        }
    }

    @Transactional
    public PayResponse getPaymentDetails(Long paymentId) {
        Pay pay = payRepository.findById(paymentId)
            .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        return new PayResponse(pay.getId(), pay.getTransactionId(), pay.getStatus());
    }
}