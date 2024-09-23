package saphy.saphy.pay.service;

import static saphy.saphy.global.exception.ErrorCode.INVALID_REQUEST;
import static saphy.saphy.global.exception.ErrorCode.PAY_FAILURE;
import static saphy.saphy.global.exception.ErrorCode.PAY_INVALID;
import static saphy.saphy.global.exception.ErrorCode.PAY_NOT_FOUND;
import static saphy.saphy.global.exception.ErrorCode.PAY_PRICE_MISMATCH;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import saphy.saphy.global.exception.SaphyException;
import saphy.saphy.item.domain.Item;
import saphy.saphy.item.domain.repository.ItemRepository;
import saphy.saphy.pay.domain.Pay;
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


    /**
     * 결제를 진행합니다.
     * */
    @Transactional
    public PayResponse processPayment(PayRequest request) {
        // Item을 상속받는 엔티티들이 존재하기에, Optional로 받아옴
        Optional<Item> optionalItem = itemRepository.findById(request.getItemId());

        Item item = optionalItem.get();

        Payment iamportPayment = getIamportPayment(request);

        // 물품 금액과 결제 금액이 같은지 검증
        if (!iamportPayment.getAmount().equals(item.getPrice())) {
            throw SaphyException.from(PAY_PRICE_MISMATCH);
        }

        Pay pay = request.toEntity(item, iamportPayment);

        payRepository.save(pay);

        // 재고 감소
        item.decreaseStock(1);
        itemRepository.save(item);

        return new PayResponse(pay.getId(), pay.getTransactionId(), pay.getStatus());
    }

    @NotNull
    private Payment getIamportPayment(PayRequest request) {
        IamportResponse<Payment> iamportResponse;

        try {
            // PortOne 결제 확인
            iamportResponse = iamportClient.paymentByImpUid(request.getImpUid());

        } catch (IOException e) {
            throw SaphyException.from(INVALID_REQUEST);
        } catch (IamportResponseException | IllegalArgumentException e) {
            throw SaphyException.from(PAY_FAILURE);
        }

        return Optional.ofNullable(iamportResponse.getResponse())
        .orElseThrow(() -> SaphyException.from(PAY_INVALID));
    }

    @Transactional
    public PayResponse getPaymentDetails(Long paymentId) {
        Pay pay = payRepository.findById(paymentId)
            .orElseThrow(() -> SaphyException.from(PAY_NOT_FOUND));
        return new PayResponse(pay.getId(), pay.getTransactionId(), pay.getStatus());
    }
}